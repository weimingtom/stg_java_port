package com.iteye.weimingtom.kikyajava;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Sprite representing the active player. 
 * @author Administrator
 *
 */
public class Player extends Sprite {
	public int power;

	public Player() {
		this.w = 10;
		this.h = 10;
		this.image = null;
		this.color = new Color(255, 0, 0, 255);
		this.dead = 0;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		// shooting power
		this.power = 0; 
	}
	
	@Override
	public void move() {
		// normal movement
		double m = 5; 
		// slow movement
		if (Global.sm_p == 1) {
			m = 2; 
		}
		// diagonal movement
		if (Global.up_p + Global.dw_p + Global.lf_p + Global.rg_p > 1) {
	    	m = m * 0.7;
		}
		// refresh player position
		if (Global.up_p == 1) {
			this.movement.cy -= m;
		}
		if (Global.dw_p == 1) { 
			this.movement.cy += m;
		}
		if (Global.lf_p == 1) { 
			this.movement.cx -= m;
		}
		if (Global.rg_p == 1) { 
			this.movement.cx += m;
		}
		// avoid going out of the screen
		if (this.movement.cx >= Global.bg.w - Global.bg.padding) { 
			this.movement.cx = Global.bg.w - Global.bg.padding;
		}
		if (this.movement.cx <= Global.bg.padding) {
			this.movement.cx = Global.bg.padding;
		}
		if (this.movement.cy >= Global.bg.h - Global.bg.padding) {
			this.movement.cy = Global.bg.h - Global.bg.padding;
		}
		if (this.movement.cy <= Global.bg.padding) {
			this.movement.cy = Global.bg.padding;
		}
	}

	public void shoot() {	
		if (this.power < 10) {
			PlayerBullet b1 = new PlayerBullet();
			Movement m1 = new Movement(25, 
					this.movement.cx - 4, 
					this.movement.cy, 
					this.movement.cx - 4, 
					-10);
			b1.addMovement(m1);
			Global.bullets.add(b1);			
			PlayerBullet b2 = new PlayerBullet();
			Movement m2 = new Movement(25, 
					this.movement.cx + 4, 
					this.movement.cy, 
					this.movement.cx + 4, 
					-10);
			b2.addMovement(m2);
			Global.bullets.add(b2);
		} else if (this.power < 20) {
			PlayerBullet b1 = new PlayerBullet();
			Movement m1 = new Movement(25, 
					this.movement.cx - 5, 
					this.movement.cy, 
					this.movement.cx - 12, 
					-10);
			b1.addMovement(m1);
			Global.bullets.add(b1);
			PlayerBullet b2 = new PlayerBullet();
			Movement m2 = new Movement(25, 
					this.movement.cx + 5, 
					this.movement.cy, 
					this.movement.cx + 12, 
					-10);
			b2.addMovement(m2);
			Global.bullets.add(b2);
			PlayerBullet b3 = new PlayerBullet();
			Movement m3 = new Movement(25, this.movement.cx, this.movement.cy, this.movement.cx, -10);
			b3.addMovement(m3);
			Global.bullets.add(b3);
		} else if (this.power < 30) {
			PlayerBullet b1 = new PlayerBullet();
			Movement m1 = new Movement(25, 
					this.movement.cx - 4, 
					this.movement.cy, 
					this.movement.cx - 6, 
					-10);
			b1.addMovement(m1);
			Global.bullets.add(b1);
			PlayerBullet b2 = new PlayerBullet();
			Movement m2 = new Movement(25, 
					this.movement.cx + 4, 
					this.movement.cy, 
					this.movement.cx + 6, 
					-10);
			b2.addMovement(m2);
			Global.bullets.add(b2);
			PlayerBullet b3 = new PlayerBullet();
			Movement m3 = new Movement(25, 
					this.movement.cx - 8, 
					this.movement.cy, 
					this.movement.cx - 18, 
					-10);
			b3.addMovement(m3);
			Global.bullets.add(b3);
			PlayerBullet b4 = new PlayerBullet();
			Movement m4 = new Movement(25, 
					this.movement.cx + 8, 
					this.movement.cy, 
					this.movement.cx + 18, 
					-10);
			b4.addMovement(m4);
			Global.bullets.add(b4);
		} else {
			PlayerBullet b1 = new PlayerBullet();
			Movement m1 = new Movement(25, 
					this.movement.cx - 12, 
					this.movement.cy, 
					this.movement.cx - 30, 
					-10);
			b1.addMovement(m1);
			Global.bullets.add(b1);
			PlayerBullet b2 = new PlayerBullet();
			Movement m2 = new Movement(25, 
					this.movement.cx + 12, 
					this.movement.cy, 
					this.movement.cx + 30, 
					-10);
			b2.addMovement(m2);
			Global.bullets.add(b2);
			b1 = new PlayerBullet();
			m1 = new Movement(25, 
					this.movement.cx - 6, 
					this.movement.cy,
					this.movement.cx - 15, -10);
			b1.addMovement(m1);
			Global.bullets.add(b1);
			b2 = new PlayerBullet();
			m2 = new Movement(25, 
					this.movement.cx + 6, 
					this.movement.cy, 
					this.movement.cx + 15, 
					-10);
			b2.addMovement(m2);
			Global.bullets.add(b2);
			PlayerBullet b3 = new PlayerBullet();
			Movement m3 = new Movement(25, 
					this.movement.cx, 
					this.movement.cy, 
					this.movement.cx, 
					-10);
			b3.addMovement(m3);
			Global.bullets.add(b3);
		}
	}
	
	@Override
	public void work() {
		super.work();
		if (Global.sh_p == 1)
			this.shoot();
	}
}
