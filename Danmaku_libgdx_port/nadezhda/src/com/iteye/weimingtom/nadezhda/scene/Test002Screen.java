package com.iteye.weimingtom.nadezhda.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;

public class Test002Screen implements Screen {
	
	public Test002Screen(Game game) {
		DrawingUtils.init();
	}
	
	@Override
	public void render(float delta) {
    	int m_PosX, m_PosY;
		if (Gdx.input.isTouched()) {
    		m_PosX = Gdx.input.getX();
    		m_PosY = Gdx.input.getY();
    		System.out.println("x:" + m_PosX + ", y:" + m_PosY);
    	}
		DrawingUtils.beginDraw();
		DrawingUtils.drawRectangle(64, 64, 64, 64, Color.BLUE);
		DrawingUtils.drawTextureCenter(Assets.shipRegion, 32, 32, 90);
		DrawingUtils.drawTextureCenter(Assets.shipRegion, 32, 32, 0);
		DrawingUtils.drawText(0, 0, "hello, world!");
		DrawingUtils.endDraw();
	}

	@Override
	public void resize(int width, int height) {
		
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

	@Override
	public void dispose() {
		
	}
}
