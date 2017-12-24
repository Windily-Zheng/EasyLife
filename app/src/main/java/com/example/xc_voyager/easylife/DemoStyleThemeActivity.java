package com.example.xc_voyager.easylife;

import android.app.Activity;
import android.os.Bundle;


public class DemoStyleThemeActivity extends Activity {

    private static boolean useThemeBlack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useThemeBlack)
            setTheme(R.style.SwitchTheme1);
        else
            setTheme(R.style.SwitchTheme2);
        useThemeBlack = !useThemeBlack;
        setContentView(R.layout.activity_theme_switch);

    }

}  