package com.iteye.weimingtom.kikyajava;

import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.item.Enemy;

public class Level {
	public ArrayList<Enemy> enemies;
	public int cs;
	public int ts;
	public int finished;
	
	public Level() {
		this.enemies = new ArrayList<Enemy>();
		this.cs = 0; 
		this.ts = 5200; 
		this.finished = 0; 
	}
	
	public void addEnemy(Enemy e) {
		this.enemies.add(e);
	}
	
	public ArrayList<Enemy> spawn() {
		if (this.cs >= this.ts) {
			this.finished = 1;
			return new ArrayList<Enemy>();
		}
		ArrayList<Enemy> spawned = new ArrayList<Enemy>();
		for (int i = 0; i < this.enemies.size(); i++) {
			Enemy e = this.enemies.get(i);
			if (e.getDelay() == this.cs) {
				spawned.add(e);
				this.enemies.remove(i);
			}
		}
		this.cs++;
		return spawned;
	}
}
