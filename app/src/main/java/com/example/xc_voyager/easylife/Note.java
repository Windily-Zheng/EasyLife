package com.example.xc_voyager.easylife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import java.lang.reflect.Method;

public class Note extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

    }

    @Override//为了让toolbar的菜单中显示图标
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch (Exception e) {

                }
            }

        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override//创建toolbar以及menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note, menu);
        return true;
    }
}
