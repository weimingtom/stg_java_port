package com.iteye.weimingtom.nadezhda.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;

public class HomingBullet extends Entity {
    private static final float HALFWIDTH = 8.0f;
    private static final float HALFHEIGHT = 8.0f;

    private float m_VelX, m_VelY;
    private Entity m_Target;

    public HomingBullet(float x, float y, float vx, float vy, Entity target) {
        m_PosX = x;
        m_PosY = y;
        m_VelX = vx;
        m_VelY = vy;
        m_Target = target;
    }

    @Override
    public void update(long simuTime) {
        // Homing!
        if (m_Target != null && m_Target.alive()) {
            Vector2 targetPos = m_Target.position();
            float x = targetPos.x - m_PosX;
            float y = targetPos.y - m_PosY;
            float d = x * x + y * y;
            if (d > 100.0f) {
                d = (float)Math.sqrt(d);
                x *= 10.0f/d;
                y *= 10.0f/d;
            }
            m_VelX = 0.8f * m_VelX + 0.2f * x;
            m_VelY = 0.8f * m_VelY + 0.2f * y;
        }
        m_PosX += m_VelX;
        m_PosY += m_VelY;
        if (m_PosX < 0.0f || 
        	m_PosX >= Application.FIELD_WIDTH || 
        	m_PosY < 0.0f || 
        	m_PosY >= Application.FIELD_HEIGHT) {
           m_Alive = false;
           return ;
        }
        Entity other = Application.collide(this, Entity.ENEMY);
        if (other != null) {
            ((Enemy)other).harm(5.0f);
            m_Alive = false;
            for(int i = 0; i < 10; ++i)
                Application.addEntity(new Particle(Assets.smallParticleRegion,
                        m_PosX, m_PosY, 4.0f * ((float)Math.random()-0.5f), 4.0f * ((float)Math.random()-0.5f), 20, true));
            return;
        }
    }

    @Override
    public void render() {
    	/*
        Assets.homingBullet.bind();
        Gdx.gl11.glColor4f(0.6f, 0.6f, 1.0f, 0.6f);
        DrawingUtils.drawRect(m_PosX - HALFWIDTH, m_PosY - HALFHEIGHT, m_PosX + HALFWIDTH, m_PosY + HALFHEIGHT);
        Gdx.gl11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        */
    	//FIXME:
    	DrawingUtils.drawTextureCenter(Assets.homingBulletRegion, m_PosX, m_PosY, 0);
    }

    @Override
    public Rectangle boundingBox() {
        return new Rectangle(m_PosX - HALFWIDTH, m_PosY - HALFHEIGHT,
                2.0f * HALFWIDTH, 2.0f * HALFHEIGHT);
    }

    @Override
    public long type() {
        return Entity.OWN_BULLET;
    }

}
