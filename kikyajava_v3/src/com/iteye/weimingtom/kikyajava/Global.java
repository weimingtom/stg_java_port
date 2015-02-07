package com.iteye.weimingtom.kikyajava;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Global {
	public static final int canv_width = 300;
	public static final int canv_height = 400;
	public static Graphics g; 
	public static Main mainframe;
	
	public static int now;
	public static int score;
	//public static int interval;	
	
	public static Background bg;
	public static Player pg;
	public static Level level;
	
	public static ArrayList<Sprite> enemies;
	public static ArrayList<Sprite> bullets;
	public static ArrayList<Sprite> others;
	public static ArrayList<Sprite> powerups;
	
	public static int up_p;
	public static int dw_p;
	public static int lf_p;
	public static int rg_p;	
	
	public static int sh_p;
	public static int sm_p;
	
	public static int rn;
	
	public static BufferedImage img_ex;
	public static BufferedImage img_p;
	public static BufferedImage img_s;
	public static BufferedImage img_e1;
	public static BufferedImage img_e2;
	public static BufferedImage img_e3;
	public static BufferedImage img_e4;
	public static BufferedImage img_e5;
	public static BufferedImage img_b8a;
	public static BufferedImage img_b8b;
	public static BufferedImage img_b8c;
	public static BufferedImage img_b8d;
	public static BufferedImage img_b8e;
	public static BufferedImage img_b8f;
	public static BufferedImage img_b8g;
	public static BufferedImage img_b8h;
	public static BufferedImage img_b8i;
	public static BufferedImage img_b8j;
	public static BufferedImage img_b16a;
	public static BufferedImage img_b16b;
	public static BufferedImage img_b16c;
	public static BufferedImage img_b16d;
	public static BufferedImage img_b16e;
	public static BufferedImage img_b16f;
	public static BufferedImage img_b16g;
	public static BufferedImage img_b16h;
	public static BufferedImage img_b16i;
	public static BufferedImage img_b16j;
	
	public static BufferedImage img_pg;
	public static BufferedImage img_bp;
	
	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}
	
	public static void init() throws IOException {
		img_ex = loadImage("./images/098.png");
		img_p = loadImage("./images/199.png");
		img_s = loadImage("./images/056.png");
		img_b8a = loadImage("./images/b8a.png");
		img_b8b = loadImage("./images/b8b.png");
		img_b8c = loadImage("./images/b8c.png");
		img_b8d = loadImage("./images/b8d.png");
		img_b8e = loadImage("./images/b8e.png");
		img_b8f = loadImage("./images/b8f.png");
		img_b8g = loadImage("./images/b8g.png");
		img_b8h = loadImage("./images/b8h.png");
		img_b8i = loadImage("./images/b8i.png");
		img_b8j = loadImage("./images/b8j.png");
		img_b16a = loadImage("./images/b16a.png");
		img_b16b = loadImage("./images/b16b.png");
		img_b16c = loadImage("./images/b16c.png");
		img_b16d = loadImage("./images/b16d.png");
		img_b16e = loadImage("./images/b16e.png");
		img_b16f = loadImage("./images/b16f.png");
		img_b16g = loadImage("./images/b16g.png");
		img_b16h = loadImage("./images/b16h.png");
		img_b16i = loadImage("./images/b16i.png");
		img_b16j = loadImage("./images/b16j.png");
		img_bp = loadImage("./images/bp.png");
		img_pg = loadImage("./images/154.png");
		img_e1 = loadImage("./images/157.png");
		img_e2 = loadImage("./images/152.png");
		img_e3 = loadImage("./images/153.png");
		img_e4 = loadImage("./images/099.png");
		img_e5 = loadImage("./images/100.png");
	}
	
	
	// function to be called every frame, manages all the gameplay
	public static void work(Main mainframe) {
		// handle background
		bg.work();
		// handle player
		pg.work();
		// handle enemies
		for (int i = 0; i < enemies.size(); i++) {
			Sprite o = enemies.get(i);
			o.work();
			if (o.dead == 1) {
				enemies.remove(i);
			}
		}
		// handle bullets
		for (int i = 0; i < bullets.size(); i++) {
			Sprite o = bullets.get(i); 
			o.work();
			if (o.dead == 1) {
				bullets.remove(i);
			}
		}
		// handle powerups
		for (int i = 0; i < powerups.size(); i++) {
			Sprite o = powerups.get(i); 
			o.work();
			if (o.dead == 1) {
				powerups.remove(i);
			}
		}
		// handle other misc sprites
		for (int i = 0; i < others.size(); i++) {
			Sprite o = others.get(i); 
			o.work();
			if (o.dead == 1) {
				others.remove(i);
			}
		}
		// handle enemy spawning
		// get array of enemies spawn in the current frame
		ArrayList<Enemy> spawned = level.spawn(); 
		for (int i = 0; i < spawned.size(); i++) {
		    enemies.add(spawned.get(i));
		}
		// print stuff
		//FIXME:
		/*
		score_text.innerHTML = score;
		power_text.innerHTML = pg.power;
		oe_text.innerHTML = enemies.length;
		ob_text.innerHTML = bullets.length;
		op_text.innerHTML = powerups.length;
		*/
		// handle player death
		if (pg.dead == 1) {
			//clearInterval(interval);
			mainframe.setEnableTick(false);
			System.err.println("Game over!");
		}
		// level complete
		if (level.finished == 1 && 
			powerups.size() == 0 && 
			enemies.size() == 0) {
			echo("Level complete!");
		}
		//FIXME:
		// increment frame
		//frame_text.innerHTML = now++;
		Global.mainframe.setFrameTitle("" + score + 
				",pebu:" + pg.power + 
				"," + enemies.size() + 
				"," + bullets.size() +
				"," + powerups.size() + 
				">" + (now++));
	}
	
	public static void play(Main mainframe) {
		//clearInterval(interval);
		now = 0;
		score = 0;
		bg = new Background();
		pg = new Player();
		pg.image = Global.img_pg;
		pg.movement = new Movement(0, 
				bg.w / 2, 
				bg.h - 50, 
				0, 
				0);
		enemies = new ArrayList<Sprite>();
		bullets = new ArrayList<Sprite>();
		others = new ArrayList<Sprite>();
		powerups = new ArrayList<Sprite>();
		up_p = 0;
		dw_p = 0;
		lf_p = 0;
		rg_p = 0;
		sh_p = 0;
		sm_p = 0;
		// call to the level generation function, located in level1.js
		level = Level1.generateLevel1();
		echo("Level start!");
		// start main game loop
		//interval = setInterval(work, 20);
		mainframe.setEnableTick(true);
	}
	
	// print a big text in the middle of the screen
	public static void echo(String text) {
		Text t = new Text();
		t.text = text;
		t.size = 24;
		Movement m = new Movement(0, 
				bg.w / 2.0, 
				bg.h / 2.0, 
				0,
				0);
		m.tl = 100;
		t.addMovement(m);
		others.add(t);
	}
	
	// check if a rectangle intersects another
	public static boolean area_overlap(RectangleRegion area1, RectangleRegion area2) {
		return ((area2.x2 < area2.x1 || area2.x2 > area1.x1) &&
				(area2.y2 < area2.y1 || area2.y2 > area1.y1) &&
				(area1.x2 < area1.x1 || area1.x2 > area2.x1) &&
				(area1.y2 < area1.y1 || area1.y2 > area2.y1));
	}
	
	//FIXME:
	// generate random number between sn and en
	public static int rand(int sn, int en) {
		rn = (int)(Math.floor(Math.random() * (en - sn + 1)) + sn);
		return rn;
	}
}
