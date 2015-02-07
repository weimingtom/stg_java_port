package com.iteye.weimingtom.kikyajava;

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
	public boolean relative;
	public boolean loop;
	public boolean finished;
	public double ts;
	public int cs;
	public double tl;
	private double mx;
	private double my;
	
	public Movement(double speed, 
			double start_x, double start_y, 
			double end_x, double end_y, 
			boolean relative, boolean loop, double tl) {
		this.speed = speed;
		this.sx = start_x; 
		this.sy = start_y;
		this.ex = end_x; 
		this.ey = end_y; 
		this.cx = start_x; 
		this.cy = start_y; 
		this.px = start_x; 
		this.py = start_y; 
		double xspan = this.ex - this.sx; 
		double yspan = this.ey - this.sy; 
		double dspan = Math.sqrt(Math.pow(xspan, 2) + Math.pow(yspan, 2)); 
		this.ts = dspan / this.speed;
		this.cs = 0;
		
		this.relative = relative; //false;
		this.loop = loop; //false; 
		this.tl = tl; //0;
		
		this.mx = xspan / this.ts;		
		this.my = yspan / this.ts;
		if (this.speed == 0) {
			this.mx = 0;
			this.my = 0;
		}
		this.finished = false;
	}
	
	public void move() {
		this.px = this.cx;
		this.py = this.cy;
		this.cx += this.mx;
		this.cy += this.my;
		this.cs++;
		if (this.cs >= this.ts || 
			(this.tl > 0 && this.cs >= this.tl)) {
			this.finished = true;
		}
	}
	
	public void reset() {
		this.cx = this.sx;
		this.cy = this.sy;
		this.px = this.sx;
		this.py = this.sy;
		this.cs = 0;
		this.finished = false;
	}
}
