package com.iteye.weimingtom.kikyajava.item;

import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.Movement;
import com.iteye.weimingtom.kikyajava.RectangleRegion;
import com.iteye.weimingtom.kikyajava.global.Game;
import com.iteye.weimingtom.kikyajava.global.GameGraph;

public class EnemyBullet {
	private EnemyBullet nextNode;
	
	private int w;
	private int h;
	private int image;
	private Movement movement;
	private double delay;
	private boolean homing;
	private ArrayList<Movement> movements;
	private boolean dead;
	
	public EnemyBullet(int w, int h, int image, double delay, boolean homing) {
		this.w = w; //8;
		this.h = h; //8;
		this.image = image; //null;
		this.dead = false;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		this.delay = delay; //0; 
		this.homing = homing; //false; 
	}
	
	public EnemyBullet getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(EnemyBullet nextNode) {
		this.nextNode = nextNode;
	}
	
	public EnemyBullet cloneNoDelay() {
		 return new EnemyBullet(this.w, this.h, this.image, 0, this.homing);
	}
	
	public Movement getMovement() {
		return this.movement;
	}
	
	public void setMovement(Movement m) {
		this.movement = m;
	}
	
	public boolean isHoming() {
		return this.homing;
	}
	
	public double getDelay() {
		return this.delay;
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public void addMovement(Movement mov) {
		this.movements.add(mov);
		if (this.movements.size() == 1 && this.movement == null) {
			this.movement = this.movements.get(0);
		}
	}
	
	private RectangleRegion area() {
		return new RectangleRegion(
			Math.min(this.movement.px, this.movement.cx), 
			Math.min(this.movement.py, this.movement.cy), 
			Math.max(this.movement.px, this.movement.cx), 
			Math.max(this.movement.py, this.movement.cy));
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
						this.movement = new Movement(this.movement.speed, 
							fx, fy, 
							this.movement.ex, this.movement.ey,
							true, this.movement.loop, this.movement.tl);
					}
					this.movement.move();
				} else {
					this.dead = true;
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
		if (this.area().area_overlap(game.pg.area())) {
			game.pg.setDead(true);
		}
		//draw
		if (this.image < 0) {
			g.fillRect(0, 0, 0, 255,
					(int)(this.movement.cx - (this.w / 2)), 
					(int)(this.movement.cy - (this.h / 2)), 
					this.w,
					this.h);
		} else {
			g.drawImage(this.image, 
					(int)this.movement.cx - (this.w / 2), 
					(int)this.movement.cy - (this.h / 2));
		}
	}
}
