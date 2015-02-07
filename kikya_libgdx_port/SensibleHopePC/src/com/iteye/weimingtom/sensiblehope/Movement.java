package com.iteye.weimingtom.sensiblehope;

/**
 * Represents a linear movement from one point to another. 
 * @author Administrator
 *
 */
public class Movement {
	public double speed;
	public double sx;
	public double sy;
	public double ex;
	public double ey;
	public double cx;
	public double cy;
	public double px;
	public double py;
	
	public int relative;
	public int loop;
	public int finished;
	
	public double ts;
	public int cs;
	public double tl;
	
	public double mx;
	public double my;
	
	public Movement(double speed, double start_x, double start_y, double end_x, double end_y) {
		// movement speed
		this.speed = speed;
		// start x position
		this.sx = start_x; 
		// start y position
		this.sy = start_y;
		// end x position
		this.ex = end_x; 
		// end y position
		this.ey = end_y; 
		// current x position
		this.cx = start_x; 
		// current y position
		this.cy = start_y; 
		// previous x position
		this.px = start_x; 
		// previous y position
		this.py = start_y; 
		
		// movement span, x axis
		double xspan = this.ex - this.sx; 
		// movement span, y axis
		double yspan = this.ey - this.sy; 
		// diagonal span length
		double dspan = Math.sqrt(Math.pow(xspan, 2) + Math.pow(yspan, 2)); 
		
		// total motion frames (distance/speed)
		this.ts = dspan / this.speed;
		// current motion frame
		this.cs = 0; 
		// time limit (if > 0, movement will be cut to this length)
		this.tl = 0; 
		
		// if 1, movement will start where the previous one ended
		this.relative = 0;
		// if 1, movements will loop until time limit
		this.loop = 0; 
		
		// movement per frame, x axis
		this.mx = xspan / this.ts;		
		// movement per frame, y axis
		this.my = yspan / this.ts;
		// movements in still life
		if (this.speed == 0) {
			this.mx = 0;
			this.my = 0;
		}
		this.finished = 0;
	}
	
	public void move() {
		// save current position in previous position
		this.px = this.cx;
		this.py = this.cy;
		// increment current position
		this.cx += this.mx;
		this.cy += this.my;
		// increment frame count
		this.cs++;
		// check if movement is finished
		if (this.cs >= this.ts || 
			(this.tl > 0 && this.cs >= this.tl)) {
			this.finished = 1;
		}
	}
	
	public void reset() {
		this.cx = this.sx;
		this.cy = this.sy;
		this.px = this.sx;
		this.py = this.sy;
		this.cs = 0;
		this.finished = 0;
	}
}
