package com.iteye.weimingtom.kikyajava.item;

import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.Movement;
import com.iteye.weimingtom.kikyajava.global.Game;
import com.iteye.weimingtom.kikyajava.global.GameGraph;

public class Text {
	private Text nextNode;
	
	private String text;
	private boolean dead;
	private ArrayList<Movement> movements;
	private Movement movement;
	
	public Text(String text) {
		this.nextNode = null;
		
		this.text = text;
		this.dead = false;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
	}
	
	public Text getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(Text nextNode) {
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
		g.drawString(255, 255, 255, 255,
				this.text, (int)this.movement.cx, (int)this.movement.cy);
	}
}
