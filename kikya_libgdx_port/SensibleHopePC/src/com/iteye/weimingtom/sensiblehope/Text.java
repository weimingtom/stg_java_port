package com.iteye.weimingtom.sensiblehope;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

/**
 * Sprite representing an onscreen text.
 * @author Administrator
 *
 */
public class Text extends GameSprite {
	public String text;
	public int size;
	public String align;
	
	public Text() {
		this.text = ""; // string to print
		this.size = 12; // font size
		this.color = new Color(255, 255, 255, 255); // font color
		this.align = "center"; // text align
		this.dead = 0;
		this.movements = new ArrayList<Movement>();
		this.movement = null;
	}
	
	@Override
	public void draw() {
		//FIXME:
		/*
		ctx.font = "bold " + this.size + "px Arial";
		ctx.textAlign = this.align;
		ctx.fillStyle = this.color;
		ctx.fillText(this.text, this.movement.cx, this.movement.cy);
		*/
		Global.sb.setColor(this.color);
		Global.font.draw(Global.sb, this.text, (int)this.movement.cx, (int)this.movement.cy);
	}
}
