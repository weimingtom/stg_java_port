package com.iteye.weimingtom.nadezhda.level;

public abstract class Level {
    private String m_Name;
    private String m_Description;

    protected boolean m_Finished = false;

    public Level(String name, String description) {
        m_Name = name;
        m_Description = description;
    }

    public final String name() {
        return m_Name;
    }

    public final String description() {
        return m_Description;
    }

    public abstract void update(long simuTime);

    public void renderBackground(long simuTime) {
    	
    }

    public void renderForeground(long simuTime) {
    	
    }

    public final boolean finished() {
        return m_Finished;
    }
}
