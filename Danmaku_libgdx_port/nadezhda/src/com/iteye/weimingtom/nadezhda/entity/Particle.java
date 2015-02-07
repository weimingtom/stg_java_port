package com.iteye.weimingtom.nadezhda.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.core.Assets;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;

public class Particle extends Entity {
    private TextureRegion m_Region;
    private float m_VelX, m_VelY;
    private int m_Life;
    private float m_AlphaMultiplier = 0.0f;

    public Particle(TextureRegion region, float x, float y, float vx, float vy, int lifetime, boolean fade) {
        m_Region = region;
        m_PosX = x - m_Region.getRegionWidth() * 0.5f;
        m_PosY = y - m_Region.getRegionHeight() * 0.5f;
        m_VelX = vx;
        m_VelY = vy;
        m_Life = lifetime;
        if (fade)
            m_AlphaMultiplier = 1.0f / lifetime;
    }

    @Override
    public void update(long simuTime) {
        m_Life--;
        if (m_Life <= 0) {
            m_Alive = false;
            return;
        }
        if (m_PosX < -m_Region.getRegionWidth() || 
        	m_PosX >= Application.FIELD_WIDTH || 
        	m_PosY < -m_Region.getRegionHeight() || 
        	m_PosY >= Application.FIELD_HEIGHT) {
            m_Alive = false;
            return;
        }
        m_PosX += m_VelX;
        m_PosY += m_VelY;
    }

    @Override
    public void render() {
    	/*
        m_Texture.bind();
        if (m_AlphaMultiplier != 0.0f)
            Gdx.gl11.glColor4f(1.0f, 1.0f, 1.0f, m_Life * m_AlphaMultiplier);
        DrawingUtils.drawRect(m_PosX, m_PosY, m_PosX + m_Texture.getWidth(), m_PosY + m_Texture.getHeight());
        Gdx.gl11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        */
    	DrawingUtils.drawTextureCenter(m_Region, m_PosX, m_PosY, 0);
    }

    @Override
    public Rectangle boundingBox() {
        return null;
    }

    @Override
    public long type() {
        return 0;
    }
}
