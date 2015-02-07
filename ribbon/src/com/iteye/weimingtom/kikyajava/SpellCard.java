package com.iteye.weimingtom.kikyajava;

import java.util.ArrayList;

import com.iteye.weimingtom.kikyajava.item.EnemyBullet;

public class SpellCard {
	public int ts;
	public int cs;
	public ArrayList<EnemyBullet> bullets;
	
	public SpellCard(int ts) {
		this.ts = ts; 
		this.cs = 0; 
		this.bullets = new ArrayList<EnemyBullet>(); 
	}
	
	public void addBullet(EnemyBullet bul) {
		this.bullets.add(bul);
	}
	
	public ArrayList<EnemyBullet> fire() {
		ArrayList<EnemyBullet> fired = new ArrayList<EnemyBullet>();
		for (int i = 0; i < this.bullets.size(); i++) {
			EnemyBullet b = this.bullets.get(i);
			if (b.getDelay() == this.cs) {
				EnemyBullet bc = b.cloneNoDelay(); 
				Movement mc = new Movement(b.getMovement().speed, 
						b.getMovement().sx, b.getMovement().sy, 
						b.getMovement().ex, b.getMovement().ey,
						false, false, 0);
				bc.addMovement(mc);
				fired.add(bc);
			}
		}
		this.cs++;
		if (this.cs > this.ts) {
			this.cs = 0;
		}
		return fired;
	}
}
