package com.iteye.weimingtom.kikyajava.global;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.Level;
import com.iteye.weimingtom.kikyajava.Movement;
import com.iteye.weimingtom.kikyajava.PointPos;
import com.iteye.weimingtom.kikyajava.SpellCard;
import com.iteye.weimingtom.kikyajava.item.Background;
import com.iteye.weimingtom.kikyajava.item.Enemy;
import com.iteye.weimingtom.kikyajava.item.EnemyBullet;
import com.iteye.weimingtom.kikyajava.item.Explosion;
import com.iteye.weimingtom.kikyajava.item.Player;
import com.iteye.weimingtom.kikyajava.item.PlayerBullet;
import com.iteye.weimingtom.kikyajava.item.Powerup;
import com.iteye.weimingtom.kikyajava.item.Text;

public class Game { 
	public int score;
	public Player pg;
	public Enemy enemies;
	public int enemies_size;
	public EnemyBullet enemybullets;
	public int enemybullets_size;
	public PlayerBullet playerbullets;
	public int playerbullets_size;
	public Explosion explosions;
	public int explosions_size;
	public Powerup powerups;
	public int powerups_size;
	public Text texts;
	public int texts_size;
	
	public int up_p;
	public int dw_p;
	public int lf_p;
	public int rg_p;	
	public int sh_p;
	public int sm_p;

	private int img_ex;
	private int img_p;
	private int img_s;
	private int img_e1;
	private int img_e2;
	private int img_e3;
	private int img_e4;
	private int img_e5;
	private int img_b8b;
	private int img_b8c;
	private int img_b8d;
	private int img_b8f;
	private int img_b8g;
	private int img_b8h;
	private int img_b8j;
	private int img_b16d;
	private int img_b16h;
	private int img_b16i;
	private int img_pg;
	private int img_bp;

	private int canv_width, canv_height;
	private int now;
	private Background bg;
	private Level level;
	private GameGraph graph;

	
	public Game(int w, int h) throws IOException {
		graph = new GameGraph();
		canv_width = w;
		canv_height = h;
		img_ex = graph.loadImage("./images/098.png");
		img_p = graph.loadImage("./images/199.png");
		img_s = graph.loadImage("./images/056.png");
		img_b8b = graph.loadImage("./images/b8b.png");
		img_b8c = graph.loadImage("./images/b8c.png");
		img_b8d = graph.loadImage("./images/b8d.png");
		img_b8f = graph.loadImage("./images/b8f.png");
		img_b8g = graph.loadImage("./images/b8g.png");
		img_b8h = graph.loadImage("./images/b8h.png");
		img_b8j = graph.loadImage("./images/b8j.png");
		img_b16d = graph.loadImage("./images/b16d.png");
		img_b16h = graph.loadImage("./images/b16h.png");
		img_b16i = graph.loadImage("./images/b16i.png");
		img_bp = graph.loadImage("./images/bp.png");
		img_pg = graph.loadImage("./images/154.png");
		img_e1 = graph.loadImage("./images/157.png");
		img_e2 = graph.loadImage("./images/152.png");
		img_e3 = graph.loadImage("./images/153.png");
		img_e4 = graph.loadImage("./images/099.png");
		img_e5 = graph.loadImage("./images/100.png");
	}
	
	public void work(MainFrame mainframe, Graphics g) {
		bg.work(this, graph.initGraph(g), canv_width, canv_height);
		pg.work(this, graph.initGraph(g), canv_width, canv_height);
		Enemy preEnemy = null;
		for (Enemy o = enemies; o != null; preEnemy = o, o = o.getNextNode()) {
			o.work(this, graph.initGraph(g), canv_width, canv_height);
			if (o.isDead() == true) {
				if (preEnemy != null) {
					preEnemy.setNextNode(o.getNextNode());
				} else {
					enemies = o.getNextNode();
				}
				o.setNextNode(null);
				enemies_size--;
			}
		}
		// handle bullets
		EnemyBullet preEnemyBullet = null;
		for (EnemyBullet o = enemybullets; o != null; preEnemyBullet = o, o = o.getNextNode()) {
			o.work(this, graph.initGraph(g), canv_width, canv_height);
			if (o.isDead() == true) {
				if (preEnemyBullet != null) {
					preEnemyBullet.setNextNode(o.getNextNode());
				} else {
					enemybullets = o.getNextNode();
				}
				o.setNextNode(null);
				enemybullets_size--;
			}
		}
		PlayerBullet prePlayerBullet = null;
		for (PlayerBullet o = playerbullets; o != null; prePlayerBullet = o, o = o.getNextNode()) {
			o.work(this, graph.initGraph(g), canv_width, canv_height);
			if (o.isDead() == true) {
				if (prePlayerBullet != null) {
					prePlayerBullet.setNextNode(o.getNextNode());
				} else {
					playerbullets = o.getNextNode();
				}
				o.setNextNode(null);
				playerbullets_size--;
			}
		}
		Powerup prePowerup = null;
		for (Powerup o = powerups; o != null; prePowerup = o, o = o.getNextNode()) {
			o.work(this, graph.initGraph(g), canv_width, canv_height);
			if (o.isDead() == true) {
				if (prePowerup != null) {
					prePowerup.setNextNode(o.getNextNode());
				} else {
					powerups = o.getNextNode();
				}
				o.setNextNode(null);
				powerups_size--;
			}
		}
		Explosion preExplosion = null;
		for (Explosion o = explosions; o != null; preExplosion = o, o = o.getNextNode()) {
			o.work(this, graph.initGraph(g), canv_width, canv_height);
			if (o.isDead() == true) {
				if (preExplosion != null) {
					preExplosion.setNextNode(o.getNextNode());
				} else {
					explosions = o.getNextNode();
				}
				o.setNextNode(null);
				explosions_size--;
			}
		}
		Text preText = null;
		for (Text o = texts; o != null; preText = o, o = o.getNextNode()) {
			o.work(this, graph.initGraph(g), canv_width, canv_height);
			if (o.isDead() == true) {
				if (preText != null) {
					preText.setNextNode(o.getNextNode());
				} else {
					texts = o.getNextNode();
				}
				o.setNextNode(null);
				texts_size--;
			}
		}
		// handle enemy spawning
		// get array of enemies spawn in the current frame
		ArrayList<Enemy> spawned = level.spawn(); 
		for (int i = 0; i < spawned.size(); i++) {
			addEnemies(spawned.get(i));
		}
		// handle player death
		if (pg.isDead() == true) {
			//clearInterval(interval);
			mainframe.setEnableTick(false);
			System.err.println("Game over!");
		}
		// level complete
		if (level.finished == 1 && 
			powerups_size == 0 && 
			enemies_size == 0) {
			echo("Level complete!");
		}
		mainframe.setFrameTitle("" + score + 
				",pebu:" + pg.getPower() + 
				"," + enemies_size + 
				"," + (enemybullets_size + playerbullets_size) +
				"," + powerups_size + 
				">" + (now++));
	}
	
	public void play(MainFrame mainframe) {
		//clearInterval(interval);
		now = 0;
		score = 0;
		bg = new Background();
		pg = new Player(img_pg, canv_width / 2, canv_height - 50, img_bp);
		enemies = null;
		enemies_size = 0;
		enemybullets = null;
		enemybullets_size = 0;
		playerbullets = null;
		playerbullets_size = 0;
		explosions = null;
		explosions_size = 0;
		texts = null;
		texts_size = 0;
		powerups = null;
		powerups_size = 0;
		up_p = 0;
		dw_p = 0;
		lf_p = 0;
		rg_p = 0;
		sh_p = 0;
		sm_p = 0;
		// call to the level generation function, located in level1.js
		level = generateLevel1();
		echo("Level start!");
		// start main game loop
		//interval = setInterval(work, 20);
		mainframe.setEnableTick(true);
	}
	
	// print a big text in the middle of the screen
	private void echo(String text) {
		Text t = new Text(text);
		t.addMovement(new Movement(0, 
				canv_width / 2.0, canv_height / 2.0, 
				0, 0,
				false, false, 100));
		addTexts(t);
	}
	
	private Level generateLevel1() {
		Level level = new Level();
		Enemy e;
		for (int i = 200; i <= 800; i += 50) {
			e = createEnemy1(i);
			move001(e);
			spellCard001(e);
			level.addEnemy(e);
		}
		for (int i = 1200; i <= 1400; i += 20) {
			e = createEnemy1(i);
			move002a(e);
			spellCard002(e);
			level.addEnemy(e);
		}
		for (int i = 1500; i <= 1700; i += 20) {
			e = createEnemy1(i);
			move002b(e);
			spellCard002(e);
			level.addEnemy(e);
		}
		for (int i = 1900; i <= 2200; i += 100) {
			e = createEnemy2(i);
			move004(e);
			spellCard003(e);
			level.addEnemy(e);
		}
		for (int i = 2400; i <= 2700; i += 100) {
			e = createEnemy2(i);
			move004(e);
			spellCard004(e);
			level.addEnemy(e);
		}
		for (int i = 2800; i <= 3000; i += 150) {
			e = createEnemy3(i);
			move001(e);
			spellCard005(e);
			level.addEnemy(e);
		}
		for (int i = 3400; i <= 3800; i += 75) {
			e = createEnemy2(i);
			move006(e);
			spellCard008(e);
			level.addEnemy(e);
		}
		for (int i = 4000; i <= 4300; i += 100) {
			e = createEnemy4(i);
			move005(e);
			spellCard007(e);
			level.addEnemy(e);
		}
		for (int i = 4600; i <= 4900; i += 100) {
			e = createEnemy2(i);
			move005(e);
			spellCard006(e);
			level.addEnemy(e);
		}
		e = createEnemy5(5000);
		move007(e);
		spellCard009(e);
		level.addEnemy(e);
		return level;
	}

	////////////////////////////////////////
	// ENEMIES
	////////////////////////////////////////

	// return a level 1 enemy, spawning at the specified frame.
	private Enemy createEnemy1(double d) {
		return new Enemy(10, 10, 1, 6, img_e1, d, img_ex, img_p, img_s);
	}

	// return a level 2 enemy, spawning at the specified frame.
	private Enemy createEnemy2(double d) {
		return new Enemy(10, 10, 2, 20, img_e2, d, img_ex, img_p, img_s);
	}

	// return a level 3 enemy, spawning at the specified frame.
	private Enemy createEnemy3(double d) {
		return new Enemy(10, 10, 3, 40, img_e3, d, img_ex, img_p, img_s);
	}

	// return a level 4 enemy, spawning at the specified frame.
	private Enemy createEnemy4(double d) {
		return new Enemy(10, 10, 4, 80, img_e4, d, img_ex, img_p, img_s);
	}

	// return a level 5 enemy, spawning at the specified frame.
	private Enemy createEnemy5(double d) {
		return new Enemy(16, 16, 5, 200, img_e5, d, img_ex, img_p, img_s);
	}

	////////////////////////////////////////
	// MOVEMENTS
	////////////////////////////////////////

	// enter from top, do an X shape, exit from top, random position
	private void move001(Enemy e) {
		double rn1 = rand(20, canv_width - 20);
		double rn2 = 0;
		double rn3 = rand(50, 100);
		if (rn1 < canv_width / 2) { 
			rn2 = rn1 + 100;
		} else {
			rn2 = rn1 - 100;
		}
		e.addMovement(new Movement(1, rn1, 0, rn1, rn3, false, false, 0));
		e.addRelativeMovement(new Movement(1, 0, 0, rn2, rn3+100, false, false, 0));
		e.addRelativeMovement(new Movement(1, 0, 0, rn1, rn3+100, false, false, 0));
		e.addRelativeMovement(new Movement(1, 0, 0, rn2, rn3, false, false, 0));
		e.addRelativeMovement(new Movement(1, 0, 0, rn2, 0, false, false, 0));
	}

	private void move002a(Enemy e) {
		e.addMovement(new Movement(3, 0, 50, canv_width, 100, false, false, 0));
	}

	private void move002b(Enemy e) {
		e.addMovement(new Movement(3, canv_width, 50, 0, 100, false, false, 0));
	}

	private void move004(Enemy e) {	
		int rn1 = rand(20, canv_width - 20);
		int rn2 = canv_width - rn1;
		int rn3 = 0;
		if (rn1 < canv_width / 2) { 
			rn3 = rand(rn1, canv_width / 2);
		} else {
			rn3 = rand(canv_width / 2, rn1);
		}
		double rn4 = canv_width - rn3;
		double rnh = rand(100, 150);
		e.addMovement(new Movement(1, rn1, 0, rn3, rnh, false, false, 0));
		e.addRelativeMovement(new Movement(1, 0, 0, rn4, rnh, false, false, 0));
		e.addRelativeMovement(new Movement(1, 0, 0, rn2, 0, false, false, 0));
	}

	// enter from top, stay, exit from top
	private void move005(Enemy e) {
		double rn = rand(20, canv_width - 20);
		e.addMovement(new Movement(1, rn, 0, rn, 120, false, false, 0));
		Movement stay = new Movement(0, 0, 0, 0, 0, false, false, 200);
		e.addRelativeMovement(stay);
		e.addRelativeMovement(new Movement(1, 0, 0, rn, 0, false, false, 0));
	}

	// enter from top, zig zag to the bottom
	private void move006(Enemy e) {
		double rn1 = rand(20, canv_width - 20);
		double rn2 = 0;
		if (rn1 < canv_width / 2) {
			rn2 = rn1 + 100;
		} else {
			rn2 = rn1 - 100;
		}
		double h = 100;
		e.addMovement(new Movement(1, rn1, 0, rn1, h, false, false, 0));
		while (h < canv_height) {
			h += 100;
			e.addRelativeMovement(new Movement(1, 0, 0, rn2, h, false, false, 0));
			h += 100;
			e.addRelativeMovement(new Movement(1, 0, 0, rn1, h, false, false, 0));
		}
	}

	// enter from top, looping triangle
	private void move007(Enemy e) {
		e.addMovement(new Movement(1, canv_width / 2, 0, canv_width / 2, 50, false, false, 0));
		e.addLoopingMovement(new Movement(1, 0, 0, canv_width / 2 - 50, 150, false, false, 0));
		e.addLoopingMovement(new Movement(1, 0, 0, canv_width / 2 + 50, 150, false, false, 0));
		e.addLoopingMovement(new Movement(1, 0, 0, canv_width / 2, 50, false, false, 0));
	}

	////////////////////////////////////////
	// SPELLCARDS
	////////////////////////////////////////

	// homing, 3 small shots
	private void spellCard001(Enemy e){
		SpellCard s = new SpellCard(120);
		for (int i = 0; i < 3; i++) {
			EnemyBullet b1 = new EnemyBullet(8, 8, img_b8g, 60 + (i * 20), true);
			b1.addMovement(new Movement(2, 0, 0, 0, 0, false, false, 0));
			s.addBullet(b1);
		}
		e.setSpellcard(s);
	}

	// homing, 10 small shots
	private void spellCard002(Enemy e) {
		SpellCard s = new SpellCard(200);
		for (int i = 0; i < 10; i++) {
			EnemyBullet b1 = new EnemyBullet(8, 8, img_b8g, 10 + (i * 5), true);
			b1.addMovement(new Movement(2, 0, 0, 0, 0, false, false, 0));
			s.addBullet(b1);
		}
		e.setSpellcard(s);
	}

	// 3 bursts in /|\ shape, 1 big shot followed by 4 small shots
	private void spellCard003(Enemy e){
		SpellCard s = new SpellCard(150);
		for (int i = 0; i < 5; i++) {
			EnemyBullet b1;
			if (i == 0) {
				b1 = new EnemyBullet(16, 16, img_b16i, 100 + (i * 8), false);
			} else {
				b1 = new EnemyBullet(8, 8, img_b8h, 100 + (i * 8), false);
			}
			b1.addMovement(new Movement(2, 0, 0, -80, canv_height, false, false, 0));
			s.addBullet(b1);
			EnemyBullet b2;
			if (i == 0) {
				b2 = new EnemyBullet(16, 16, img_b16i, 100 + (i * 8), false);
			} else {
				b2 = new EnemyBullet(8, 8, img_b8h, 100 + (i * 8), false);
			}
			b2.addMovement(new Movement(2, 0, 0, 0, canv_height, false, false, 0));
			s.addBullet(b2);
			EnemyBullet b3;
			if (i == 0) {
				b3 = new EnemyBullet(16, 16, img_b16i, 100 + (i * 8), false);
			} else {
				b3 = new EnemyBullet(8, 8, img_b8h, 100 + (i * 8), false);
			}
			b3.addMovement(new Movement(2, 0, 0, 80, canv_height, false, false, 0));
			s.addBullet(b3);
		}
		e.setSpellcard(s);
	}

	// homing, cloud of bullets
	private void spellCard004(Enemy e) {
		SpellCard s = new SpellCard(80);
		for (int i = 0; i < 30; i++) {
			EnemyBullet b1 = new EnemyBullet(8, 8, img_b8d, i, true);
			b1.addMovement(new Movement(2, rand(-10, 10), rand(-10, 10), 0, 0, false, false, 0));
			s.addBullet(b1);
		}
		e.setSpellcard(s);
	}

	// single circle curtain
	private void spellCard005(Enemy e) {
		SpellCard s = new SpellCard(125);
		ArrayList<PointPos> coords = getCircle();
		for (int i = 0; i < coords.size(); i++) {
			EnemyBullet b1 = new EnemyBullet(8, 8, img_b8j, 100, false);
			b1.addMovement(new Movement(2, 0, 0, coords.get(i).x, coords.get(i).y, false, false, 0));
			s.addBullet(b1);
		}
		e.setSpellcard(s);
	}

	// cosine flow curtain
	private void spellCard006(Enemy e) {
		SpellCard s = new SpellCard(150);
		for (int i = 0; i <= 100; i++) {
			double nx = Math.cos(i / 5.0) * (i / 2.0);
			EnemyBullet b1 = new EnemyBullet(8, 8, img_b8c, i, false);
			b1.addMovement(new Movement(2.5, nx, 0, nx, canv_height, false, false, 0));
			s.addBullet(b1);
		}
		e.setSpellcard(s);
	}

	// semicircle curtain in random directions, 5 shots at a time
	private void spellCard007(Enemy e) {
		SpellCard s = new SpellCard(150);
		ArrayList<PointPos> coords = getCircle();
		for (int i = 0; i < 10; i++) {
			int rn = rand(0, (coords.size() / 2) - 6);
			for (int j = 0; j < 5; j++) {
				EnemyBullet b1 = new EnemyBullet(8, 8, img_b8h, 50 + (i * 10), false);
				b1.addMovement(new Movement(2, 0, 0, coords.get(rn + j).x, coords.get(rn + j).y, false, false, 0));
				s.addBullet(b1);
			}
		}
		e.setSpellcard(s);
	}

	// homing cannon, slightly random small bullets and straight big bullets
	private void spellCard008(Enemy e) {
		SpellCard s = new SpellCard(250);
		for (int i = 0; i < 5; i++) {
			EnemyBullet b1 = new EnemyBullet(8, 8, img_b8f, 10 + (i * 5), true);
			b1.addMovement(new Movement(3, 
				rand(-20, 20), rand(-20, 20), 
				rand(-20, 20), rand(-20,20),
				false, false, 0));
			s.addBullet(b1);			
			EnemyBullet b2 = new EnemyBullet(16, 16, img_b16d, 10, true);
			b2.addMovement(new Movement(5 - i, 0, 0, 0, 0, false, false, 0));
			s.addBullet(b2);
		}
		e.setSpellcard(s);
	}

	// spiralling circle curtain + random big bullets
	private void spellCard009(Enemy e) {
		SpellCard s = new SpellCard(240);
		ArrayList<PointPos> coords = getCircle();
		int c1 = 0;
		int c2 = 10;
		int c3 = 20;
		int c4 = 30;
		for (int i = 0; i < 80; i++) {
			c1++;
			c1 = c1 % coords.size();
			c2++;
			c2 = c2 % coords.size();
			c3++;
			c3 = c3 % coords.size();
			c4++;
			c4 = c4 % coords.size();
			EnemyBullet b1 = new EnemyBullet(8, 8, img_b8b, 50 + (i * 2), false);
			b1.addMovement(new Movement(2, 0, 0, coords.get(c1).x, coords.get(c1).y, false, false, 0));
			s.addBullet(b1);
			EnemyBullet b2 = new EnemyBullet(8, 8, img_b8b, 50 + (i * 2), false);
			b2.addMovement(new Movement(2, 0, 0, coords.get(c2).x, coords.get(c2).y, false, false, 0));
			s.addBullet(b2);
			EnemyBullet b3 = new EnemyBullet(8, 8, img_b8b, 50 + (i * 2), false);
			b3.addMovement(new Movement(2, 0, 0, coords.get(c3).x, coords.get(c3).y, false, false, 0));
			s.addBullet(b3);
			EnemyBullet b4 = new EnemyBullet(8, 8, img_b8b, 50 + (i * 2), false);
			b4.addMovement(new Movement(2, 0, 0, coords.get(c4).x, coords.get(c4).y, false, false, 0));
			s.addBullet(b4);
		}		
		for (int i = 0; i < 8; i++) {		
			EnemyBullet b1 = new EnemyBullet(16, 16, img_b16h, 60 + (i * 5), true);
			b1.addMovement(new Movement(3, 0, 0, rand(-40, 40), rand(-40, 40), false, false, 0));
			s.addBullet(b1);
			EnemyBullet b2 = new EnemyBullet(16, 16, img_b16h, 180 + (i * 5), true);
			b2.addMovement(new Movement(3, 0, 0, rand(-40, 40), rand(-40, 40), false, false, 0));
			s.addBullet(b2);
		}
		e.setSpellcard(s);
	}

	////////////////////////////////////////
	// UTILITIES
	////////////////////////////////////////

	// returns the coordinates of 18 equidistant points on a circumference
	// centered in 0,0 with radius of 500
	private static ArrayList<PointPos> getCircle() {
		ArrayList<PointPos> coords = new ArrayList<PointPos>();
		int rr = 500;
		for (int xx = 0; xx < rr * 2; xx += 50) {
			double nx = Math.cos(Math.PI * (xx / (rr * 2))) * rr;
			double ny = Math.sqrt(Math.pow(rr, 2) - Math.pow(nx, 2));
			coords.add(new PointPos(nx, ny));
		}
		for (int xx = rr * 2; xx > 0; xx -= 50) {
			double nx = Math.cos(Math.PI*(xx/(rr*2)))*rr;
			double ny = Math.sqrt(Math.pow(rr,2)-Math.pow(nx,2));
			coords.add(new PointPos(nx, -ny));
		}
		return coords;
	}
	
	private static int rand(int sn, int en) {
		return (int)(Math.floor(Math.random() * (en - sn + 1)) + sn);
	}

	public void addEnemyBullets(EnemyBullet eb) {
		eb.setNextNode(enemybullets);
		enemybullets = eb;
		enemybullets_size++;			
	}
	
	public void addExplosions(Explosion ex) {
		ex.setNextNode(explosions);
		explosions = ex;
		explosions_size++;			
	}
	
	public void addPlayerBullets(PlayerBullet pb) {
		pb.setNextNode(playerbullets);
		playerbullets = pb;
		playerbullets_size++;			
	}
	
	public void addEnemies(Enemy e) {
		e.setNextNode(enemies);
		enemies = e;
		enemies_size++;		
	}
	
	public void addTexts(Text t) {
		t.setNextNode(texts);
		texts = t;
		texts_size++;
	}
	
	public void addPowerups(Powerup p) {
		p.setNextNode(powerups);
		powerups = p;
		powerups_size++;
	}
}
