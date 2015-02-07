package com.iteye.weimingtom.sensiblehope;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SHDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 240;
		config.height = 320;
		config.title = "SensibleHope";
		config.useGL20 = false;
		config.resizable = false;
		new LwjglApplication(new SHGame(), config);
	}
}
