package com.iteye.weimingtom.sensiblehope;

import java.util.ArrayList;

/**
 * Represents a level as an array of enemies.
 * @author Administrator
 *
 */
public class Level {
	public ArrayList<Enemy> enemies;
	public int cs;
	public int ts;
	public int finished;
	
	public Level() {
		// enemy array
		this.enemies = new ArrayList<Enemy>(); 
		// current frame
		this.cs = 0; 
		// level total length in frames
		this.ts = 5200; 
		// if 1, level is completed
		this.finished = 0; 
	}
	
	public void addEnemy(Enemy e) {
		this.enemies.add(e);
	}
	
	//FIXME: Enemy e1
	// returns an array of enemies that must be spawned in the current frame.
	public ArrayList<Enemy> spawn() {
		if (this.cs >= this.ts) {
			this.finished = 1;
			return new ArrayList<Enemy>();
		}
		ArrayList<Enemy> spawned = new ArrayList<Enemy>();
		for (int i = 0; i < this.enemies.size(); i++) {
			Enemy e = this.enemies.get(i);
			// check if enemy must be spawned in the current frame
			if (e.delay == this.cs) {
				spawned.add(e);
				this.enemies.remove(i);
			}
		}
		this.cs++;
		return spawned;
	}
}
