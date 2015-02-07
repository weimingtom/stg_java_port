package com.iteye.weimingtom.kikyajava.global;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameGraph {
	private Context mContext;
	private Paint mPaintRect, mPaintLine, mPaintText;
	private Canvas graph;
	private ArrayList<Bitmap> images;
	
	public GameGraph(Context context) {
		mContext = context;
		mPaintRect = new Paint();
		mPaintRect.setStyle(Paint.Style.FILL);
		mPaintLine = new Paint();
		mPaintLine.setStyle(Paint.Style.STROKE);
		mPaintText = new Paint();
		mPaintText.setTextSize(18);
		images = new ArrayList<Bitmap>();
	}
	
	public int loadImage(String path) {
		int id = -1;
		Bitmap image = null;
		InputStream is = null;
		try {
			is = mContext.getAssets().open(path);
			image = BitmapFactory.decodeStream(is);
			if (image != null) {
				images.add(image);
				id = images.indexOf(image);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}
	
	public GameGraph initGraph(Canvas graph) {
		this.graph = graph;
		return this;
	}
	
	public void fillRect(int r, int g, int b, int a, int x, int y, int width, int height) {
		mPaintRect.setColor(rbg2int(r, g, b, a));
		graph.drawRect(x, y, x + width, y + height, mPaintRect);
	}
	
	public void drawLine(int r, int g, int b, int a, int x1, int y1, int x2, int y2) {
		mPaintLine.setColor(rbg2int(r, g, b, a));
		graph.drawLine(x1, y1, x2, y2, mPaintLine);
	}
	
	public void drawImage(int imgId, int x, int y) {
		if (imgId >= 0 && imgId < images.size()) {
			Bitmap img = images.get(imgId);
			graph.drawBitmap(img, x, y, null);
		}
	}
	
	public void drawString(int r, int g, int b, int a, String str, int x, int y) {
		mPaintText.setColor(rbg2int(r, g, b, a));
		graph.drawText(str, x, y, mPaintText);
	}
	
	private final static int rbg2int(int r, int g, int b, int a) {
		return (a & 0xff) << 24 | 
		(r & 0xff) << 16 | 
		(g & 0xff) << 8 | 
		(b & 0xff);
	}
}
