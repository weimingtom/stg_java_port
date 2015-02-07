package com.iteye.weimingtom.nadezhda;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.os.Bundle;

public class NadezhdaAndroidActivity extends AndroidApplication  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        initialize(new Nadezhda(), false);
    }
}
