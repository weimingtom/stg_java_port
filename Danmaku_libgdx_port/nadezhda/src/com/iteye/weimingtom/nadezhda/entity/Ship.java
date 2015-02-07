package com.iteye.weimingtom.nadezhda.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;
import com.iteye.weimingtom.nadezhda.utils.MathUtils;

public class Ship extends Entity {
    private static final float MOVE_SPEED = 4.0f;
    private static final float HALFWIDTH = 32.0f;
    private static final float HALFHEIGHT = 32.0f;
    private static final Rectangle COLLISION = new Rectangle(-3.0f, 6.0f, 6.0f, 6.0f);

    private long m_LastStraitBullets = 0;
    private long m_LastHomingBullets = 0;

    public Ship() {
        reset();
    }

    public void reset() {
        m_PosX = Application.FIELD_WIDTH * 0.5f;
        m_PosY = 500.0f;
    }

    @Override
    public void update(long simuTime) {
    	if (Gdx.input.isTouched()) {
    		m_PosX = Gdx.input.getX();
    		m_PosY = Gdx.input.getY();
    	}
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            m_PosX -= MOVE_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            m_PosX += MOVE_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            m_PosY -= MOVE_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            m_PosY += MOVE_SPEED;
        }
        if (m_PosX < HALFWIDTH) {
            m_PosX = HALFWIDTH;
        } else if(m_PosX > Application.FIELD_WIDTH - HALFWIDTH) {
            m_PosX = Application.FIELD_WIDTH - HALFWIDTH;
        }
        if (m_PosY < HALFHEIGHT) {
            m_PosY = HALFHEIGHT;
        } else if(m_PosY > Application.FIELD_HEIGHT - HALFHEIGHT) {
            m_PosY = Application.FIELD_HEIGHT - HALFHEIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isTouched()) {
            if (simuTime >= m_LastStraitBullets + 200) {
                Application.addEntity(new StraightBullet(m_PosX - 5.0f, m_PosY - 20.0f, -0.30f, -15.0f));
                Application.addEntity(new StraightBullet(m_PosX + 5.0f, m_PosY - 20.0f, +0.30f, -15.0f));
                m_LastStraitBullets = simuTime;
                Application.gainPoints(2);
            }
            if (simuTime >= m_LastHomingBullets + 500) {
                Entity best = null;
                float sqDist = 99999999.0f;
                for (Entity target : Application.entities()) {
                    if ((target.type() & Entity.ENEMY) == 0) {
                        continue;
                    }
                    Vector2 t = target.position();
                    float sq = MathUtils.square(t.x - m_PosX) + MathUtils.square(t.y - m_PosY);
                    if (sq < sqDist) {
                        best = target;
                        sqDist = sq;
                    }
                }
                Application.addEntity(new HomingBullet(m_PosX - 23.0f, m_PosY - 2.0f, -3.0f, -10.0f, best));
                Application.addEntity(new HomingBullet(m_PosX + 23.0f, m_PosY - 2.0f, +3.0f, -10.0f, best));
                m_LastHomingBullets = simuTime;
            }
        }
        Entity other = Application.collide(this, Entity.ENEMY_BULLET);
        if (other != null) {
            hit(simuTime);
        }
        if (simuTime % 40 == 0) {
            TextureRegion particle = null;
            switch ((int)(Math.random() * 3.0)) {
            case 0: 
            	particle = Assets.fire1Region; 
            	break;
            	
            case 1: 
            	particle = Assets.fire2Region; 
            	break;
            	
            case 2: 
            	particle = Assets.fire3Region; 
            	break;
            }
            Application.addEntity(new Particle(particle, m_PosX-5.0f, m_PosY + 31.0f, (float)(Math.random()-0.5)*2.0f, 2.5f, 10, true));
            Application.addEntity(new Particle(particle, m_PosX+5.0f, m_PosY + 31.0f, (float)(Math.random()-0.5)*2.0f, 2.5f, 10, true));
        }
    }

    private void hit(long simuTime) {
        if (Application.lastHit() + Application.INVULN_ON_HIT < simuTime) {
            Application.shipDies();
        }
    }

    @Override
    public void render() {
        if ((Application.lastHit() + Application.INVULN_ON_HIT < Application.simulatedTime()) || 
        	((Application.simulatedTime()/200) % 2 == 0)) {
            /*
        	Assets.ship.bind();
            DrawingUtils.drawRect(m_PosX - HALFWIDTH, m_PosY - HALFHEIGHT,
                    m_PosX + HALFWIDTH, m_PosY + HALFHEIGHT);
            */
        	DrawingUtils.drawTextureCenter(Assets.shipRegion, m_PosX, m_PosY, 0);
        }
    }

    @Override
    public Rectangle boundingBox() {
        return new Rectangle(
        		m_PosX + COLLISION.x,
                m_PosY + COLLISION.y,
                COLLISION.width,
                COLLISION.height);
    }

    @Override
    public long type() {
        return Entity.SHIP;
    }
}
