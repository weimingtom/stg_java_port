package com.iteye.weimingtom.kikyajava.global;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GameGraph {
	private Graphics graph;
	private ArrayList<BufferedImage> images;
	
	public GameGraph() {
		images = new ArrayList<BufferedImage>();
	}
	
	public int loadImage(String path) {
		int id = -1;
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
			images.add(image);
			id = images.indexOf(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public GameGraph initGraph(Graphics graph) {
		this.graph = graph;
		return this;
	}
	
	public void fillRect(int r, int g, int b, int a, int x, int y, int width, int height) {
		graph.setColor(new Color(r, g, b, a));
		graph.fillRect(x, y, width, height);
	}
	
	public void drawLine(int r, int g, int b, int a, int x1, int y1, int x2, int y2) {
		graph.setColor(new Color(r, g, b, a));
		graph.drawLine(x1, y1, x2, y2);
	}
	
	public void drawImage(int imgId, int x, int y) {
		if (imgId >= 0 && imgId < images.size()) {
			BufferedImage img = images.get(imgId);
			graph.drawImage(img, x, y, null);
		}
	}
	
	public void drawString(int r, int g, int b, int a, String str, int x, int y) {
		graph.setColor(new Color(r, g, b, a));
		graph.drawString(str, x, y);
	}
}
