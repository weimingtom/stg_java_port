package com.iteye.weimingtom.nadezhda.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;

public class FiringEnemy extends Enemy {
    private static final float HALFWIDTH = 32.0f;
    private static final float HALFHEIGHT = 32.0f;

    private static final float HALFCOLWIDTH = 24.0f;
    private static final float HALFCOLHEIGHT = 24.0f;

    private long m_LastFired = -99999;

    public FiringEnemy() {
        this(HALFWIDTH + (float)Math.random() * (Application.FIELD_WIDTH - 2.0f*HALFWIDTH));
    }

    public FiringEnemy(float x) {
        super(198.0f);
        m_PosX = x;
        m_PosY = -HALFHEIGHT - 2.0f;
    }

    @Override
    public void update(long simuTime) {
        if (m_PosY <= 100.0f)
            m_PosY += 2.0f;
        else if (m_PosY <= 450.0f)
            m_PosY += 0.5f;
        else
            m_PosY += 4.0f;
        
        if (m_PosY >= 100.0f && m_PosY <= 400.0f && simuTime >= m_LastFired + 2500) {
            for (float a = 0.0f; a <= 1.99f * Math.PI; a += 0.125f * Math.PI) {
                final float COS = (float)Math.cos(a);
                final float SIN = (float)Math.sin(a);
                Application.addEntity(new Projectile(m_PosX + 30.0f*COS, m_PosY + 30.0f*SIN, 2.0f*COS, 2.0f*SIN));
            }
            m_LastFired = simuTime;
        }
        if (dying()) {
            Application.gainPoints(30);
            m_Alive = false;
        } else if(m_PosY > Application.FIELD_HEIGHT + HALFHEIGHT) {
            m_Alive = false;
        }
    }

    @Override
    public void render() {
        final float a = Application.simulatedTime() * 0.2f;
        /*
        Assets.enemy2.bind();
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(m_PosX, m_PosY, 0.0f);
        Gdx.gl11.glRotatef(a, 0.0f, 0.0f, 1.0f);
        DrawingUtils.drawRect(-HALFWIDTH, -HALFHEIGHT, +HALFWIDTH, +HALFHEIGHT);
        Gdx.gl11.glPopMatrix();
        */
        //FIXME:
        DrawingUtils.drawTextureCenter(Assets.enemy2Region, m_PosX, m_PosY, a);
    }

    @Override
    public Rectangle boundingBox() {
        return new Rectangle(m_PosX - HALFCOLWIDTH, m_PosY - HALFCOLHEIGHT,
                2.0f * HALFCOLWIDTH, 2.0f * HALFCOLHEIGHT);
    }

    @Override
    public long type() {
        return Entity.ENEMY | Entity.ENEMY_BULLET;
    }
}
