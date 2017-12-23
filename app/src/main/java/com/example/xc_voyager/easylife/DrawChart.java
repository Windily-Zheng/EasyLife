package com.example.xc_voyager.easylife;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DrawChart extends AppCompatActivity {

    ArrayList<String> chart_data = new ArrayList<String>();
    int data_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_chart);
        initData();
        initChart();
    }

    private void initData(){
        Intent intent = getIntent();
        chart_data = intent.getStringArrayListExtra("chart_data");
        data_num = chart_data.size()/2;
    }

    private void initChart(){
        LineChart lineChart = (LineChart)findViewById(R.id.linechart);
        List<Entry> entries = new ArrayList<Entry>();
        Float x, y;
        String str;
        for(int i = 0; i < data_num; i++){
            str = chart_data.get(2*i);
            x = Float.parseFloat(str);
            str = chart_data.get(2*i+1);
            y = Float.parseFloat(str);
            entries.add(new Entry(x, y));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Your Data");
        dataSet.setColor(0xFF2196F3);//千万记住是argb颜色，坑死了！
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setValueTextColor(0x64FFDA);
        dataSet.setLineWidth(2.5f);
        dataSet.setDrawFilled(true);
        dataSet.setValueTextSize(9.0f);
        dataSet.setHighLightColor(Color.RED);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        //x轴坐标
        //description
        Description description = new Description();
        description.setText("Line Chart");
        description.setTextSize(20);
        description.setTextColor(Color.GREEN);
        lineChart.setDescription(description);

        lineChart.invalidate();
    }

    private void drawChart(){

    }
}
