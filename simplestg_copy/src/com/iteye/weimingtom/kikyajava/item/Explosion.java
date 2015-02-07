package com.iteye.weimingtom.kikyajava.item;

import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.Movement;
import com.iteye.weimingtom.kikyajava.global.Game;
import com.iteye.weimingtom.kikyajava.global.GameGraph;

public class Explosion {
	private Explosion nextNode;
	
	private int w;
	private int h;
	private int image;
	private boolean dead;
	private ArrayList<Movement> movements;
	private Movement movement;
	
	public Explosion(int image) {
		this.w = 16;
		this.h = 16;
		this.image = image;
		this.dead = false;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
	}
	
	public Explosion getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(Explosion nextNode) {
		this.nextNode = nextNode;
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
