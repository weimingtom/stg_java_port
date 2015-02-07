package com.iteye.weimingtom.nadezhda.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture texture;
	public static BitmapFont font;
	
    public static Texture ship;
    public static Texture straightBullet;
    public static Texture homingBullet;
    public static Texture enemy1;
    public static Texture enemy2;
    public static Texture smallParticle;
    public static Texture fire1, fire2, fire3;

    public static TextureRegion shipRegion;
    public static TextureRegion straightBulletRegion;
    public static TextureRegion homingBulletRegion;
    public static TextureRegion enemy1Region;
    public static TextureRegion enemy2Region;
    public static TextureRegion smallParticleRegion;
    public static TextureRegion fire1Region, fire2Region, fire3Region;
    
	public static void load () {
		font = new BitmapFont();
		font.setColor(Color.RED);
		texture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		
        ship = new Texture(Gdx.files.internal("data/ship.png"));
        shipRegion = new TextureRegion(ship);
        
        straightBullet = new Texture(Gdx.files.internal("data/bullet.png"));
        straightBulletRegion = new TextureRegion(straightBullet);
        
        homingBullet = new Texture(Gdx.files.internal("data/bigbullet.png"));
        homingBulletRegion = new TextureRegion(homingBullet);
        
        enemy1 = new Texture(Gdx.files.internal("data/enemy1.png"));
        enemy1Region = new TextureRegion(enemy1);
        
        enemy2 = new Texture(Gdx.files.internal("data/enemy2.png"));
        enemy2Region = new TextureRegion(enemy2);
        
        smallParticle = new Texture(Gdx.files.internal("data/smallparticle.png"));
        smallParticleRegion = new TextureRegion(smallParticle);
        
        fire1 = new Texture(Gdx.files.internal("data/fire1.png"));
        fire1Region = new TextureRegion(fire1);
        
        fire2 = new Texture(Gdx.files.internal("data/fire2.png"));
        fire2Region = new TextureRegion(fire2);
        
        fire3 = new Texture(Gdx.files.internal("data/fire3.png"));
        fire3Region = new TextureRegion(fire3);
	}
}
