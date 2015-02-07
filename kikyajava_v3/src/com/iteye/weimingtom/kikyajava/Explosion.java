package com.iteye.weimingtom.kikyajava;
import java.util.ArrayList;

/**
 * Sprite representing an explosion. (ie. when an enemy dies)
 * @author Administrator
 *
 */
public class Explosion extends Sprite {
	public Explosion() {
		this.w = 16;
		this.h = 16;
		this.image = Global.img_ex;
		this.dead = 0;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
	}
}
