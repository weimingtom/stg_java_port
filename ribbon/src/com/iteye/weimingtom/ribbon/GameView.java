package com.iteye.weimingtom.ribbon;

import java.io.IOException;
import java.io.InputStream;

import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView {
	private final static boolean D = false;
	private final static String TAG = "GameView";

	private final static int FG_TWEEN_UNDEFINE = 0;
	private final static int FG_TWEEN_WRONG = 1;
	private final static int FG_TWEEN_RIGHT = 2;
	
	private final static int FG_TWEEN_ALPHA_STEP = 64;
	
	private static final int PAUSE = 0;
    private static final int READY = 1;
	private int mode = READY;
	
	private boolean isInit = false;
	
	private GameSprite[] button;
	private GameSprite bg002;
	private GameSprite fg001, fg002, fg003;
	private GameSprite dialog;
	private GameSprite musicOn;
	private GameSprite musicOff;
	private GameSprite copyright;
	private GameSprite[] icons;
	private GameSprite an01, an02, an03, an04;
	
	private int bgcolor = Color.WHITE; //Color.BLACK; //0xFFCCCCCC;
	private Paint redrawBoxPaint, textPaint, buttonTextPaint;
	private Paint textPaintLoading;

	private final static int TIMER_DELAY = 1000;
	private final static int TIMER_SLEEP_DELAY = 1000 / 24;
	private final static int GEOMETRIC_PROGRESSION = 4;
	private long lastTimer;
    private TimerHandler mTimerHandler = new TimerHandler();
    
    private Rect[] buttonRects;
    private Rect[] iconButtonRects;
    private GameSprite[] notification;
	
	private int tweenCounter;
	private static final int TWEEN_COUNTER_MAX = 1000;
	private final static double DIFF_MAX = 0.005;
	private double musicOnX;
	private double musicOnXTarget = 0.8;
	private double fgX;
	private double fgXTarget = 0;
	private int fgAlpha;
	private int fgXStep;
	private double dialogX;
	private double dialogXTarget;
	private double selectButtonY;
	private double selectButtonYTarget;
	private double selectButtonX;
	private double selectButtonXTarget;
	
	private int tweenFGType;
	private int tweenFGCounter;
	private int fgTransAlpha;
	private int fgTransAlphaTarget;
	
	private int totalWinNum = 0;
	
	public static interface OnFinishListener {
		void onFinish();
	}
	
	private OnFinishListener onFinishListener;
	
	public void setOnFinishListener(OnFinishListener onFinishListener) {
		this.onFinishListener = onFinishListener;
	}
	
	private void resetTween() {
		tweenCounter = 0;
		musicOnX = 1;
		musicOnXTarget = 0.8;
		fgX = 1;
		fgXTarget = 0;
		fgXStep = getTweenStep(fgX, fgXTarget);
		fgAlpha = 0;
		dialogX = -15.0 / 16;
		dialogXTarget = 1.0 / 16;
		selectButtonY = 1;	
		selectButtonYTarget = 27.0 / 32; //9.0 / 16; //27.0 / 32;
		selectButtonX = 1.0 / 13 - 1.0 / 58 - 1;
		selectButtonXTarget = 1.0 / 13 - 1.0 / 58; //(i * 3.0 / 13)
	}

	private static int getTweenStep(double original, double target) {
		double testX = original;
		double testXTarget = target;
		int step = 0;
		while (true) {
        	if (Math.abs(testX - testXTarget) > DIFF_MAX) {
        		testX = testX + (testXTarget - testX) / GEOMETRIC_PROGRESSION;
        		step++;
        	} else {
    			break;
    		}
		}
		return step;
	}
	
	private boolean onTween() {
        if (tweenCounter <= TWEEN_COUNTER_MAX) {
        	tweenCounter++;
    		
        	//musicOnX
        	if (Math.abs(musicOnX - musicOnXTarget) > DIFF_MAX && 
        		this.tweenCounter < TWEEN_COUNTER_MAX) {
    			musicOnX = musicOnX + (musicOnXTarget - musicOnX) / GEOMETRIC_PROGRESSION;
    		} else {
    			musicOnX = musicOnXTarget;
    		}
        	
        	//fgX
        	if (Math.abs(fgX - fgXTarget) > DIFF_MAX &&
        		this.tweenCounter < TWEEN_COUNTER_MAX) {
    			fgX = fgX + (fgXTarget - fgX) / GEOMETRIC_PROGRESSION;
    			int alpha = (int)(((double)this.tweenCounter / fgXStep * 255));
    			if (alpha < 0) {
    				fgAlpha = 0;
    			} else if (alpha > 255) {
    				fgAlpha = 255;
    			} else {
    				fgAlpha = alpha;
    			}
        	} else {
    			fgX = fgXTarget;
    			fgAlpha = 255;
    		}
        	
        	//dialogX
        	if (Math.abs(dialogX - dialogXTarget) > DIFF_MAX &&
        		this.tweenCounter < TWEEN_COUNTER_MAX) {
        		dialogX = dialogX + (dialogXTarget - dialogX) / GEOMETRIC_PROGRESSION;
    		} else {
    			dialogX = dialogXTarget;
    		}

        	//selectButtonY
        	if (Math.abs(selectButtonY - selectButtonYTarget) > DIFF_MAX &&
        		this.tweenCounter < TWEEN_COUNTER_MAX) {
        		selectButtonY = selectButtonY + (selectButtonYTarget - selectButtonY) / GEOMETRIC_PROGRESSION;
        	} else {
    			selectButtonY = selectButtonYTarget;
    		}

        	if (Math.abs(selectButtonX - selectButtonXTarget) > DIFF_MAX &&
        		this.tweenCounter < TWEEN_COUNTER_MAX) {
        		selectButtonX = selectButtonX + (selectButtonXTarget - selectButtonX) / GEOMETRIC_PROGRESSION;
        	} else {
    			selectButtonX = selectButtonXTarget;
    		}
        	
        	return true;
        }
        return false;
	}
	
	private boolean onFGTween() { 
        if (this.tweenFGType != FG_TWEEN_UNDEFINE && 
        	tweenFGCounter <= TWEEN_COUNTER_MAX) {
        	tweenFGCounter++;
        	if (this.fgTransAlpha > this.fgTransAlphaTarget) {
        		this.fgTransAlpha -= FG_TWEEN_ALPHA_STEP;
        		return true;
    		} else {
    			this.fgTransAlpha = this.fgTransAlphaTarget;
    			if (this.tweenFGType == FG_TWEEN_RIGHT) {
    			} else {
    			}
    			this.tweenFGType = FG_TWEEN_UNDEFINE;
    			//return false;
    			return true;
    		}
        } else {
        	return false;
        }
	}
	
	public GameView(Context context) {
		super(context);
		init(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		getHolder().addCallback(new SurfaceCallback());
		setFocusable(true);
//		setFocusableInTouchMode(true);
		requestFocus();
		//used when loading, so created here
		textPaintLoading = new Paint();
		float scale = this.getResources().getDisplayMetrics().scaledDensity;
		textPaintLoading.setTextSize(18 * scale);
		textPaintLoading.setAntiAlias(true);
		textPaintLoading.setTextAlign(Paint.Align.CENTER);
		textPaintLoading.setColor(Color.BLACK);
	}
	
	private void onSurfaceCreated() {
		this.isInit = true;
	}

	private void onSurfaceCreated2() {
		resetSM();
		preload();
		reload(0);
	}
	
    private void resetSM() {
    	
    }
	
    private void preload() {	
    	//paint
		redrawBoxPaint = new Paint();
		redrawBoxPaint.setStyle(Paint.Style.STROKE);
		redrawBoxPaint.setStrokeWidth(0);
		redrawBoxPaint.setColor(Color.RED);
		
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.STROKE);
		textPaint.setColor(Color.BLACK);
		textPaint.setTextAlign(Paint.Align.LEFT);
		
		buttonTextPaint = new Paint();
		buttonTextPaint.setAntiAlias(true);
		buttonTextPaint.setStyle(Paint.Style.STROKE);
		buttonTextPaint.setColor(0xccfc7e8d/*0xcc4883c0*/);
		buttonTextPaint.setTextAlign(Paint.Align.LEFT);
		
		//sprite
		button = new GameSprite[] {
			loadLetterButtonFromAsset("button.png", "A"),
			loadLetterButtonFromAsset("button.png", "B"),
			loadLetterButtonFromAsset("button.png", "C"),
			loadLetterButtonFromAsset("button.png", "D"),
		};
		musicOn = loadFromAsset("music_on.png");
		musicOff = loadFromAsset("music_off.png");
		copyright = loadFromAsset("copyright.png");
		notification = new GameSprite[] {
			loadFromAsset("stageclear.png"), 
			loadFromAsset("gameover.png"),
			loadFromAsset("right.png"), 
			loadFromAsset("wrong.png"), 
			loadFromAsset("timeout.png"),
			loadFromAsset("gameclear.png"),
			loadFromAsset("start.png"),
			loadFromAsset("number3.png"),
			loadFromAsset("number2.png"),
			loadFromAsset("number1.png"),
		};
		icons = new GameSprite[] {
			loadFromAsset("icon_001.jpg"), 
			loadFromAsset("icon_002.jpg"),
			loadFromAsset("icon_003.jpg"), 
			loadFromAsset("icon_004.jpg"), 
			loadFromAsset("icon_005.jpg"),			
			loadFromAsset("icon_006.jpg"),
			loadFromAsset("cross_001.png"),
		};
		an01 = loadFromAsset("an_01.png");
		an02 = loadFromAsset("an_02.png");
		an03 = loadFromAsset("an_03.png");
		an04 = loadFromAsset("an_04.png");
		
		dialog = newDialog(100, 100);

		buttonRects = new Rect[4];
		for (int i = 0; i < buttonRects.length; i++) {
			buttonRects[i] = new Rect();
		}
		iconButtonRects = new Rect[icons.length];
		for (int i = 0; i < iconButtonRects.length; i++) {
			iconButtonRects[i] = new Rect();
		}
		resetTween();
    }
    
	private void reload(int charIndex) {
		if (charIndex < 0 || charIndex > 5) {
			MersenneTwisterRandom mt = new MersenneTwisterRandom();
			mt.init_genrand((int)System.currentTimeMillis());
			charIndex = mt.nextInt(0, 5);
		}
		reloadAndComposeBitmaps(charIndex);
		reloadVoice(charIndex);
	}
	
	private void onSurfaceChanged(int width, int height) {
//		bg002.scaleCenter(0, 0, width, height, 
//				GameSprite.SCALE_WITH_BIG_CENTER
//			);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		// background
		canvas.drawColor(bgcolor);
		if (!this.isInit) {
			bg002.scaleCenter(0, 0, this.getWidth(), this.getHeight(), 
				GameSprite.SCALE_WITH_BIG_CENTER
			);
			bg002.drawCanvas(canvas);
			Rect bgRect = bg002.getDstRect();
			
			if (this.tweenFGType == FG_TWEEN_WRONG) {
				if (this.fgTransAlpha >= 0) {
					fg001.setAlpha(fgTransAlpha);
					fg001.setRelativeRegion(bg002, fgX, 0, 1, 1);
					fg001.drawCanvasInRect(canvas, bgRect);
				} else {
					fg003.setAlpha(-fgTransAlpha);
					fg003.setRelativeRegion(bg002, fgX, 0, 1, 1);
					fg003.drawCanvasInRect(canvas, bgRect);				
				}
			} else if (this.tweenFGType == FG_TWEEN_RIGHT) {
				if (this.fgTransAlpha >= 0) {
					fg001.setAlpha(fgTransAlpha);
					fg001.setRelativeRegion(bg002, fgX, 0, 1, 1);
					fg001.drawCanvasInRect(canvas, bgRect);
				} else {
					fg002.setAlpha(-fgTransAlpha);
					fg002.setRelativeRegion(bg002, fgX, 0, 1, 1);
					fg002.drawCanvasInRect(canvas, bgRect);				
				}
			} else {
				fg001.setAlpha(fgAlpha);
				fg001.setRelativeRegion(bg002, fgX, 0, 1, 1);
				fg001.drawCanvasInRect(canvas, bgRect);
			}
			
			{
				double r = (double)copyright.getSrcRect().height() / bg002.getSrcRect().width();
				copyright.setRelativeRegion(bg002, 0, 1 - r, 1, r);
				copyright.drawCanvasInRect(canvas, bgRect);
			}
			
			// dialog
			{
				double drx = dialogX; //1.0 / 16;
				double dry = 22.0 / 32; //7.0 / 16;
				double drw = 14.0 / 16;
				double drh =  9.0 / 32; //17.0 / 32;
				dialog.setRelativeRegion(bg002, drx, dry, drw, drh);
				//dialog.drawCanvas(canvas);
				dialog.drawCanvasInRect(canvas, bgRect);
			}
						
			musicOn.setRelativeRegion(bg002, musicOnX, 0/*0.09*/, 0.2, (double)musicOn.getSrcRect().height() / (double)musicOn.getSrcRect().width() * 0.2); //0.07);
			//musicOn.drawCanvas(canvas);
			musicOn.drawCanvasInRect(canvas, bgRect);
			
			//animation (small notification)
		} else {
			/**
			 * 
			 * Loading...
			 * 
			 */
			String progressInfo = "数据加载中...";
			//Rect progressBounds = new Rect();
			//textPaintLoading.getTextBounds(progressInfo, 0, progressInfo.length(), progressBounds);		
			canvas.drawText(progressInfo, this.getWidth() / 2, this.getHeight() / 2, textPaintLoading);
		}
		canvas.restore();
	}

	private GameSprite loadFromAsset(String filename) {
		InputStream is = null;
		try {
			is = getContext().getAssets().open(filename);
			return new GameSprite(BitmapFactory.decodeStream(is));
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
		return null;
	}

	private GameSprite loadLetterButtonFromAsset(String filename, String letter) {
		InputStream is = null;
		try {
			is = getContext().getAssets().open(filename);
			Bitmap tempBmp = BitmapFactory.decodeStream(is);
			Bitmap bmp = Bitmap.createBitmap(tempBmp.getWidth(), tempBmp.getHeight(), 
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			canvas.drawBitmap(tempBmp, 0, 0, null);
			
			Rect bounds = new Rect();
			buttonTextPaint.setTextAlign(Paint.Align.CENTER);
			buttonTextPaint.setTextSize(tempBmp.getWidth());
			buttonTextPaint.getTextBounds(letter, 0, 1, bounds);
			canvas.drawText(letter, tempBmp.getWidth() / 2, tempBmp.getHeight() / 2 + bounds.height() / 2, buttonTextPaint);
			
			tempBmp.recycle();
			return new GameSprite(bmp);
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
		return null;
	}
	
	private GameSprite newDialog(int w, int h) {
		return new GameSprite(GameSprite.DIALOG, w, h);
	}
	
	public void onFirstStartedState() {
		if (D) {
			Log.e(TAG, "onFirstStartedState");
		}
		setMode(READY);
		onTimer();
	}
	
	public void onRestartedState() {
		if (D) {
			Log.e(TAG, "onRestartedState");
		}
		setMode(READY);
		onTimer();
	}
	
	public void onPauseState() {
		if (D) {
			Log.e(TAG, "onPauseState");
		}
		setMode(PAUSE);
		resetSM();
	}
	
	public void onStopState() {
		if (D) {
			Log.e(TAG, "onStopState");
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (D) {
			Log.e(TAG, "onDetachedFromWindow");
		}
		try {
			//resouce release, after onPauseState
			if (button != null) {
				for (int i = 0; i < button.length; i++) {
					button[i].release();
				}
			}
			if (bg002 != null) {
				bg002.release();
			}
			if (fg001 != null) {
				fg001.release();
			}
			if (fg002 != null) {
				fg002.release();
			}
			if (fg003 != null) {
				fg003.release();
			}
			if (dialog != null) {
				dialog.release();
			}
			if (musicOn != null) {
				musicOn.release();
			}
			if (musicOff != null) {
				musicOff.release();
			}			
			if (copyright != null) {
				copyright.release();
			}
			if (notification != null) {
				for (int i = 0; i < notification.length; i++) {
					if (notification[i] != null) {
						notification[i].release();
					}
				}
			}
			if (icons != null) {
				for (int i = 0; i < icons.length; i++) {
					if (icons[i] != null) {
						icons[i].release();
					}
				}
			}
			if (an01 != null) {
				an01.release();
			}
			if (an02 != null) {
				an02.release();
			}
			if (an03 != null) {
				an03.release();
			}
			if (an04 != null) {
				an04.release();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void setMode(int newMode) {
		this.mode = newMode;
	}
	
	private void repaint() {
		Canvas canvas = null;
		SurfaceHolder surfaceHolder = getHolder();
		try {
			canvas = surfaceHolder.lockCanvas();
			if (canvas == null) {
				return;
			}
			synchronized (surfaceHolder) {
				draw(canvas);
			}
		} finally {
			if (canvas != null) {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	/**
	 * @see https://code.google.com/p/skia/source/browse/android/app/src/com/skia/SkiaSampleView.java
	 */
	@Override 
    public boolean onTouchEvent(MotionEvent e) {
		if (e == null) {
			return false;
		}
        int count = e.getPointerCount();
        boolean isRepaint = false;
        for (int i = 0; i < count; i++) {
            final float x = e.getX(i);
            final float y = e.getY(i);
            int action = e.getAction() & MotionEvent.ACTION_MASK;
            switch (action) {
            case MotionEvent.ACTION_UP:
	        case MotionEvent.ACTION_POINTER_UP:
	        	action = MotionEvent.ACTION_UP;
	        	handleClick(x, y);
//	        	Log.e(TAG, "handleClick " + x + ", " + y);
	        	isRepaint = true;
	        	break;
	        	
	        case MotionEvent.ACTION_DOWN:
	        case MotionEvent.ACTION_POINTER_DOWN:
                action = MotionEvent.ACTION_DOWN;
                break;
                
            default:
                break;
	        }
        }
        if (isRepaint) {
        	repaint();
        }
        return true;
    } 

	private void handleClick(float x, float y) {
    	for (int i = 0; i < buttonRects.length; i++) {
        	Rect rect = buttonRects[i];
        	if (rect != null && !rect.isEmpty() && rect.contains((int)x, (int)y)) {
        		if (D) {
        			Log.e(TAG, "hit " + i);
        		}
        	}
        }
        for (int i = 0; i < iconButtonRects.length; i++) {
        	Rect rect = iconButtonRects[i];
        	if (rect != null && !rect.isEmpty() && rect.contains((int)x, (int)y)) {
        		if (D) {
        			Log.e(TAG, "hit " + i);
        		}
        		if (i == 6) {
            		if (this.onFinishListener != null) {
            			this.onFinishListener.onFinish();
            		}
            	}
        	}
        }
	}
	
	private class SurfaceCallback implements SurfaceHolder.Callback {
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			if (D) {
				Log.e(TAG, "surfaceChanged");
			}
			onSurfaceChanged(width, height);
			//repaint();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			if (D) {
				Log.e(TAG, "surfaceCreated");
			}
			onSurfaceCreated();
			//repaint();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			
		}
	}
	
	private void onTimer() {
		if (isInit) {
			repaint();
			this.onSurfaceCreated2();
			isInit = false;
		}
		
		if (mode == PAUSE) {
			return;
		}
        long now = System.currentTimeMillis();
        boolean isRepaint = false;
        if (now - lastTimer > TIMER_DELAY) {
        	//repaint();
        	isRepaint = true;
        	lastTimer = now;
        }
        if (onTween()) {
        	isRepaint = true;
        }
        if (onFGTween()) {
        	isRepaint = true;
        }
        if (isRepaint) {
        	repaint();
        }
        long delta = System.currentTimeMillis() - now;
        if (TIMER_SLEEP_DELAY > delta) {
        	mTimerHandler.sleep(TIMER_SLEEP_DELAY - delta);
        } else {
        	mTimerHandler.sleep(0);
        }
	}
	
    private class TimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	GameView.this.onTimer();
        }
        
        public void sleep(long delayMillis) {
        	this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
    
	private Bitmap compose(String filenameNoSuffix) {
		Bitmap bmp1 = loadBitmapFromAsset(filenameNoSuffix + ".jpg");
		Bitmap bmp2 = loadBitmapFromAsset(filenameNoSuffix + ".png");
		PorterDuffXfermode mode2 = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
		Bitmap bmp3 = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas3 = new Canvas(bmp3);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
        canvas3.drawBitmap(bmp2, 0, 0, paint);
        paint.setColorFilter(null);
        paint.setXfermode(mode2);
        canvas3.drawBitmap(bmp1, 0, 0, paint);
        bmp1.recycle();
        bmp2.recycle();
        return bmp3;
	}

	private Bitmap loadBitmapFromAsset(String filename) {
		InputStream is = null;
		try {
			is = getContext().getAssets().open(filename);
			return BitmapFactory.decodeStream(is);
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
		return null;
	}
	
	private void reloadAndComposeBitmaps(int index) {
		if (bg002 != null) {
			bg002.release();
		}
		switch (totalWinNum % 3) {
		case 0:
			bg002 = loadFromAsset("bg003.jpg");
			break;
			
		case 1:
			bg002 = loadFromAsset("bg004.jpg");
			break;
			
		case 2:
			bg002 = loadFromAsset("bg005.jpg");
			break;
		}
		if (fg001 != null) {
			fg001.release();
		}
		if (fg002 != null) {
			fg002.release();
		}
		if (fg003 != null) {
			fg003.release();
		}
		switch(index) {
		case 0:
			fg001 = new GameSprite(compose("gfx/ch001_001"));
			fg002 = new GameSprite(compose("gfx/ch001_002"));
			fg003 = new GameSprite(compose("gfx/ch001_003"));
			break;
			
		case 1:
			fg001 = new GameSprite(compose("gfx/ch002_001"));
			fg002 = new GameSprite(compose("gfx/ch002_002"));
			fg003 = new GameSprite(compose("gfx/ch002_003"));
			break;
			
		case 2:
			fg001 = new GameSprite(compose("gfx/ch003_001"));
			fg002 = new GameSprite(compose("gfx/ch003_002"));
			fg003 = new GameSprite(compose("gfx/ch003_003"));
			break;
			
		case 3:
			fg001 = new GameSprite(compose("gfx/ch004_001"));
			fg002 = new GameSprite(compose("gfx/ch004_002"));
			fg003 = new GameSprite(compose("gfx/ch004_003"));
			break;
			
		case 4:
			fg001 = new GameSprite(compose("gfx/ch005_001"));
			fg002 = new GameSprite(compose("gfx/ch005_002"));
			fg003 = new GameSprite(compose("gfx/ch005_003"));
			break;
			
		case 5:
			fg001 = new GameSprite(compose("gfx/ch006_001"));
			fg002 = new GameSprite(compose("gfx/ch006_002"));
			fg003 = new GameSprite(compose("gfx/ch006_003"));
			break;
			
		default:
			fg001 = new GameSprite(compose("gfx/ch001_001"));
			fg002 = new GameSprite(compose("gfx/ch001_002"));
			fg003 = new GameSprite(compose("gfx/ch001_003"));
			break;
		}
	}
	
    private void reloadVoice(int index) {
    	
    }
}
