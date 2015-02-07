package com.iteye.weimingtom.kikyajava;

public class RectangleRegion {
	public double x1, y1, x2, y2;
	
	public RectangleRegion(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public boolean area_overlap(RectangleRegion area2) {
		return ((area2.x2 < area2.x1 || area2.x2 > this.x1) &&
				(area2.y2 < area2.y1 || area2.y2 > this.y1) &&
				(this.x2 < this.x1 || this.x2 > area2.x1) &&
				(this.y2 < this.y1 || this.y2 > area2.y1));
	}
}
