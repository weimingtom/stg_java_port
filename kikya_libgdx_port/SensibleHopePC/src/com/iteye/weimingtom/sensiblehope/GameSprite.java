package com.iteye.weimingtom.sensiblehope;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a sprite in the game canvas.
 * @author Administrator
 *
 */
public class GameSprite {
	public int w;
	public int h;
	public Sprite image;
	public Color color = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	
	// if 1, sprite must be removed before next frame
	public int dead = 0;
	public ArrayList<Movement> movements = new ArrayList<Movement>();
	public Movement movement;
	
	public void addMovement(Movement mov) {
		this.movements.add(mov);
		if (this.movements.size() == 1 && this.movement == null) {
			this.movement = this.movements.get(0);
		}
	}
	
	public void addRelativeMovement(Movement mov) {
		mov.relative = 1;
		this.addMovement(mov);
	}
	
	public void addLoopingMovement(Movement mov) {
		mov.relative = 1;
		mov.loop = 1;
		this.addMovement(mov);
	}
	
	// handles sprite movement
	public void move() {
		// no more movements left, die
		if (this.movements.size() == 0) {
			this.dead = 1;
			return;
		}
		// initialize movement if null
		if (this.movement == null) {
			this.movement = this.movements.get(0);
		}
		// movement is complete
		if (this.movement.finished == 1) {
			// save ending coords of movement
			double fx = this.movement.cx;
			double fy = this.movement.cy;
			// remove first movement (the current one) from the array
			this.movements.remove(0);
			// if loop, put it back at the end of the array
			if (this.movement.loop == 1) {
				this.movement.reset();
				this.movements.add(this.movement);
			}
			// read first movement from the array and use it as the current one
			if (this.movements.size() > 0) {
				this.movement = this.movements.get(0);
				// new movement is relative, use last coordinates
				if (this.movement.relative != 0) {
					Movement m = new Movement(this.movement.speed, fx, fy, this.movement.ex, this.movement.ey);
					m.relative = 1;
					m.loop = this.movement.loop;
					m.tl = this.movement.tl;
					this.movement = m;
				}
			} else {
				this.dead = 1;
				return;
			}
		}
		this.movement.move();
	}
	
	public void check() {
		// check if sprite is gone outside of the screen
		if (this.movement.cx > Global.bg.w + 10 ||
	        this.movement.cy > Global.bg.h + 10 ||
	        this.movement.cx < -10 ||
	        this.movement.cy < -10) {
			this.dead = 1;
		}
	}
	
	public void draw() {
		//FIXME:
		/*
		if (this.image == null) {
			ctx.fillStyle = this.color;
			ctx.fillRect(this.movement.cx-(this.w/2), this.movement.cy-(this.h/2), this.w, this.h);
		} else {
			ctx.drawImage(this.image, this.movement.cx-(this.w/2), this.movement.cy-(this.h/2));
		}
		*/
		if (this.image == null) {
			if (Global.pm != null) {
				Global.pm.setColor(this.color);
				Global.pm.fillRectangle(
						(int)(this.movement.cx - (this.w / 2)), 
						(int)(this.movement.cy - (this.h / 2)), 
						this.w,
						this.h);
				Global.mainframe.drawPixmap();
			}
		} else {
			/**
			 * @see http://docs.oracle.com/javase/1.4.2/docs/api/java/awt/Graphics.html
			 * @see http://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
			 */
			this.image.setPosition(
					(int)this.movement.cx - (this.w / 2), 
					(int)this.movement.cy - (this.h / 2));
			image.draw(Global.sb);
		}
	}
	
	public RectangleRegion area() {
		return new RectangleRegion(
			this.movement.cx - this.w / 2, 
			this.movement.cy - this.h / 2, 
			this.movement.cx + this.w / 2, 
			this.movement.cy + this.h / 2);
	}
	
	public void work() {
		this.move();
		this.check();
		this.draw();
	}
}
