package com.iteye.weimingtom.kikyajava.item;

import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.Movement;
import com.iteye.weimingtom.kikyajava.RectangleRegion;
import com.iteye.weimingtom.kikyajava.global.Game;
import com.iteye.weimingtom.kikyajava.global.GameGraph;

public class Powerup {
	public final static int POWERUP_TYPE_POWER = 0; 
	public final static int POWERUP_TYPE_SCORE = 1;
	
	private Powerup nextNode;
	
	private boolean dead;
	private int w;
	private int h;
	private int image;
	private ArrayList<Movement> movements;
	private Movement movement;
	private int type;
	
	public Powerup(int type, int image) {	
		this.w = 10;
		this.h = 10;
		this.dead = false;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		this.type = type;
		this.image = image;
	}
	
	public Powerup getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(Powerup nextNode) {
		this.nextNode = nextNode;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void addMovement(Movement mov) {
		this.movements.add(mov);
		if (this.movements.size() == 1 && this.movement == null) {
			this.movement = this.movements.get(0);
		}
	}
	
	private RectangleRegion area() {
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
							true, this.movement.loop, this.movement.tl);;
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
			if (this.type == POWERUP_TYPE_POWER) {
				game.pg.setPower(game.pg.getPower() + 1);
			} else if (this.type == POWERUP_TYPE_SCORE) {
				game.score += 5;
			}
			this.dead = true;
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
