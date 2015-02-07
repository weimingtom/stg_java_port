package com.iteye.weimingtom.sensiblehope;

import java.io.IOException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * @see http://www.kikya.com/danmaku/
 * @author Administrator
 *
 */
public class Main implements Screen {
	private final static boolean DIRECTION_DOWN = true;
	
	private int LOG_LEVEL = Application.LOG_DEBUG;
	private final static String TAG = "Test002Screen"; 
	private FPSLogger logger;
	
	private SpriteBatch sb;
	private Pixmap pixmap;
	private Texture texture;
	private TextureRegion textureRegion;
	private BitmapFont font;
	
	private boolean enableTick = false;
	
	public Main() {
		Gdx.app.setLogLevel(LOG_LEVEL);
		logger = new FPSLogger();
		//FIXME:
		init();
	}

	private void init() {
		log("init");
		sb = new SpriteBatch();
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
		final int potW = MathUtils.nextPowerOfTwo(w);
		final int potH = MathUtils.nextPowerOfTwo(h);
		texture = new Texture(potW, potH, Pixmap.Format.RGBA8888);
		textureRegion = new TextureRegion(texture, 0, h, w, -h);
		font = new BitmapFont();
		font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		Global.mainframe = this;
		Global.sb = this.sb;
		if (false) {
			Global.pm = this.pixmap;
		}
		Global.font = this.font;
		try {
			Global.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Global.play(this);
	}
	
	@Override
	public void dispose() {
		log("dispose");
		texture.dispose();
		pixmap.dispose();
		sb.dispose();
	}
	
	public void keyPressed() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			Global.lf_p = 1;
		} else {
			Global.lf_p = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			Global.rg_p = 1;
		} else {
			Global.rg_p = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			Global.up_p = 1;
		} else {
			Global.up_p = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			Global.dw_p = 1;
		} else {
			Global.dw_p = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			Global.sh_p = 1;
		} else {
			Global.sh_p = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			Global.sm_p = 1;
		} else {
			Global.sm_p = 0;
		}
		if (Gdx.input.isTouched()) {
			Global.touch_p = true;
			Global.touch_x = Gdx.input.getX();
			Global.touch_y = Gdx.input.getY();
		} else {
			Global.touch_p = false;
		}
	}

	@Override
	public void render(float delta) {
		keyPressed();
		GL10 gl = Gdx.gl10;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//gl.glClearColor(0, 0, 0, 0);
		//gl.glClearColor(1, 1, 1, 1);
		gl.glClearColor(0.0f, 0.0f, 0.75f, 1.0f);
		sb.begin();
		if (enableTick) {
			onUpdate(delta);
		}
		sb.end();
		logger.log();
	}

	@Override
	public void resize(int width, int height) {
		if (DIRECTION_DOWN) {
			sb.getProjectionMatrix().setToOrtho2D(0, height, width, -height);	
		} else {
			sb.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		}
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	private void onUpdate(float delta) {
		Global.work(this);
	}
	
	private void log(String message) {
		Gdx.app.log(TAG, message);
	}
	
	public void drawPixmap() {
		texture.draw(pixmap, 0, 0);
		sb.draw(textureRegion, 0, 0);
	}
	
	public void setEnableTick(boolean enable) {
		this.enableTick = enable;
	}
	
	public void setFrameTitle(String title) {
		Gdx.graphics.setTitle(title);
	}
}
