package com.iteye.weimingtom.kikyajava.item;

import com.iteye.weimingtom.kikyajava.Movement;
import com.iteye.weimingtom.kikyajava.RectangleRegion;
import com.iteye.weimingtom.kikyajava.global.Game;
import com.iteye.weimingtom.kikyajava.global.GameGraph;

public class Player {
	private static final double padding = 10;
	
	private int image, image_bp;
	private Movement movement;
	private int power;
	private boolean dead;
	private int w;
	private int h;

	public Player(int image, int startX, int startY, int image_bp) {
		this.w = 10;
		this.h = 10;
		this.image = image;
		this.image_bp = image_bp;
		this.dead = false;
		this.movement = new Movement(0, startX, startY, 0, 0, false, false, 0);
		this.power = 0; 
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public Movement getMovement() {
		return this.movement;
	}
	
	public RectangleRegion area() {
		return new RectangleRegion(
			movement.cx - w / 2, 
			movement.cy - h / 2, 
			movement.cx + w / 2, 
			movement.cy + h / 2);
	}
	
	public void work(Game game, GameGraph g, int canv_width, int canv_height) {
		//move();
		double m = 5; 
		if (game.sm_p == 1) {
			m = 2; 
		}
		if (game.useDirection) {
			if (game.up_p + game.dw_p + game.lf_p + game.rg_p > 1) {
		    	m = m * 0.7;
			}
			if (game.up_p == 1) {
				movement.cy -= m;
			}
			if (game.dw_p == 1) { 
				movement.cy += m;
			}
			if (game.lf_p == 1) { 
				movement.cx -= m;
			}
			if (game.rg_p == 1) { 
				movement.cx += m;
			}
		} else {
			movement.cx = game.pos_x;
			movement.cy = game.pos_y;
		}
		if (movement.cx >= canv_width - padding) { 
			movement.cx = canv_width - padding;
		}
		if (movement.cx <= padding) {
			movement.cx = padding;
		}
		if (movement.cy >= canv_height - padding) {
			movement.cy = canv_height - padding;
		}
		if (movement.cy <= padding) {
			movement.cy = padding;
		}
		//check
		if (movement.cx > canv_width + 10 ||
	        movement.cy > canv_height + 10 ||
	        movement.cx < -10 ||
	        movement.cy < -10) {
			dead = true;
		}
		//draw
		if (image < 0) {
			g.fillRect(255, 0, 0, 255, 
					(int)(movement.cx - (w / 2)), 
					(int)(movement.cy - (h / 2)), 
					w,
					h);
		} else {
			g.drawImage(image, 
					(int)movement.cx - (w / 2), 
					(int)movement.cy - (h / 2));
		}
		if (game.sh_p == 1) {
			//shoot
			if (power < 10) {
				PlayerBullet b1 = new PlayerBullet(this.image_bp);
				b1.addMovement(new Movement(25, 
					movement.cx - 4, movement.cy, 
					movement.cx - 4, -10, 
					false, false, 0));
				game.addPlayerBullets(b1);			
				PlayerBullet b2 = new PlayerBullet(this.image_bp);
				b2.addMovement(new Movement(25, 
					movement.cx + 4, movement.cy, 
					movement.cx + 4, -10,
					false, false, 0));
				game.addPlayerBullets(b2);
			} else if (power < 20) {
				PlayerBullet b1 = new PlayerBullet(this.image_bp);
				b1.addMovement(new Movement(25, 
					movement.cx - 5, movement.cy, 
					movement.cx - 12, -10,
					false, false, 0));
				game.addPlayerBullets(b1);
				PlayerBullet b2 = new PlayerBullet(this.image_bp);
				b2.addMovement(new Movement(25, 
					movement.cx + 5, movement.cy, 
					movement.cx + 12, -10,
					false, false, 0));
				game.addPlayerBullets(b2);
				PlayerBullet b3 = new PlayerBullet(this.image_bp);
				b3.addMovement(new Movement(25, 
					movement.cx, movement.cy, 
					movement.cx, -10,
					false, false, 0));
				game.addPlayerBullets(b3);
			} else if (power < 30) {
				PlayerBullet b1 = new PlayerBullet(this.image_bp);
				b1.addMovement(new Movement(25, 
					movement.cx - 4, movement.cy, 
					movement.cx - 6, -10,
					false, false, 0));
				game.addPlayerBullets(b1);
				PlayerBullet b2 = new PlayerBullet(this.image_bp);
				b2.addMovement(new Movement(25, 
					movement.cx + 4, movement.cy, 
					movement.cx + 6, -10,
					false, false, 0));
				game.addPlayerBullets(b2);
				PlayerBullet b3 = new PlayerBullet(this.image_bp);
				b3.addMovement(new Movement(25, 
					movement.cx - 8, movement.cy, 
					movement.cx - 18, -10,
					false, false, 0));
				game.addPlayerBullets(b3);
				PlayerBullet b4 = new PlayerBullet(this.image_bp);
				b4.addMovement(new Movement(25, 
					movement.cx + 8, movement.cy, 
					movement.cx + 18, -10,
					false, false, 0));
				game.addPlayerBullets(b4);
			} else {
				PlayerBullet b1 = new PlayerBullet(this.image_bp);
				b1.addMovement(new Movement(25, 
					movement.cx - 12, movement.cy, 
					movement.cx - 30, -10,
					false, false, 0));
				game.addPlayerBullets(b1);
				PlayerBullet b2 = new PlayerBullet(this.image_bp);
				b2.addMovement(new Movement(25, 
					movement.cx + 12, movement.cy, 
					movement.cx + 30, -10,
					false, false, 0));
				game.addPlayerBullets(b2);
				b1 = new PlayerBullet(this.image_bp);
				b1.addMovement(new Movement(25, 
					movement.cx - 6, movement.cy,
					movement.cx - 15, -10,
					false, false, 0));
				game.addPlayerBullets(b1);
				b2 = new PlayerBullet(this.image_bp);
				b2.addMovement(new Movement(25, 
					movement.cx + 6, movement.cy, 
					movement.cx + 15, -10,
					false, false, 0));
				game.addPlayerBullets(b2);
				PlayerBullet b3 = new PlayerBullet(this.image_bp);
				b3.addMovement(new Movement(25, 
					movement.cx, movement.cy, 
					movement.cx, -10,
					false, false, 0));
				game.addPlayerBullets(b3);
			}
		}
	}
}
