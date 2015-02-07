package com.iteye.weimingtom.nadezhda.level;

import com.badlogic.gdx.graphics.Color;
import com.iteye.weimingtom.nadezhda.core.Application;
import com.iteye.weimingtom.nadezhda.entity.FiringEnemy;
import com.iteye.weimingtom.nadezhda.entity.SimpleEnemy;
import com.iteye.weimingtom.nadezhda.utils.DrawingUtils;
import com.iteye.weimingtom.nadezhda.utils.MathUtils;

public class Level1 extends Level {
    public Level1() {
        super("Level 1", "A simple level to test stuff");
    }

    @Override
    public void update(long simuTime) {
        if (simuTime == 7500) {
            Application.addEntity(new SimpleEnemy(Application.FIELD_WIDTH*0.8f));
            Application.addEntity(new SimpleEnemy(Application.FIELD_WIDTH*0.2f));
        } else if(simuTime == 8000) {
            Application.addEntity(new SimpleEnemy(Application.FIELD_WIDTH*0.78f));
            Application.addEntity(new SimpleEnemy(Application.FIELD_WIDTH*0.22f));
        } else if(simuTime == 8500) {
            Application.addEntity(new SimpleEnemy(Application.FIELD_WIDTH*0.76f));
            Application.addEntity(new SimpleEnemy(Application.FIELD_WIDTH*0.24f));
        } else if(simuTime == 10000) {
            Application.addEntity(new FiringEnemy(Application.FIELD_WIDTH*0.6f));
        } else if(simuTime == 11000) {
            Application.addEntity(new FiringEnemy(Application.FIELD_WIDTH*0.2f));
        } else if(simuTime == 30000) {
            Application.gainPoints(1000);
            m_Finished = true;
        }
    }

    @Override
    public void renderForeground(long simuTime) {
        if (simuTime >= 2000 && simuTime <= 6000) {
            float alpha = 1.0f;
            if (simuTime <= 3000) {
                alpha = 1.0f - MathUtils.square((simuTime - 3000)*1.0E-3f);
            } else if (simuTime >= 5000) {
                alpha = 1.0f - MathUtils.square((simuTime - 5000)*1.0E-3f);
            }
            DrawingUtils.drawText(200.0f, 100.0f, name(), 
            		new Color(1.0f, 1.0f, 1.0f, alpha));
            DrawingUtils.drawText(20.0f, 300.0f, description(), 
            		new Color(0.6f, 0.6f, 0.6f, alpha));
        }
    }

    @Override
    public void renderBackground(long simuTime) {
    	
    }
}
