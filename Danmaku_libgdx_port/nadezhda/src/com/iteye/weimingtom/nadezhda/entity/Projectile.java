package com.iteye.weimingtom.nadezhda.entity;

import com.badlogic.gdx.math.Rectangle;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;

public class Projectile extends Entity {
    private static final float HALFWIDTH = 8.0f;
    private static final float HALFHEIGHT = 8.0f;
    
    private float m_VelX, m_VelY;

    public Projectile(float x, float y, float vx, float vy) {
        m_PosX = x;
        m_PosY = y;
        m_VelX = vx;
        m_VelY = vy;
    }

    @Override
    public void update(long simuTime) {
        m_PosX += m_VelX;
        m_PosY += m_VelY;
        if (m_PosX < 0.0f || 
        	m_PosX >= Application.FIELD_WIDTH || 
        	m_PosY < 0.0f || 
        	m_PosY >= Application.FIELD_HEIGHT) {
            m_Alive = false;
            return ;
        }
    }

    @Override
    public void render() {
    	/*
        Assets.homingBullet.bind();
        DrawingUtils.drawRect(m_PosX - HALFWIDTH, m_PosY - HALFHEIGHT, m_PosX + HALFWIDTH, m_PosY + HALFHEIGHT);
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
        return Entity.ENEMY_BULLET;
    }
}
