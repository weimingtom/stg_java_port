package com.iteye.weimingtom.sensiblehope;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

/**
 * Sprite representing a bullet shot by an enemy. 
 * @author Administrator
 *
 */
public class EnemyBullet extends GameSprite {
	public double delay;
	public int homing;
	
	public EnemyBullet() {
		this.w = 8;
		this.h = 8;
		this.image = null;
		this.color = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		this.dead = 0;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		// bullet spawn delay
		this.delay = 0; 
		// if 1, this is a homing bullet
		this.homing = 0; 
	}
	
	@Override
	public void check() {
		super.check();
		// check if bullet is hitting the player	
		if (Global.area_overlap(this.area(), Global.pg.area())) {
			if (!Global.GM) {
				Global.pg.dead = 1;
			}
		}
	}
	
	// overwritten, because a bullet must return a bigger active area,
	// stretched from its position in the previous frame, to avoid 
	// passing through the player
	@Override
	public RectangleRegion area() {
		return new RectangleRegion(
			Math.min(this.movement.px, this.movement.cx), 
			Math.min(this.movement.py, this.movement.cy), 
			Math.max(this.movement.px, this.movement.cx), 
			Math.max(this.movement.py, this.movement.cy));
	}
}
