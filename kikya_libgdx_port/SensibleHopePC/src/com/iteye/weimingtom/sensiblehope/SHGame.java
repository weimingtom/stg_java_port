package com.iteye.weimingtom.sensiblehope;

import com.badlogic.gdx.Game;
import com.iteye.weimingtom.libgdxtest.Test001Screen;
import com.iteye.weimingtom.libgdxtest.Test002Screen;

public class SHGame extends Game {

	@Override
	public void create() {
		//setScreen(new Test001Screen());
		//setScreen(new Test002Screen());
		setScreen(new Main());
	}

	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
	}
}
