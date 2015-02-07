package com.iteye.weimingtom.nadezhda.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.math.Rectangle;
import com.iteye.weimingtom.nadezhda.entity.Entity;
import com.iteye.weimingtom.nadezhda.entity.Ship;
import com.iteye.weimingtom.nadezhda.level.Level;
import com.iteye.weimingtom.nadezhda.level.Level1;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;
import com.iteye.weimingtom.nadezhda.utils.MathUtils;

public class Application {
    public static final long SIMULATION_STEP = 20;
    public static final int RENDER_FPS_LIMIT = 60;
    public static final int INVULN_ON_HIT = 4000;
    public static float FIELD_WIDTH = 500.0f;
    public static float FIELD_HEIGHT = 600.0f;

    private final List<Entity> m_Entities = new LinkedList<Entity>();
    private final List<Entity> m_NewEntities = new LinkedList<Entity>();
    private Ship m_Ship;

    private static Application instance = null;

    private long m_SimuTime;

    private long m_LastHit;
    private boolean m_ShipDies = false;

    private int m_NbLives = 9;
    private int m_Score = 0;

    private Level[] levels = {
    	new Level1()
    };
    
    public Application() {
        instance = this;
    }

    public long getTime() {
        //return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    	return System.currentTimeMillis();
    }

    public void initialize() {
    	/*
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, 800.0, 600.0, 0.0, 1.0, -1.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);
        */
    	
        DrawingUtils.loadFont();
    }
    
    long lastFPSUpdate;
    long frames;
    long lastFrameTime;
    boolean paused;
    
    public void initLevel() {
        m_Entities.clear();
        lastFPSUpdate = getTime();
        frames = 0;
        lastFrameTime = getTime();
        m_SimuTime = 0;
        m_LastHit = -99999;
        m_Ship = new Ship();
        m_Entities.add(m_Ship);
        paused = false;
    }
    
    public void onRender() {
    	play(levels[0]);
    }
    
    public void play(Level level) {
    	if (!level.finished() && m_NbLives > 0) {
            long now = getTime();
            if (m_ShipDies) {
                m_NbLives--;
                m_LastHit = m_SimuTime;
                instance.m_Entities.remove(m_Ship);
                m_Entities.add(m_Ship = new Ship());
                m_ShipDies = false;
            }
            //Keyboard.poll();
            //FIXME:
            if (false) {
	            if(!paused && now > lastFrameTime + 20 * SIMULATION_STEP) {
	                System.err.println("Warning: can't keep up! delta=" + (now - lastFrameTime) + "ms");
	                paused = true;
	            }
            }
            while (!paused && now > lastFrameTime + SIMULATION_STEP) {
                lastFrameTime += SIMULATION_STEP;
                m_SimuTime += SIMULATION_STEP;
                level.update(m_SimuTime);
                for(Iterator<Entity> it = m_Entities.iterator(); it.hasNext();) {
                    Entity entity = it.next();
                    entity.update(m_SimuTime);
                    if(!entity.alive())
                        it.remove();
                }
                m_Entities.addAll(m_NewEntities);
                m_NewEntities.clear();
            }
            
            /*
            Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            DrawingUtils.translate(50.0f, 0.0f);
            Gdx.gl11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
            DrawingUtils.drawRect(0.0f, 0.0f, FIELD_WIDTH, FIELD_HEIGHT);
			*/
            DrawingUtils.drawRectangle(0, 0, (int)FIELD_WIDTH, (int)FIELD_HEIGHT, new Color(0.0f, 0.0f, 0.0f, 1.0f));
            
            level.renderBackground(m_SimuTime);

            //Gdx.gl11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            //Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
            for(Entity entity : m_Entities) {
                entity.render();
            }
            //Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);

            if (m_LastHit + 1000 > m_SimuTime) {
                float alpha = 1.0f - MathUtils.square((m_SimuTime - m_LastHit)*1.0E-3f);
                //Gdx.gl11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                //DrawingUtils.drawRect(0.0f, 0.0f, FIELD_WIDTH, FIELD_HEIGHT);
                DrawingUtils.drawRectangle(0, 0, (int)FIELD_WIDTH, (int)FIELD_HEIGHT, new Color(1.0f, 1.0f, 1.0f, alpha));
            }

            level.renderForeground(m_SimuTime);

            if (paused) {
            	/*
                Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
                Gdx.gl11.glColor4f(0.8f, 0.4f, 0.4f, 0.7f);
                DrawingUtils.drawRect(0.0f, 0.0f, FIELD_WIDTH, FIELD_HEIGHT);
                Gdx.gl11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                */
            	DrawingUtils.drawRectangle(0, 0, (int)FIELD_WIDTH, (int)FIELD_HEIGHT, new Color(0.8f, 0.4f, 0.4f, 0.7f));
            	//FIXME:
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                    paused = false;
                    lastFrameTime = now;
                }
            }
            
            //FIXME:
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                paused = true;
            }
            
            //DrawingUtils.reset();
            
            float dx = -300f;
            DrawingUtils.drawText(570.0f + dx, 100.0f, "Score: " + m_Score);
            DrawingUtils.drawText(570.0f + dx, 150.0f, "Lives: " + m_NbLives);
            DrawingUtils.drawText(570.0f + dx, 300.0f, "Sim time: ");
            DrawingUtils.drawText(570.0f + dx, 350.0f, String.valueOf(m_SimuTime));

            //FIXME:
            /*
            Display.update();

            if (RENDER_FPS_LIMIT != 0) {
                Display.sync(RENDER_FPS_LIMIT);
            }
            */
            
            frames++;
            if(now > lastFPSUpdate + 1000) {
                //Display.setTitle("Danmaku - FPS: " + frames);
                lastFPSUpdate = now;
                frames = 0;
            }
            
            //FIXME:
            /*
            if(Display.isCloseRequested()) {
                Display.destroy();
                System.exit(0);
            }
            */
        }
    }

    private void gameover() {
    	/*
        while (!Display.isCloseRequested()) {
            Keyboard.poll();

            if(Keyboard.isKeyDown(Keyboard.KEY_RETURN))
                break;

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            DrawingUtils.drawText(300.0f, 200.0f, "GAME OVER !");
            DrawingUtils.drawText(250.0f, 350.0f, "Your score: " + m_Score);
            DrawingUtils.drawText(250.0f, 450.0f, "Press enter to exit");

            Display.update();

            // FPS limit
            Display.sync(25);
        }
        */
    }

    public static long simulatedTime() {
        return instance.m_SimuTime;
    }

    public static Collection<Entity> entities() {
        return instance.m_Entities;
    }

    public static void addEntity(Entity entity) {
        instance.m_NewEntities.add(entity);
    }

    public static Entity getShip() {
        return instance.m_Ship;
    }

    public static Entity collide(Entity entity, long types) {
        for (Entity other : instance.m_Entities) {
            if((other.type() & types) != 0 && 
            	intersects(other.boundingBox(), entity.boundingBox())) {
                return other;
            }
        }
        return null;
    }

    private static boolean intersects(Rectangle a, Rectangle b) {
    	if (a != null && b != null) {
    		return a.overlaps(b);
    	} else {
    		return false;
    	}
    }
    
    public static long lastHit() {
        return instance.m_LastHit;
    }

    public static void shipDies() {
        instance.m_ShipDies = true;
    }

    public static void gainPoints(int points) {
        if(points >= 0 || points + instance.m_Score >= 0)
            instance.m_Score += points;
    }
}
