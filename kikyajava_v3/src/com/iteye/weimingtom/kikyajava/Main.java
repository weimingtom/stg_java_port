package com.iteye.weimingtom.kikyajava;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.iteye.weimingtom.firetree.MainFrame;

/**
 * @see http://www.kikya.com/danmaku/
 * @author Administrator
 *
 */
public class Main extends MainFrame {
	private static final long serialVersionUID = 1L;

	public Main() {
		super("kikyajava", Global.canv_width, Global.canv_height, 1000 / 20);
	}

	@Override
	protected void onInit() {
		// TODO Auto-generated method stub
		super.onInit();
		Global.mainframe = this;
		Global.g = this.getBufGraph();
		try {
			Global.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Global.play(this);
	}

	@Override
	protected void onExit() {
		// TODO Auto-generated method stub
		super.onExit();
	}
	
	@Override
	protected void onDraw(Graphics g) {
		/*
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		*/
	}
	
	@Override
	protected void onTick() {
		super.onTick();
		Global.work(this);
	}

	// respond to keydown
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			Global.lf_p = 1;
			break;
			
		case KeyEvent.VK_RIGHT:
			Global.rg_p = 1;
			break;
			
		case KeyEvent.VK_UP:
			Global.up_p = 1;
			break;
			
		case KeyEvent.VK_DOWN:
			Global.dw_p = 1;
			break;
			
		case KeyEvent.VK_Z:
			Global.sh_p = 1;
			break;
			
		case KeyEvent.VK_SHIFT:
			Global.sm_p = 1;
			break;
			
		case KeyEvent.VK_SPACE:
			Global.play(this);
			break;
		}
	}
	
	// respond to keyup
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			Global.lf_p = 0;
			break;
			
		case KeyEvent.VK_RIGHT:
			Global.rg_p = 0;
			break;
			
		case KeyEvent.VK_UP:
			Global.up_p = 0;
			break;
			
		case KeyEvent.VK_DOWN:
			Global.dw_p = 0;
			break;
			
		case KeyEvent.VK_Z:
			Global.sh_p = 0;
			break;
			
		case KeyEvent.VK_SHIFT:
			Global.sm_p = 0;
			break;
		}
	}

	public final static void main(String[] args) {
		new Main().start();
	}
}
