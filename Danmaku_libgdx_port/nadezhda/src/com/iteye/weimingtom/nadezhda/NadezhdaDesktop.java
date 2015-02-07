package com.iteye.weimingtom.nadezhda;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.iteye.weimingtom.nadezhda.core.Application;

public class NadezhdaDesktop {
	public static void main (String[] argv) {
		new LwjglApplication(new Nadezhda(), "Hello World", 
				240, 
				320, 
				false);
	}
}
