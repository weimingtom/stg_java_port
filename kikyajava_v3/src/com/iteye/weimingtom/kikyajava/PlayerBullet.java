package com.iteye.weimingtom.kikyajava;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Sprite representing a bullet shot by the player. 
 * @author Administrator
 *
 */
public class PlayerBullet extends Sprite {
	public int delay;
	
	public PlayerBullet() {
		this.w = 4;
		this.h = 20;
		this.image = Global.img_bp;
		this.color = new Color(0, 0, 0, 255);
		this.dead = 0;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		// bullet spawn delay
		this.delay = 0; 
	}
	
	public void check() {
		super.check();	
		// check if this bullet hit an enemy
		for (int i = 0; i < Global.enemies.size(); i++) {
			Enemy e = (Enemy) Global.enemies.get(i);
			if (Global.area_overlap(this.area(), e.area())) {
				e.hit = 1;
				this.dead = 1;
			}
		}
	}
	
	@Override
	public RectangleRegion area() {
		return new RectangleRegion(
			Math.min(this.movement.px, this.movement.cx), 
			Math.min(this.movement.py, this.movement.cy), 
			Math.max(this.movement.px, this.movement.cx), 
			Math.max(this.movement.py, this.movement.cy));
	}
}
