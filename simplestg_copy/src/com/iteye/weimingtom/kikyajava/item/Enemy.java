package com.iteye.weimingtom.kikyajava.item;

import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.Movement;
import com.iteye.weimingtom.kikyajava.RectangleRegion;
import com.iteye.weimingtom.kikyajava.SpellCard;
import com.iteye.weimingtom.kikyajava.global.Game;
import com.iteye.weimingtom.kikyajava.global.GameGraph;

public class Enemy {
	private Enemy nextNode;
	
	private int w;
	private int h;
	private int image, image_ex, image_p, image_s;
	private int life;
	private int level;
	private ArrayList<Movement> movements;
	private Movement movement;
	private boolean dead;
	private boolean hit;
	private double delay;
	private SpellCard spellcard;
	
	public Enemy(int w, int h, int level, int life, 
			int image, double delay, 
			int image_ex, 
			int image_p,
			int image_s) {
		this.w = w; //10;
		this.h = h; //10;
		this.image = image; //null;
		this.image_ex = image_ex;
		this.image_p = image_p;
		this.image_s = image_s;
		//this.color = new Color(0, 128, 0, 255);
		this.dead = false;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		this.hit = false; 
		this.life = life; //10; 
		this.level = level; //1; 
		this.spellcard = null;
		this.delay = delay; //0; 
	}
	
	public Enemy getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(Enemy nextNode) {
		this.nextNode = nextNode;
	}
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public double getDelay() {
		return this.delay;
	}
	
	public void setSpellcard(SpellCard spellcard) {
		this.spellcard = spellcard;
	}
	
	public void addMovement(Movement mov) {
		this.movements.add(mov);
		if (this.movements.size() == 1 && this.movement == null) {
			this.movement = this.movements.get(0);
		}
	}
	
	public void addRelativeMovement(Movement mov) {
		mov.relative = true;
		this.addMovement(mov);
	}
	
	public void addLoopingMovement(Movement mov) {
		mov.relative = true;
		mov.loop = true;
		this.addMovement(mov);
	}
	
	public RectangleRegion area() {
		return new RectangleRegion(
			this.movement.cx - this.w / 2, 
			this.movement.cy - this.h / 2, 
			this.movement.cx + this.w / 2, 
			this.movement.cy + this.h / 2);
	}
	
	public void work(Game game, GameGraph g, int canv_width, int canv_height) {
		//move
		if (this.movements.size() == 0) {
			this.dead = true;
			return;
		} else {
			if (this.movement == null) {
				this.movement = this.movements.get(0);
			}
			if (this.movement.finished == true) {
				double fx = this.movement.cx;
				double fy = this.movement.cy;
				this.movements.remove(0);
				if (this.movement.loop == true) {
					this.movement.reset();
					this.movements.add(this.movement);
				}
				if (this.movements.size() > 0) {
					this.movement = this.movements.get(0);
					if (this.movement.relative != false) {
						Movement m = new Movement(this.movement.speed, 
								fx, fy, 
								this.movement.ex, this.movement.ey,
								true, this.movement.loop, this.movement.tl);
						this.movement = m;
					}
					this.movement.move();
				} else {
					this.dead = true;
					return;
				}
			} else {
				this.movement.move();
			}
		}
		//check
		if (this.movement.cx > canv_width + 10 ||
	        this.movement.cy > canv_height + 10 ||
	        this.movement.cx < -10 ||
	        this.movement.cy < -10) {
			this.dead = true;
		}
		if (this.hit == true) {
			this.hit = false;
			this.life -= 1;
		}
		if (this.life <= 0) {
			game.score += (this.level * this.level * 10);
			this.dead = true;
			//drop
			if (this.level == 1)  {
				if (rand(1, 10) > 3) {
					Powerup p = new Powerup(Powerup.POWERUP_TYPE_SCORE, this.image_s);
					double rx = rand(-20, 20); 
					double ry = rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							canv_height,
							false, false, 0));
					game.addPowerups(p);
				}
				if (rand(1, 10) > 5) {
					Powerup p = new Powerup(Powerup.POWERUP_TYPE_POWER, this.image_p);
					double rx = rand(-20, 20); 
					double ry = rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							canv_height,
							false, false, 0));
					game.addPowerups(p);
				}
			} else if (this.level == 2) {
				for (int i = 0; i < 3; i++) {
					if (rand(1, 10) > 2) {
						Powerup p = new Powerup(Powerup.POWERUP_TYPE_SCORE, this.image_s);
						double rx = rand(-20, 20); 
						double ry = rand(-20, 20);
						p.addMovement(new Movement(1.5, 
								this.movement.cx + rx, 
								this.movement.cy + ry, 
								this.movement.cx + rx, 
								canv_height,
								false, false, 0));
						game.addPowerups(p);
					}
					if (rand(1, 10) > 4) {
						Powerup p = new Powerup(Powerup.POWERUP_TYPE_POWER, this.image_p);
						double rx = rand(-20, 20); 
						double ry = rand(-20, 20);
						p.addMovement(new Movement(1.5, 
								this.movement.cx + rx, 
								this.movement.cy + ry, 
								this.movement.cx + rx, 
								canv_height,
								false, false, 0));
						game.addPowerups(p);
					}
				}
			} else if (this.level == 3) {
				for (int i = 0; i < 6; i++) {
					if (rand(1, 10) > 5) {
						Powerup p = new Powerup(Powerup.POWERUP_TYPE_SCORE, this.image_s);
						double rx = rand(-20, 20); 
						double ry = rand(-20, 20);
						p.addMovement(new Movement(1.5, 
								this.movement.cx + rx, 
								this.movement.cy + ry, 
								this.movement.cx + rx, 
								canv_height,
								false, false, 0));
						game.addPowerups(p);
					}
					if (rand(1, 10) > 2) {
						Powerup p = new Powerup(Powerup.POWERUP_TYPE_POWER, this.image_p);
						double rx = rand(-20, 20); 
						double ry = rand(-20, 20);
						p.addMovement(new Movement(1.5, 
								this.movement.cx + rx, 
								this.movement.cy + ry, 
								this.movement.cx + rx, 
								canv_height,
								false, false, 0));
						game.addPowerups(p);
					}
				}
			} else if (this.level == 4) {
				for (int i = 0; i < 10; i++) {
					if (rand(1, 10) > 4) {
						Powerup p = new Powerup(Powerup.POWERUP_TYPE_SCORE, this.image_s);
						double rx = rand(-20, 20); 
						double ry = rand(-20, 20);
						p.addMovement(new Movement(1.5, 
								this.movement.cx + rx, 
								this.movement.cy + ry, 
								this.movement.cx + rx, 
								canv_height,
								false, false, 0));
						game.addPowerups(p);
					}
					if (rand(1, 10) > 2) {
						Powerup p = new Powerup(Powerup.POWERUP_TYPE_POWER, this.image_p);
						double rx = rand(-20, 20); 
						double ry = rand(-20, 20);
						p.addMovement(new Movement(1.5, 
								this.movement.cx + rx, 
								this.movement.cy + ry, 
								this.movement.cx + rx, 
								canv_height, 
								false, false, 0));
						game.addPowerups(p);
					}
				}
			} else if (this.level == 5) {
				for (int i = 0; i < 5; i++) {
					Powerup p = new Powerup(Powerup.POWERUP_TYPE_POWER, this.image_p);
					double rx = rand(-20, 20); 
					double ry = rand(-20, 20);
					p.addMovement(new Movement(1.5, 
							this.movement.cx + rx, 
							this.movement.cy + ry, 
							this.movement.cx + rx, 
							canv_height,
							false, false, 0));
					game.addPowerups(p);
				}
				EnemyBullet preEnemyBullet = null;
				for (EnemyBullet b = game.enemybullets; b != null; preEnemyBullet = b, b = b.getNextNode()) {
					Powerup p = new Powerup(Powerup.POWERUP_TYPE_SCORE, this.image_s);
					p.addMovement(new Movement(1.5, 
							b.getMovement().cx, 
							b.getMovement().cy, 
							b.getMovement().cx, 
							canv_height,
							false, false, 0));
					game.addPowerups(p);
					if (preEnemyBullet != null) {
						preEnemyBullet.setNextNode(b.getNextNode());
					} else {
						game.enemybullets = b.getNextNode();
					}
					b.setNextNode(null);
					game.enemybullets_size--;
				}
				game.enemybullets = null;
				game.enemybullets_size = 0;
				game.playerbullets = null;
				game.playerbullets_size = 0;
			}
			// drop end
			
			for (int i = 0; i <= this.level; i++) {
				Explosion e = new Explosion(this.image_ex);
				e.addMovement(new Movement(0, 
						this.movement.cx + rand(-8, 8), 
						this.movement.cy + rand(-8, 8), 
						0, 0, 
						false, false, 25));
				game.addExplosions(e);
			}
	    }
		if (this.area().area_overlap(game.pg.area())) {
			game.pg.setDead(true);
		}
		//draw
		if (this.image < 0) {
			g.fillRect(0, 128, 0, 255, 
					(int)(this.movement.cx - (this.w / 2)), 
					(int)(this.movement.cy - (this.h / 2)), 
					this.w,
					this.h);
		} else {
			g.drawImage(this.image, 
					(int)this.movement.cx - (this.w / 2), 
					(int)this.movement.cy - (this.h / 2));
		}
		//shoot
		if (this.dead == false) {
			ArrayList<EnemyBullet> fired = this.spellcard.fire();
			for (int i = 0; i < fired.size(); i++) {
				EnemyBullet b = fired.get(i);
			    if (b.isHoming() == true) {
					double xspan = game.pg.getMovement().cx - this.movement.cx;
					double yspan = game.pg.getMovement().cy - this.movement.cy;
					double newxspan, newyspan;
					if (xspan < yspan) {
						newxspan = xspan * canv_height / yspan;
						newyspan = canv_height;
					} else {
						newxspan = canv_width;
						newyspan = yspan * canv_width / xspan;
					}
					if (xspan < 0 && yspan < 0) {
						newxspan = -newxspan;
						newyspan = -newyspan;
					}
					b.setMovement(new Movement(b.getMovement().speed, 
							this.movement.cx + b.getMovement().sx, 
							this.movement.cy + b.getMovement().sy,
							this.movement.cx + b.getMovement().ex + newxspan, 
							this.movement.cy + b.getMovement().ey + newyspan, 
							false, false, 0));
				} else {
			    	b.setMovement(new Movement(b.getMovement().speed, 
			    			this.movement.cx + b.getMovement().sx, 
			    			this.movement.cy + b.getMovement().sy, 
							this.movement.cx + b.getMovement().ex, 
							this.movement.cy + b.getMovement().ey, 
							false, false, 0));
			    }
			    game.addEnemyBullets(b);
			}
		}
	}
	
	private static int rand(int sn, int en) {
		return (int)(Math.floor(Math.random() * (en - sn + 1)) + sn);
	}
}
