package com.iteye.weimingtom.kikyajava;
import java.util.ArrayList;

/**
 * Represents an enemy 'spell card' as an array of bullets.
 * @author Administrator
 *
 */
public class SpellCard {
	public int ts;
	public int cs;
	public ArrayList<EnemyBullet> bullets;
	
	public SpellCard() {
		// total motion frames{
		this.ts = 100; 
		// current motion frame
		this.cs = 0; 
		// bullet array
		this.bullets = new ArrayList<EnemyBullet>(); 
	}
	
	public void addBullet(EnemyBullet bul) {
		this.bullets.add(bul);
	}
	
	// returns an array of bullets that must be fired in the current frame.
	public ArrayList<EnemyBullet> fire() {
		ArrayList<EnemyBullet> fired = new ArrayList<EnemyBullet>();
		for (int i = 0; i < this.bullets.size(); i++) {
			EnemyBullet b = this.bullets.get(i);
			// check if bullet must be fired in the current frame
			if (b.delay == this.cs) {
				// clone the bullet and return it
				EnemyBullet bc = new EnemyBullet(); 
				bc.w = b.w;
				bc.h = b.h;
				bc.image = b.image;
				bc.homing = b.homing;
				Movement mc = new Movement(b.movement.speed, 
						b.movement.sx, 
						b.movement.sy, 
						b.movement.ex, 
						b.movement.ey);
				bc.addMovement(mc);
				fired.add(bc);
			}
		}
		this.cs++;
		if (this.cs > this.ts) {
			this.cs = 0;
		}
		return fired;
	}
}
