package com.example.xc_voyager.easylife;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;

import java.io.File;

/**
 * Created by xc_voyager on 2017/12/24.
 */

public class ChartToImage {

    static int count = 0;
    static String path1, path2;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void createFolder(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path1 = Environment.getExternalStorageDirectory() + "/" + "Easylife/Charts/";
            path2 = Environment.getExternalStorageDirectory() + "/" + "Easylife/Images/";
            File f1 = new File(path1);
            if (!f1.exists()) {
                f1.mkdirs();
            }
        }
    }

    static public void chartToImage(Chart chart, String type){

            String filename;
            filename = type + "_" + count;
            Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
            chart.saveToGallery(filename, path1, "Android Chart Save", compressFormat, 85);
            count++;
    }

    static public void verifyPermission(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}
