package com.example.xc_voyager.easylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 定时提醒广播
 */
public class TaskReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setClass(context, AlarmActivity.class);

        context.startActivity(intent);

    }
}
