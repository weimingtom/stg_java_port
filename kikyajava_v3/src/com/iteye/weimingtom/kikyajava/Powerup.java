package com.iteye.weimingtom.kikyajava;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Sprite representing a collectable powerup. 
 * @author Administrator
 *
 */
public class Powerup extends Sprite {
	public char type;
	
	public Powerup(char type) {	
		this.w = 10;
		this.h = 10;
		this.image = null;
		this.color = new Color(0, 0, 0, 255);
		this.dead = 0;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
		// type, p: power, s: score
		this.type = type;
		if (this.type == 'p') {
			this.image = Global.img_p;
		} else if (this.type == 's') {
			this.image = Global.img_s;
		}
	}
	
	@Override
	public void check() {
		super.check();
		// check if powerup has been collected
		if (Global.area_overlap(this.area(), Global.pg.area())) {
			if (this.type == 'p') {
				Global.pg.power++;
			} else if (this.type == 's') {
				Global.score += 5;
			}
			this.dead = 1;
		}
	}
}
