package com.iteye.weimingtom.nadezhda.entity;

import com.badlogic.gdx.math.Rectangle;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;

public class SimpleEnemy extends Enemy {
    private static final float HALFWIDTH = 8.0f;
    private static final float HALFHEIGHT = 8.0f;

    public SimpleEnemy() {
        this(HALFWIDTH + (float)Math.random() * (Application.FIELD_WIDTH - 2.0f * HALFWIDTH));
    }

    public SimpleEnemy(float x) {
        super(18.0f);
        m_PosX = x;
        m_PosY = -HALFHEIGHT - 2.0f;
    }

    @Override
    public void update(long simuTime) {
        m_PosY += 2.5;
        if (dying()) {
            Application.gainPoints(10);
            m_Alive = false;
        } else if(m_PosY > Application.FIELD_HEIGHT + HALFHEIGHT) {
            m_Alive = false;
        }
    }

    @Override
    public void render() {
    	/*
        Assets.enemy1.bind();
        DrawingUtils.drawRect(m_PosX - HALFWIDTH, m_PosY - HALFHEIGHT, m_PosX + HALFWIDTH, m_PosY + HALFHEIGHT);
        */
    	DrawingUtils.drawTextureCenter(Assets.enemy1Region, m_PosX, m_PosY, 0);
    }

    @Override
    public Rectangle boundingBox() {
        return new Rectangle(m_PosX - HALFWIDTH, m_PosY - HALFHEIGHT,
        	2.0f * HALFWIDTH, 2.0f * HALFHEIGHT);
    }

    @Override
    public long type() {
        return Entity.ENEMY | Entity.ENEMY_BULLET;
    }
}
