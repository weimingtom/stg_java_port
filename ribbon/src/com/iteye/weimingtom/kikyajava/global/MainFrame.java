package com.iteye.weimingtom.kikyajava.global;

import java.io.IOException;

import com.iteye.weimingtom.ribbon.GameSprite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.FrameLayout;

public class MainFrame extends Activity {
	private static final boolean D = false;
	private static final String TAG = "JkanjiGameActivity";
	
	private GameView gameBoard;
	
	//
	
	private static final int canv_width = 300;
	private static final int canv_height = 400;
	private Game game;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hideTitle();
        this.gameBoard = new GameView(this);
        gameBoard.setOnFinishListener(new GameView.OnFinishListener() {
			@Override
			public void onFinish() {
				if (D) {
					Log.d(TAG, "onFinish");
				}
				finish();
			}
			
			public void onStart() {
				onInit();
			}
			
			public void onTimerTick() {
				onTick();
			}
			
			public void onInputDown(int x, int y) {
				keyPressed(x, y);
			}

			public void onInputMove(int x, int y) {
				keyDraged(x, y);
			}
			
			public void onInputUp(int x, int y) {
				keyReleased(x, y);
			}
        });
        FrameLayout layout = new FrameLayout(this);
        layout.addView(gameBoard);
        setContentView(layout);

		if (savedInstanceState == null) {
			onFirstStartedState();
        } else {
            boolean isFirstSaved = savedInstanceState.getBoolean("isFirstSaved");
            if (isFirstSaved) {
            	onRestartedState();
            } else {
                onPauseState(); //FIXME:
            }
        }
    }
    
	@Override
	protected void onStop() {
		super.onStop();
		onStopState();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		onRestartedState();
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        onPauseState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isFirstSaved", true);
    }

    /**
     * @see http://embed.e800.com.cn/articles/2011/217/1297910848825_1.html
     */
    public void hideTitle() {
//    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
	private void onFirstStartedState() {
		if (D) {
			Log.e(TAG, "onFirstStartedState");
		}
		if (this.gameBoard != null) {
			this.gameBoard.onFirstStartedState();
		}
	}
    
	private void onRestartedState() {
		if (D) {
			Log.e(TAG, "onRestartedState");
		}
		if (this.gameBoard != null) {
			this.gameBoard.onRestartedState();
		}
	}
	
	private void onPauseState() {
		if (D) {
			Log.e(TAG, "onPauseState");
		}
		if (this.gameBoard != null) {
			this.gameBoard.onPauseState();
		}
	}
	
	private void onStopState() {
		if (D) {
			Log.e(TAG, "onStopState");
		}
		if (this.gameBoard != null) {
			this.gameBoard.onStopState();
		}
	}
	
	private final static class GameView extends SurfaceView {
		private final static boolean D = false;
		private final static String TAG = "GameView";

		private static final int PAUSE = 0;
	    private static final int READY = 1;
		private int mode = READY;
		
		private boolean isInit = false;
		
		private int bgcolor = Color.BLACK;
		private Paint textPaintLoading;

		private final static int TIMER_DELAY = 1000;
		private final static int TIMER_SLEEP_DELAY = 1000 / 24;
		private long lastTimer;
	    private TimerHandler mTimerHandler = new TimerHandler();
	    
	    public GameSprite bg;
	    public Bitmap bgBitmap;
	    public Canvas bgCanvas;
		public boolean enableTick = true;
	    
		public static interface OnFinishListener {
			void onFinish();
			void onStart();
			void onTimerTick();
			void onInputUp(int x, int y);
			void onInputDown(int x, int y);
			void onInputMove(int x, int y);
		}
		
		private OnFinishListener onFinishListener;
		
		public void setOnFinishListener(OnFinishListener onFinishListener) {
			this.onFinishListener = onFinishListener;
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
//			setFocusableInTouchMode(true);
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
			preload();
		}
		
	    private void preload() {	
	    	if (onFinishListener != null) {
	    		onFinishListener.onStart();
	    	}
	    }
		
		private void onSurfaceChanged(int width, int height) {
			
		}
		
		@Override
		public void draw(Canvas canvas) {
			canvas.save();
			// background
			canvas.drawColor(bgcolor);
			if (!this.isInit) {
				bg.scaleCenter(0, 0, this.getWidth(), this.getHeight(), 
					GameSprite.SCALE_WITH_BIG_CENTER
				);
				bg.drawCanvas(canvas);
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
		        	if (onFinishListener != null) {
		        		onFinishListener.onInputUp(getBgX(x), getBgY(y));
		        	}
		        	isRepaint = true;
		        	break;
		        	
		        case MotionEvent.ACTION_MOVE:
	                action = MotionEvent.ACTION_DOWN;
		        	if (onFinishListener != null) {
		        		onFinishListener.onInputMove(getBgX(x), getBgY(y));
		        	}
		        	isRepaint = true;		        	
		        	break;
		        	
		        case MotionEvent.ACTION_DOWN:
		        case MotionEvent.ACTION_POINTER_DOWN:
	                action = MotionEvent.ACTION_DOWN;
		        	if (onFinishListener != null) {
		        		onFinishListener.onInputDown(getBgX(x), getBgY(y));
		        	}
		        	isRepaint = true;
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
		
		public int getBgX(float x) {
			if (bg != null) {
				return (int)(((x - bg.getX()) / bg.getWidth()) * bg.getSrcRect().width());
			}
			return 0;
		}
		
		public int getBgY(float y) {
			if (bg != null) {
				return (int)(((y - bg.getY()) / bg.getHeight()) * bg.getSrcRect().height());
			}
			return 0;
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
	        //FIXME:
	        if (enableTick && onFinishListener != null) {
	        	onFinishListener.onTimerTick();
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
	}
	
	protected void onInit() {
		if (this.gameBoard != null) {
			this.gameBoard.bgBitmap = Bitmap.createBitmap(canv_width, canv_height, Bitmap.Config.ARGB_8888);
			this.gameBoard.bgCanvas = new Canvas(this.gameBoard.bgBitmap);
			this.gameBoard.bg = new GameSprite(this.gameBoard.bgBitmap);
		}
		try {
			game = new Game(this, canv_width, canv_height);
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
		game.play(this);
	}
	
	public void setEnableTick(boolean enableTick) {
		if (gameBoard != null) {
			gameBoard.enableTick = enableTick; //FIXME:
		}
	}
	
	public void setFrameTitle(String title) {
		this.setTitle(title);
	}
	
	public Canvas getBufGraph() {
		if (gameBoard != null) {
			return gameBoard.bgCanvas;
		}
		return null;
	}
	
	protected void onTick() {
		if (game != null) {
			game.work(this, this.getBufGraph());
		}
	}
	
	public void keyPressed(int x, int y) {
		if (game != null) {
			game.useDirection = false;
			game.pos_x = x;
			game.pos_y = y;
			game.sh_p = 1;
		}
	}

	public void keyReleased(int x, int y) {
		if (game != null) {
			game.useDirection = false;
			game.pos_x = x;
			game.pos_y = y;
			game.sh_p = 0;
		}
	}
	
	public void keyDraged(int x, int y) {
		keyPressed(x, y);
	}
}
