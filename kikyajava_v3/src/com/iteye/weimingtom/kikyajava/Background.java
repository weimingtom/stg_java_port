package com.iteye.weimingtom.kikyajava;

import java.awt.Color;

/**
 * Represents the main playing canvas.
 * This class is not really a sprite so it doesn't extend it.
 * Lots of methods and properties are the same though
 * 
 * @author Administrator
 *
 */
public class Background {
	public int w;
	public int h;
	public int y;
	public double padding;
	
	public Background() {
		// screen width
		this.w = Global.canv_width; 
		// screen height
		this.h = Global.canv_height;
		// background scrolling position
		this.y = 0; 
		// internal padding where player cannot step
		this.padding = 10; 		
	}
	
	public void move() {
		// scroll lines to the bottom a bit
		this.y += 4;
	}
	
	public void draw() {
		//FIXME:
		// draw bg lines
		/*
		ctx.lineWidth = 1;
		ctx.strokeStyle = "rgba(0,0,192,255)";
		for (int i = 0; i < 8; i++) {
			ctx.beginPath();
			ctx.moveTo(0, (this.y + (i*50))%this.h);
			ctx.lineTo(this.w, (this.y + (i*50))%this.h);
			ctx.closePath();
			ctx.stroke();
		}
		*/
		Global.g.setColor(new Color(0, 0, 192, 255));
		for (int i = 0; i < 8; i++) {
			Global.g.drawLine(
				0, (this.y + (i * 50)) % this.h,
				this.w, (this.y + (i*50))%this.h);
		}
	}
	
	public void clear() {
		//FIXME:
		// fill background
		/*
		ctx.fillStyle = 'rgba(0,0,128,255)';
		ctx.fillRect(0, 0, this.w, this.h);
		*/
		Global.g.setColor(new Color(0, 0, 128, 255));
		Global.g.fillRect(0, 0, this.w, this.h);
	}
	
	public void work() {
		this.clear();
		this.move();
		this.draw();
	}
}
