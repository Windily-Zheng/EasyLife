package com.example.xc_voyager.easylife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<CommonNote> commonNoteList = new ArrayList<>();
    private static int theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = getIntent().getIntExtra("theme",0);

        setContentView(R.layout.activity_main);
        //recycler view部分
//        initCommonNotes();
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.main_recyclerview);//在content_main布局中
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        HomeAdapter homeAdapter = new HomeAdapter(commonNoteList);
//        recyclerView.setAdapter(homeAdapter);
//        recyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));//分割线

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//右下角的小按钮

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//fab部分的监听器
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();//点击之后下方出现的提示栏
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);//更改drawerlayout图标的颜色
        navigationView.setNavigationItemSelectedListener(this);

        ChartToImage.verifyPermission(this);
        ChartToImage.createFolder();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override//toolbar中元素被选中
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            return true;
        }
        else if(id == R.id.action_nightmode){
            setEnableNightMode(!enableNightMode);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.schedule) {
            Intent intent = new Intent(this, ScheduleActivity.class);
            intent.putExtra("theme", theme);
            startActivity(intent);
        } else if (id == R.id.note) {
            Intent intent = new Intent(this, Note.class);
            startActivity(intent);
        } else if (id == R.id.travel) {
            Intent intent = new Intent(this, Travel.class);
            startActivity(intent);
        } else if (id == R.id.statistic) {
            Intent intent = new Intent(this, StatisticActivity.class);
            startActivity(intent);
        } else if (id == R.id.account) {

        } else if(id == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if(id == R.id.about_us){
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initCommonNotes(){
        for(int i = 0; i <= 4; i++){
            CommonNote c1 = new CommonNote("Schedule "+i, "Have a picnic", getCurrentTime(), R.drawable.schedule, R.drawable.clock);
            commonNoteList.add(c1);
            CommonNote c2 = new CommonNote("Note "+i, "Android is fun!", getCurrentTime(), R.drawable.book_open, R.drawable.clock);
            commonNoteList.add(c2);
            CommonNote c3 = new CommonNote("Travel "+i, "To Shanghai at 15:00 PM.", getCurrentTime(), R.drawable.train, R.drawable.clock);
            commonNoteList.add(c3);
            CommonNote c4 = new CommonNote("Statistic "+i, "Income chart of last month.", getCurrentTime(), R.drawable.chart_bar, R.drawable.clock);
            commonNoteList.add(c4);
        }
    }

    private String getCurrentTime(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String str = date.format(new Date());
        return str;
    }
}
