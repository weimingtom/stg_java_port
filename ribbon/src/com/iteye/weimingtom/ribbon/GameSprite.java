package com.iteye.weimingtom.ribbon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Off-screen sprite and state machine
 * @author weimingtom
 *
 */
public class GameSprite {
	public static final int BITMAP = 1;
	public static final int DIALOG = 2;
	
    public static final int NO_HANDLE = -1;
    public static final int NO_SCALE_CENTER = 0;
    public static final int SCALE_WITH_BIG_CENTER = 1;
    public static final int SCALE_CENTER = 2;
    public static final int SCALE_WITH_SMALL = 3;		
    public static final int NO_SCALE_CENTER_AND_KEEP_IN_RECT = 4;
    public static final int SCALE_WITH_SMALL_TOP = 5;
    public static final int SCALE_WITH_SMALL_BUTTOM = 6;
	
    private int type;
	private Bitmap bmp;
	private Rect srcBounds;
	private Rect bounds;
	private Paint mPaint;
	
	public GameSprite(int type, int w, int h) {
		this.type = type;
		this.bounds = new Rect(0, 0, w, h);
		this.srcBounds = new Rect(bounds);
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		this.mPaint.setDither(true);
		this.mPaint.setFilterBitmap(true);
		this.mPaint.setStyle(Paint.Style.FILL);
		this.mPaint.setStrokeWidth(0);
		this.mPaint.setColor(/*Color.WHITE*/0xfffddbdf); //dialog
		this.mPaint.setAlpha(0xa0/*0x80*/);
	}
	
	public GameSprite(Bitmap bmp) {
		this.type = BITMAP;
		this.bmp = bmp;
		this.bounds = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		this.srcBounds = new Rect(bounds);
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		this.mPaint.setDither(true);
		this.mPaint.setFilterBitmap(true);
	}
	
	public void release() {
		if (this.bmp != null) {
			this.bmp.recycle();
		}
	}

	public Bitmap getBitmap() {
		return bmp;
	}
	
	public Rect getSrcRect() {
		return srcBounds;
	}
	
	public Rect getDstRect() {
		return bounds;
	}
	
	public void drawCanvas(Canvas canvas) {
		switch (type) {
		case BITMAP:
			canvas.drawBitmap(getBitmap(), getSrcRect(), getDstRect(), mPaint);
			break;
			
		case DIALOG:
			canvas.drawRoundRect(new RectF(getDstRect()), 10, 10, mPaint);
			break;
		}
	}

	public void drawCanvasInRect(Canvas canvas, Rect rect) {
		Rect dstRect = new Rect();
		dstRect.setIntersect(rect, getDstRect());
		if (!dstRect.isEmpty()) {
			switch (type) {
			case BITMAP:
				Rect srcRect = new Rect();
				Rect sr = getSrcRect();
				Rect dr = getDstRect();
				/*
				srcRect.left = (int) (((double)dstRect.left / dr.left) * sr.left);
				srcRect.right = (int) (((double)dstRect.right / dr.right) * sr.right);
				srcRect.top = (int) (((double)dstRect.top / dr.top) * sr.top);
				srcRect.bottom = (int) (((double)dstRect.bottom / dr.bottom) * sr.bottom);
				*/
				double dleft = (double)(dstRect.left - dr.left) / dr.width();
				double dright = (double)(dstRect.right - dr.right) / dr.width();
				double dtop = (double)(dstRect.top - dr.top) / dr.height();
				double dbottom = (double)(dstRect.bottom - dr.bottom) / dr.height();
				srcRect.left = (int)(sr.left + dleft * sr.width());
				srcRect.right = (int)(sr.right + dright * sr.width());
				srcRect.top = (int)(sr.top + dtop * sr.height());
				srcRect.bottom = (int)(sr.bottom+ dbottom * sr.height());
				
				canvas.drawBitmap(getBitmap(), srcRect, dstRect, mPaint);
				break;
				
			case DIALOG:
				canvas.drawRoundRect(new RectF(dstRect), 10, 10, mPaint);
				break;
			}
		}
	}
	
	public void setPosition(int x, int y) {
		int width = getWidth();
		int height = getHeight();
		bounds.left = x;
		bounds.right = x + width;
		bounds.top = y;
		bounds.bottom = y + height;
	}
	
	public int getX() {
		return bounds.left;
	}
	
	public int getY() {
		return bounds.top;
	}
	
	public void setScale(double scalex, double scaley) {
		bounds.right = (int)(bounds.left + srcBounds.width() * scalex);
		bounds.bottom = (int)(bounds.top + srcBounds.height() * scaley);
	}
	
	public double getScaleX() {
		return ((double)(bounds.right - bounds.left)) / srcBounds.width();
	}
	
	public double getScaleY() {
		return ((double)(bounds.bottom - bounds.top)) / srcBounds.height();
	}
	
	public int getWidth() {
		return bounds.width();
	}
	
	public int getHeight() {
		return bounds.height();
	}
	
	public void setWidth(int w) {
		this.bounds.right = this.bounds.left + w;
	}
	
	public void setHeight(int h) {
		this.bounds.bottom = this.bounds.top + h;
	}
	
	public void setAlpha(int alpha) {
		if (alpha < 0) {
			alpha = 0;
		} else if (alpha > 255) {
			alpha = 255;
		}
		this.mPaint.setAlpha(alpha);
	}
	
	/*
	public void alignToRelativePosition(GameSprite sprite, int dx, int dy) {
		this.setPosition(sprite.getX() + dx, sprite.getY() + dy);
		this.setScale(sprite.getScaleX(), sprite.getScaleY());
	}
	*/
	
	public void setRelativeRegion(GameSprite sprite, double rx, double ry, double rw, double rh) {
		this.setPosition((int) (sprite.getX() + rx * sprite.getWidth()), 
				(int) (sprite.getY() + ry * sprite.getHeight()));
		//this.setScale(sprite.getScaleX(), sprite.getScaleY());
		this.setWidth((int)(sprite.getWidth() * rw));
		this.setHeight((int)(sprite.getHeight() * rh));
	}
	
	/*
	public void alignToRelativeRegion(GameSprite sprite, int dx, int dy, int w, int h) {
		setPosition(sprite.getX() + dx, sprite.getY() + dy);
		bounds.right = (int)(bounds.left + w);
		bounds.bottom = (int)(bounds.top + h);
	}
	*/
	
    public void scaleCenter(Rect rect, int type)  {
    	scaleCenter(rect.left, rect.top, rect.width(), rect.height(), type);
    }
    
    public void scaleCenter(int x, int y, int width, int height, int type) {
        if (type == NO_HANDLE) {
            return;
        }
        this.setScale(1, 1);
		switch(type) {
		case NO_SCALE_CENTER:
            this.setScale(1, 1);
			break;
        
		case SCALE_WITH_BIG_CENTER:
			if ((double)getWidth() / width > (double)getHeight() / height) {
                setHeight((int)((double)getHeight() / getWidth() * width));
                setWidth(width);
            } else {
                setWidth((int)((double)getWidth() / getHeight() * height));
                setHeight(height);
            }
			break;
			
        case SCALE_WITH_SMALL:
            if ((double)getWidth() / width < (double)getHeight() / height) {
                setHeight((int)((double)getHeight() / getWidth() * width));
                setWidth(width);
            } else {
                setWidth((int)((double)getWidth() / getHeight() * height));
                setHeight(width);
            }
			break;
			
        case SCALE_CENTER:
            setWidth(width);
            setHeight(height);
			break;
			
        case NO_SCALE_CENTER_AND_KEEP_IN_RECT:	
            if ((double)getWidth() > width || (double)getHeight() > height) {
                if ((double)getWidth() / width > (double)getHeight() / height) {
                    setHeight((int)((double)getHeight() / getWidth() * width));
                    setWidth(width);
                } else {
                    setWidth((int)((double)getWidth() / getHeight() * height));
                    setHeight(height);
                }
            } else {
                setScale(1, 1);
            }
			break;
			
        case SCALE_WITH_SMALL_TOP:
            if ((double)getWidth() / width < (double)getHeight() / height) {
                setHeight((int)((double)getHeight() / getWidth() * width));
                setWidth(width);
            } else {
                setWidth((int)((double)getWidth() / getHeight() * height));
                setHeight(width);
            }
            setPosition((int)(x + (double)width / 2 - (double)getWidth() / 2), y);
            return;
			
        case SCALE_WITH_SMALL_BUTTOM:		
            if ((double)getWidth() / width < (double)getHeight() / height) {
                setHeight((int)((double)getHeight() / getWidth() * width));
                setWidth(width);
            } else {
                setWidth((int)((double)getWidth() / getHeight() * height));
                setHeight(width);
            }
            setPosition((int)(x + (double)width / 2 - (double)getWidth() / 2), 
            		height - getHeight());
            return;
        }
		setPosition((int)(x + (double)width / 2 - (double)getWidth() / 2), 
				(int)(y + (double)height / 2 - getHeight() / 2));
    }
}
