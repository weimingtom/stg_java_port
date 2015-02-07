package com.iteye.weimingtom.nadezhda;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.core.Settings;
import com.iteye.weimingtom.nadezhda.scene.MainMenuScreen;
import com.iteye.weimingtom.nadezhda.scene.Test001Screen;
import com.iteye.weimingtom.nadezhda.scene.Test002Screen;

public class Nadezhda extends Game {
	@Override
	public void create () {
		Settings.load();
		Assets.load();
		setScreen(new MainMenuScreen(this));
		//setScreen(new Test001Screen(this));
		//setScreen(new Test002Screen(this));
	}
	
	@Override
	public void dispose () {
		super.dispose();
		getScreen().dispose();
	}
}
