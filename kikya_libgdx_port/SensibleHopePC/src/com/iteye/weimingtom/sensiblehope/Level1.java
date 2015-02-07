package com.iteye.weimingtom.sensiblehope;

import java.util.ArrayList;

public class Level1 {
	public static Level generateLevel1() {
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
	public static Enemy createEnemy1(double d) {
		Enemy e = new Enemy();
		e.level = 1;
		e.life = 6;
		e.image = Global.img_e1;
		e.delay = d;
		return e;
	}

	// return a level 2 enemy, spawning at the specified frame.
	public static Enemy createEnemy2(double d) {
		Enemy e = new Enemy();
		e.level = 2;
		e.life = 20;
		e.image = Global.img_e2;
		e.delay = d;
		return e;
	}

	// return a level 3 enemy, spawning at the specified frame.
	public static Enemy createEnemy3(double d) {
		Enemy e = new Enemy();
		e.level = 3;
		e.life = 40;
		e.image = Global.img_e3;
		e.delay = d;
		return e;
	}

	// return a level 4 enemy, spawning at the specified frame.
	public static Enemy createEnemy4(double d) {
		Enemy e = new Enemy();
		e.level = 4;
		e.life = 80;
		e.image = Global.img_e4;
		e.delay = d;
		return e;
	}

	// return a level 5 enemy, spawning at the specified frame.
	public static Enemy createEnemy5(double d) {
		Enemy e = new Enemy();
		e.level = 5;
		e.life = 200;
		e.w = 16; 
		e.h = 16;
		e.image = Global.img_e5;
		e.delay = d;
		return e;
	}

	////////////////////////////////////////
	// MOVEMENTS
	////////////////////////////////////////

	// enter from top, do an X shape, exit from top, random position
	public static void move001(Enemy e) {
		double rn1 = Global.rand(20, Global.bg.w - 20);
		double rn2 = 0;
		double rn3 = Global.rand(50, 100);
		if (rn1 < Global.bg.w / 2) { 
			rn2 = rn1 + 100;
		} else {
			rn2 = rn1 - 100;
		}
		e.addMovement(new Movement(1, rn1, 0, rn1, rn3));
		e.addRelativeMovement(new Movement(1, 0, 0, rn2, rn3+100));
		e.addRelativeMovement(new Movement(1, 0, 0, rn1, rn3+100));
		e.addRelativeMovement(new Movement(1, 0, 0, rn2, rn3));
		e.addRelativeMovement(new Movement(1, 0, 0, rn2, 0));
	}

	// enter from left, exit from right, diagonal direction
	public static void move002a(Enemy e) {
		e.addMovement(new Movement(3, 
				0, 
				50, 
				Global.bg.w, 
				100));
	}

	// enter from right, exit from left, diagonal direction
	public static void move002b(Enemy e) {
		e.addMovement(new Movement(3, 
				Global.bg.w, 
				50, 
				0, 
				100));
	}

	// enter from top, exit from bottom, diagonal random direction
	public static void move003(Enemy e) {
		double rn1 = Global.rand(20, Global.bg.w - 20);
		double rn2 = Global.rand(100, Global.bg.w - 100);
		e.addMovement(new Movement(1, 
				rn1, 
				0, 
				rn2, 
				Global.bg.h));
	}

	// enter from top, do an U shape, exit from top, random points 
	public static void move004(Enemy e) {	
		int rn1 = Global.rand(20, Global.bg.w - 20);
		int rn2 = Global.bg.w - rn1;
		int rn3 = 0;
		if (rn1 < Global.bg.w / 2) { 
			rn3 = Global.rand(rn1, Global.bg.w / 2);
		} else {
			rn3 = Global.rand(Global.bg.w / 2, rn1);
		}
		double rn4 = Global.bg.w - rn3;
		double rnh = Global.rand(100, 150);
		e.addMovement(new Movement(1, 
				rn1, 
				0, 
				rn3, 
				rnh));
		e.addRelativeMovement(new Movement(1, 
				0, 
				0, 
				rn4, 
				rnh));
		e.addRelativeMovement(new Movement(1, 
				0, 
				0, 
				rn2, 
				0));
	}

	// enter from top, stay, exit from top
	public static void move005(Enemy e) {
		double rn = Global.rand(20, Global.bg.w - 20);
		e.addMovement(new Movement(1, 
				rn, 
				0, 
				rn, 
				120));
		Movement stay = new Movement(0, 
				0, 
				0, 
				0, 
				0);
		stay.tl = 200;
		e.addRelativeMovement(stay);
		e.addRelativeMovement(new Movement(1, 
				0, 
				0, 
				rn, 
				0));
	}

	// enter from top, zig zag to the bottom
	public static void move006(Enemy e) {
		double rn1 = Global.rand(20, Global.bg.w - 20);
		double rn2 = 0;
		if (rn1 < Global.bg.w / 2) {
			rn2 = rn1+100;
		} else {
			rn2 = rn1-100;
		}
		double h = 100;
		e.addMovement(new Movement(1, 
				rn1, 
				0, 
				rn1, 
				h));
		while (h < Global.bg.h) {
			h += 100;
			e.addRelativeMovement(new Movement(1, 
					0, 
					0, 
					rn2, 
					h));
			h += 100;
			e.addRelativeMovement(new Movement(1, 
					0, 
					0, 
					Global.rn,
					h));
		}
	}

	// enter from top, looping triangle
	public static void move007(Enemy e) {
		e.addMovement(new Movement(1, 
				Global.bg.w / 2, 
				0, 
				Global.bg.w / 2, 
				50));
		e.addLoopingMovement(new Movement(1, 
				0, 
				0, 
				Global.bg.w / 2 - 50, 
				150));
		e.addLoopingMovement(new Movement(1, 
				0, 
				0, 
				Global.bg.w / 2 + 50, 
				150));
		e.addLoopingMovement(new Movement(1, 
				0, 
				0, 
				Global.bg.w / 2, 
				50));
	}


	////////////////////////////////////////
	// SPELLCARDS
	////////////////////////////////////////

	// homing, 3 small shots
	public static void spellCard001(Enemy e){
		SpellCard s = new SpellCard();
		s.ts = 120;
		for (int i = 0; i < 3; i++) {
			EnemyBullet b1 = new EnemyBullet();
			b1.image = Global.img_b8g;
			b1.delay = 60 + (i * 20);
			b1.homing = 1;
			b1.addMovement(new Movement(2, 
					0, 
					0, 
					0, 
					0));
			s.addBullet(b1);
		}
		e.spellcard = s;
	}

	// homing, 10 small shots
	public static void spellCard002(Enemy e) {
		SpellCard s = new SpellCard();
		s.ts = 200;
		for (int i = 0; i < 10; i++) {
			EnemyBullet b1 = new EnemyBullet();
			b1.image = Global.img_b8g;
			b1.delay = 10 + (i * 5);
			b1.homing = 1;
			b1.addMovement(new Movement(2, 
					0, 
					0, 
					0, 
					0));
			s.addBullet(b1);
		}
		e.spellcard = s;
	}

	// 3 bursts in /|\ shape, 1 big shot followed by 4 small shots
	public static void spellCard003(Enemy e){
		SpellCard s = new SpellCard();
		s.ts = 150;
		for (int i = 0; i < 5; i++) {
			EnemyBullet b1 = new EnemyBullet();
			if (i == 0) {
				b1.image = Global.img_b16i;
				b1.w = 16; 
				b1.w = 16;
			} else {
				b1.image = Global.img_b8h;
			}
			b1.delay = 100 + (i * 8);
			b1.addMovement(new Movement(2, 
					0, 
					0, 
					-80, 
					Global.bg.h));
			s.addBullet(b1);
			EnemyBullet b2 = new EnemyBullet();
			if (i == 0) {
				b2.image = Global.img_b16i;
				b2.w = 16; 
				b2.w = 16;
			} else {
				b2.image = Global.img_b8h;
			}
			b2.delay = 100 + (i * 8);
			b2.addMovement(new Movement(2, 
					0, 
					0, 
					0, 
					Global.bg.h));
			s.addBullet(b2);
			EnemyBullet b3 = new EnemyBullet();
			if (i == 0) {
				b3.image = Global.img_b16i;
				b3.w = 16; 
				b3.w = 16;
			} else {
				b3.image = Global.img_b8h;
			}
			b3.delay = 100+(i*8);
			b3.addMovement(new Movement(2, 
					0, 
					0, 
					80, 
					Global.bg.h));
			s.addBullet(b3);
		}
		e.spellcard = s;
	}

	// homing, cloud of bullets
	public static void spellCard004(Enemy e) {
		SpellCard s = new SpellCard();
		s.ts = 80; 
		for (int i = 0; i < 30; i++) {
			EnemyBullet b1 = new EnemyBullet();
			b1.image = Global.img_b8d;
			b1.delay = i;
			b1.homing = 1;
			b1.addMovement(new Movement(2, 
					Global.rand(-10, 10), 
					Global.rand(-10, 10), 
					0, 
					0));
			s.addBullet(b1);
		}
		e.spellcard = s;
	}

	// single circle curtain
	public static void spellCard005(Enemy e) {
		SpellCard s = new SpellCard();
		s.ts = 125;
		ArrayList<PointPos> coords = getCircle();
		for (int i = 0; i < coords.size(); i++) {
			EnemyBullet b1 = new EnemyBullet();
			b1.image = Global.img_b8j;
			b1.delay = 100;
			b1.addMovement(new Movement(2, 
					0, 
					0, 
					coords.get(i).x, 
					coords.get(i).y));
			s.addBullet(b1);
		}
		e.spellcard = s;
	}

	// cosine flow curtain
	public static void spellCard006(Enemy e) {
		SpellCard s = new SpellCard();
		s.ts = 150;
		for (int i = 0; i <= 100; i++) {
			double nx = Math.cos(i / 5.0) * (i / 2.0);
			EnemyBullet b1 = new EnemyBullet();
			b1.image = Global.img_b8c;
			b1.delay = i;
			b1.addMovement(new Movement(2.5, 
					nx, 
					0, 
					nx, 
					Global.bg.h));
			s.addBullet(b1);
		}
		e.spellcard = s;
	}

	// semicircle curtain in random directions, 5 shots at a time
	public static void spellCard007(Enemy e) {
		SpellCard s = new SpellCard();
		s.ts = 150;
		ArrayList<PointPos> coords = getCircle();
		for (int i = 0; i < 10; i++) {
			int rn = Global.rand(0, (coords.size() / 2) - 6);
			for (int j = 0; j < 5; j++) {
				EnemyBullet b1 = new EnemyBullet();
				b1.image = Global.img_b8h;
				b1.delay = 50 + (i * 10);
				b1.addMovement(new Movement(2, 
						0, 
						0, 
						coords.get(rn + j).x, 
						coords.get(rn + j).y));
				s.addBullet(b1);
			}
		}
		e.spellcard = s;
	}

	// homing cannon, slightly random small bullets and straight big bullets
	public static void spellCard008(Enemy e) {
		SpellCard s = new SpellCard();
		s.ts = 250;
		for (int i = 0; i < 5; i++) {
			EnemyBullet b1 = new EnemyBullet();
			b1.image = Global.img_b8f;
			b1.delay = 10 + (i * 5);
			b1.homing = 1;
			b1.addMovement(new Movement(3, 
					Global.rand(-20, 20), 
					Global.rand(-20, 20), 
					Global.rand(-20, 20), 
					Global.rand(-20,20)));
			s.addBullet(b1);			
			EnemyBullet b2 = new EnemyBullet();
			b2.w = 16; 
			b2.h = 16;
			b2.image = Global.img_b16d;
			b2.delay = 10;
			b2.homing = 1;
			b2.addMovement(new Movement(5 - i, 
					0, 
					0, 
					0, 
					0));
			s.addBullet(b2);
		}
		e.spellcard = s;
	}

	// spiralling circle curtain + random big bullets
	public static void spellCard009(Enemy e) {
		SpellCard s = new SpellCard();
		s.ts = 240;
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
			EnemyBullet b1 = new EnemyBullet();
			b1.image = Global.img_b8b;
			b1.delay = 50 + (i * 2);
			b1.addMovement(new Movement(2, 
					0, 
					0, 
					coords.get(c1).x, 
					coords.get(c1).y));
			s.addBullet(b1);
			EnemyBullet b2 = new EnemyBullet();
			b2.image = Global.img_b8b;
			b2.delay = 50 + (i * 2);
			b2.addMovement(new Movement(2, 
					0, 
					0, 
					coords.get(c2).x, 
					coords.get(c2).y));
			s.addBullet(b2);
			EnemyBullet b3 = new EnemyBullet();
			b3.image = Global.img_b8b;
			b3.delay = 50 + (i * 2);
			b3.addMovement(new Movement(2, 
					0, 
					0, 
					coords.get(c3).x, 
					coords.get(c3).y));
			s.addBullet(b3);
			EnemyBullet b4 = new EnemyBullet();
			b4.image = Global.img_b8b;
			b4.delay = 50 + (i * 2);
			b4.addMovement(new Movement(2, 
					0, 
					0, 
					coords.get(c4).x, 
					coords.get(c4).y));
			s.addBullet(b4);
		}		
		for (int i = 0; i < 8; i++) {		
			EnemyBullet b1 = new EnemyBullet();
			b1.w = 16; 
			b1.h = 16;
			b1.image = Global.img_b16h;
			b1.delay = 60 + (i * 5);
			b1.homing = 1;
			b1.addMovement(new Movement(3, 
					0, 
					0, 
					Global.rand(-40, 40), 
					Global.rand(-40, 40)));
			s.addBullet(b1);
			EnemyBullet b2 = new EnemyBullet();
			b2.w = 16; 
			b2.h = 16;
			b2.image = Global.img_b16h;
			b2.delay = 180 + (i * 5);
			b2.homing = 1;
			b2.addMovement(new Movement(3, 
					0,
					0, 
					Global.rand(-40, 40), 
					Global.rand(-40, 40)));
			s.addBullet(b2);
		}
		e.spellcard = s;
	}

	////////////////////////////////////////
	// UTILITIES
	////////////////////////////////////////

	// returns the coordinates of 18 equidistant points on a circumference
	// centered in 0,0 with radius of 500
	public static ArrayList<PointPos> getCircle() {
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
}
