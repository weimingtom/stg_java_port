package com.iteye.weimingtom.nadezhda.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    public static final long SHIP = 1L << 0;
    public static final long ENEMY = 1L << 1;
    public static final long OWN_BULLET = 1L << 2;
    public static final long ENEMY_BULLET = 1L << 3;

    protected boolean m_Alive = true;
    protected float m_PosX, m_PosY;

    public abstract void update(long simuTime);

    public abstract void render();

    public final boolean alive() {
        return m_Alive;
    }

    public abstract Rectangle boundingBox();

    public final Vector2 position() {
        return new Vector2(m_PosX, m_PosY);
    }

    public abstract long type();
}
