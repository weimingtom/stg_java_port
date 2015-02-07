package com.iteye.weimingtom.kikyajava.item;

import com.iteye.weimingtom.kikyajava.global.Game;
import com.iteye.weimingtom.kikyajava.global.GameGraph;

public class Background {
	private int y;
	
	public Background() {
		y = 0;
	}
	
	public void work(Game game, GameGraph g, int canv_width, int canv_height) {
		//clear
		g.fillRect(0, 0, 128, 255, 0, 0, canv_width, canv_height);
		//move
		y += 4;
		//draw
		for (int i = 0; i < 8; i++) {
			g.drawLine(0, 0, 192, 255, 
				0, (y + (i * 50)) % canv_height,
				canv_width, (y + (i * 50)) % canv_height);
		}
	}
}
