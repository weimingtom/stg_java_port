package com.iteye.weimingtom.nadezhda.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Settings;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;

public class MainMenuScreen implements Screen {
	private Application app;
	
	public MainMenuScreen(Game game) {
		DrawingUtils.init();
		app = new Application();
		app.initialize();
		app.initLevel();
	}
	
	private long lastTime = 0;
	@Override
	public void render(float delta) {
		//Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		long currentTime = app.getTime();
		if (currentTime - lastTime > 1000 / 120) {
			lastTime = currentTime;
			
			DrawingUtils.beginDraw();
			app.onRender();
			DrawingUtils.endDraw();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		Application.FIELD_WIDTH = (float)width;
		Application.FIELD_HEIGHT = (float)height;
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		Settings.save();
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}
