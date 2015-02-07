package com.iteye.weimingtom.sensiblehope;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

/**
 * Sprite representing an emeny. 
 * @author Administrator
 *
 */
public class Enemy extends GameSprite {
	public int hit;
	public int life;
	public int level;
	public SpellCard spellcard;
	public double delay;
	
	public Enemy() {
		this.w = 10;
		this.h = 10;
		this.image = null;
		this.color = new Color(0.0f, 0.5f, 0.0f, 1.0f);
		this.dead = 0;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		// indicates if the enemy has been hit this frame
		this.hit = 0; 
		// life points
		this.life = 10; 
		// enemy level (1 to 5) to calculate drops and score.
		this.level = 1; 
		// shooting pattern
		this.spellcard = null;
		// spawn delay
		this.delay = 0; 
	}
	
	@Override
	public void check() {
		super.check();
		// enemy has been hit
		if (this.hit == 1) {
			this.hit = 0;
			this.life -= 1;
		}		
		// enemy has been shot to death
		if (this.life <= 0) {
			Global.score += (this.level * this.level * 10);
			this.dead = 1;
			this.drop();
			for (int i = 0; i <= this.level; i++) {
				Explosion e = new Explosion();
				Movement m = new Movement(0, 
						this.movement.cx + Global.rand(-8, 8), 
						this.movement.cy + Global.rand(-8, 8), 0, 0);
				m.tl = 25;
				e.addMovement(m);
				Global.others.add(e);
			}
	    }
		// enemy is colliding with the player
		if (Global.area_overlap(this.area(), Global.pg.area())) {
			if (!Global.GM) {
				Global.pg.dead = 1;
			}
		}
	}
	
	// fire bullets
	public void shoot() {
		if (this.dead == 0) {
			// get array of bullets that must be fired in the current frame
			ArrayList<EnemyBullet> fired = this.spellcard.fire();
			for (int i = 0; i < fired.size(); i++) {
				EnemyBullet b = fired.get(i);
			    if (b.homing == 1) {
					// homing bullet, recalculate direction
					double xspan = Global.pg.movement.cx - this.movement.cx;
					double yspan = Global.pg.movement.cy - this.movement.cy;
					double newxspan, newyspan;
					if (xspan < yspan) {
						newxspan = xspan * Global.bg.h / yspan;
						newyspan = Global.bg.h;
					} else {
						newxspan = Global.bg.w;
						newyspan = yspan * Global.bg.w/ xspan;
					}
					if (xspan < 0 && yspan < 0) {
						newxspan = -newxspan;
						newyspan = -newyspan;
					}
					Movement m = new Movement(b.movement.speed, 
							this.movement.cx + b.movement.sx, 
							this.movement.cy + b.movement.sy,
							this.movement.cx + b.movement.ex + newxspan, 
							this.movement.cy + b.movement.ey + newyspan);
					b.movement = m;
				} else {
			    	// not homing, enemy position might have changed, so refresh bullet movement adding enemy coords
					Movement m = new Movement(b.movement.speed, 
			    			this.movement.cx + b.movement.sx, 
			    			this.movement.cy + b.movement.sy, 
							this.movement.cx + b.movement.ex, 
							this.movement.cy + b.movement.ey);
			    	b.movement = m;
			    }
			    Global.bullets.add(b);
			}
		}
	}
	
	// drop powerups
	public void drop() {
		if (this.level == 1)  {
			// small fairy
			if (Global.rand(1,10) > 3) {
				Powerup p = new Powerup('s');
				double rx = Global.rand(-20, 20); 
				double ry = Global.rand(-20, 20);
				p.addMovement(new Movement(1.5, 
						this.movement.cx + rx, 
						this.movement.cy + ry, 
						this.movement.cx + rx, 
						Global.bg.h));
				Global.powerups.add(p);
			}
			if (Global.rand(1, 10) > 5) {
				Powerup p = new Powerup('p');
				double rx = Global.rand(-20, 20); 
				double ry = Global.rand(-20, 20);
				p.addMovement(new Movement(1.5, 
						this.movement.cx + rx, 
						this.movement.cy + ry, 
						this.movement.cx + rx, 
						Global.bg.h));
				Global.powerups.add(p);
			}
		} else if (this.level == 2) {
			// big fairy
			for (int i = 0; i < 3; i++) {
				if (Global.rand(1,10) > 2) {
					Powerup p = new Powerup('s');
					double rx = Global.rand(-20, 20); 
					double ry = Global.rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							Global.bg.h));
					Global.powerups.add(p);
				}
				if (Global.rand(1, 10) > 4) {
					Powerup p = new Powerup('p');
					double rx = Global.rand(-20, 20); 
					double ry = Global.rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							Global.bg.h));
					Global.powerups.add(p);
				}
			}
		} else if (this.level == 3) {
			// butterflies in MoF
			for (int i = 0; i < 6; i++) {
				if (Global.rand(1, 10) > 5) {
					Powerup p = new Powerup('s');
					double rx = Global.rand(-20, 20); 
					double ry = Global.rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							Global.bg.h));
					Global.powerups.add(p);
				}
				if (Global.rand(1, 10) > 2) {
					Powerup p = new Powerup('p');
					double rx = Global.rand(-20, 20); 
					double ry = Global.rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							Global.bg.h));
					Global.powerups.add(p);
				}
			}
		} else if (this.level == 4) {
			// lily white
			for (int i = 0; i < 10; i++) {
				if (Global.rand(1, 10) > 4) {
					Powerup p = new Powerup('s');
					double rx = Global.rand(-20, 20); 
					double ry = Global.rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							Global.bg.h));
					Global.powerups.add(p);
				}
				if (Global.rand(1, 10) > 2) {
					Powerup p = new Powerup('p');
					double rx = Global.rand(-20, 20); 
					double ry = Global.rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							Global.bg.h));
					Global.powerups.add(p);
				}
			}
		} else if (this.level == 5) {
			// final boss
			for (int i = 0; i < 5; i++) {
				Powerup p = new Powerup('p');
				double rx = Global.rand(-20, 20); 
				double ry = Global.rand(-20, 20);
				p.addMovement(new Movement(1.5, 
						this.movement.cx + rx, 
						this.movement.cy + ry, 
						this.movement.cx + rx, 
						Global.bg.h));
				Global.powerups.add(p);
			}
			for (int i = 0; i < Global.bullets.size(); i++) {
				GameSprite b = Global.bullets.get(i);
				if (b instanceof EnemyBullet) {
					Powerup p = new Powerup('s');
					p.addMovement(new Movement(1.5, 
							b.movement.cx, 
							b.movement.cy, 
							b.movement.cx, 
							Global.bg.h));
					Global.powerups.add(p);
					Global.bullets.remove(i);
				}
			}
			Global.bullets.clear();
		}
	}
	
	@Override
	public void work() {
		super.work();
		this.shoot();
	}
}
