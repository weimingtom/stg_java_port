package com.iteye.weimingtom.libgdxtest;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Test001Screen implements Screen {
	private final static boolean DIRECTION_DOWN = true;
	private final static int BOX_W = 50;
	private final static int BOX_H = 50;
	
	private int LOG_LEVEL = Application.LOG_DEBUG;
	private final static String TAG = "Test001Screen"; 
	private FPSLogger logger;
	
	private SpriteBatch sb;
	private Pixmap pixmap;
	private Texture texture;
	private TextureRegion textureRegion;
	private Vector2 pos;
	private Vector2 dir;

	public Test001Screen() {
		Gdx.app.setLogLevel(LOG_LEVEL);
		logger = new FPSLogger();
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
		pos = new Vector2(w / 2, h / 2);
		dir = new Vector2(1, 1);
	}
	
	@Override
	public void dispose() {
		log("dispose");
		texture.dispose();
		pixmap.dispose();
		sb.dispose();
	}
	
	@Override
	public void render(float delta) {
		onUpdate(delta);
		GL10 gl = Gdx.gl10;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glClearColor(0, 0, 0, 0);
		//gl.glClearColor(1, 1, 1, 1);
		onRender();
		sb.begin();
		texture.draw(pixmap, 0, 0);
		sb.draw(textureRegion, 0, 0);
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
		int w = pixmap.getWidth();
		int h = pixmap.getHeight();
		
		pos.x += dir.x * delta * 60;
		pos.y += dir.y * delta * 60;
		if (pos.x < 0) {
			dir.x = -dir.x;
			pos.x = 0;
		}
		if (pos.x > w) {
			dir.x = -dir.x;
			pos.x = w;
		}
		if (pos.y < 0) {
			dir.y = -dir.y;
			pos.y = 0;
		}
		if (pos.y > h) {
			dir.y = -dir.y;
			pos.y = h;
		}
	}
	
	private void onRender() {
		pixmap.setColor(0, 0, 0, 0);
		pixmap.fill();
		pixmap.setColor(Color.BLUE);
		pixmap.fillRectangle(
			(int)pos.x - BOX_W / 2, (int)pos.y - BOX_H / 2, 
			BOX_W, BOX_H);
	}
	
	private void log(String message) {
		Gdx.app.log(TAG, message);
	}
}
