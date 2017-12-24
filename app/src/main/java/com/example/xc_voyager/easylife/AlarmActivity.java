package com.example.xc_voyager.easylife;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends Activity {
    public static final int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        // 获得Button、TextView实例
        Button btn = findViewById(R.id.cancelButton01);
        TextView tv = findViewById(R.id.msgTextView01);

        // 获得NotificationManager实例
        String service = Context.NOTIFICATION_SERVICE;
        final NotificationManager nm = (NotificationManager)getSystemService(service);
        // 实例化Notification
        Notification n = new Notification();
        // 设置显示提示信息，该信息也会在状态栏显示
        String msg = getIntent().getStringExtra("msg");
        int voice = getIntent().getIntExtra("voice",1);
        int theme = getIntent().getIntExtra("theme", 0);
        // 显示时间
        n.tickerText = msg;
        tv.setText(msg);
        // 设置图标
        Resources res=getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.nv);
        n.icon = R.drawable.nv;
        // 设置声音提示

        if(voice == 1 && theme != 2)
        {
            //n.sound = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Easylife/Spring.ogg");
            //添加系统默认铃声
            n.defaults |= Notification.DEFAULT_SOUND;
            //铃声循环播放直到用户响应
            if(theme == 0)
                n.flags |= Notification.FLAG_INSISTENT;
            //添加系统默认振动
            n.defaults |= Notification.DEFAULT_VIBRATE;
            //添加系统默认灯光
            n.defaults |= Notification.DEFAULT_LIGHTS;
        }

        // 发出通知
        assert nm != null;
        nm.notify(ID, n);
        // 取消通知
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nm.cancel(ID);
                finish();
            }
        });
    }
}
