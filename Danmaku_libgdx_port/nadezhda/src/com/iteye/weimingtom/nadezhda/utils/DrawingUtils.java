package com.iteye.weimingtom.nadezhda.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Assets;

public class DrawingUtils {
	public static SpriteBatch spriteBatch;
	public static Pixmap pixmapRect;
	public static Texture textureRect;
	
    public static void loadFont() {
    	
    }

    public static void init() {
    	spriteBatch = new SpriteBatch();
    	pixmapRect = new Pixmap(800, 480, Pixmap.Format.RGBA8888);
    	textureRect = new Texture(1024, 1024, Pixmap.Format.RGBA8888);
    	textureRect.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
		textureRect.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
		
    }
    
    private DrawingUtils() {
        assert(false);
    }
    
	/*
    public static void drawRect(float x1, float y1, float x2, float y2) {
    	//FIXME:
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.f, 0.f); GL11.glVertex2f(x1, y1);
        GL11.glTexCoord2f(1.f, 0.f); GL11.glVertex2f(x2, y1);
        GL11.glTexCoord2f(1.f, 1.f); GL11.glVertex2f(x2, y2);
        GL11.glTexCoord2f(0.f, 1.f); GL11.glVertex2f(x1, y2);
        GL11.glEnd();   
    }
	 */
    public static void drawText(float x, float y, String text) {
    	Assets.font.setColor(Color.WHITE);
		Assets.font.draw(spriteBatch, text, x, Gdx.graphics.getHeight() - y);
    }

    public static void drawText(float x, float y, String text, Color color) {
    	Assets.font.setColor(color);
		Assets.font.draw(spriteBatch, text, x, Gdx.graphics.getHeight() - y);
    }
/*
    public static void translate(float x, float y) {
    	Gdx.gl11.glTranslatef(x, y, 0.0f);
    }

    public static void reset() {
        Gdx.gl11.glLoadIdentity();
    }
*/    
    public static void beginDraw() {
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.getProjectionMatrix().setToOrtho2D(
				0, 0, 
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
				//Application.FIELD_WIDTH, Application.FIELD_HEIGHT
				);
		spriteBatch.begin();
    }
    
    public static void endDraw() {
    	spriteBatch.end();
    }
    
    public static void drawTextureCenter(TextureRegion region, float x, float y, float rotation) {
		spriteBatch.draw(region,
    		x - region.getRegionWidth() * 0.5f, Gdx.graphics.getHeight() - region.getRegionHeight() - y + region.getRegionHeight() * 0.5f,
    		region.getRegionWidth() * 0.5f, region.getRegionHeight() * 0.5f, 
    		region.getRegionWidth(), region.getRegionHeight(), 
    		1f, 1f, -rotation);
    }
    
    public static void drawRectangle(int x, int y, int width, int height, Color color) {
    	if (false) {
    	pixmapRect.setColor(0);
    	pixmapRect.fill();
    	pixmapRect.setColor(color);
		pixmapRect.fillRectangle(0, 0, width, height);
		textureRect.draw(pixmapRect, 0, 0);
		TextureRegion region = new TextureRegion(textureRect, 0, 0, width, height);
    	spriteBatch.draw(region, x, Gdx.graphics.getHeight() - height - y);
		//pixmap.dispose();
    	}
    }
}

