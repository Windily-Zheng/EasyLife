package com.example.xc_voyager.easylife;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DrawLineChart extends AppCompatActivity {

    ArrayList<String> chart_data = new ArrayList<String>();
    int data_num;
    Chart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_linechart);
        initData();
        setTitle("Line Chart");
        initLineChart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.chart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_save){
            ChartToImage.chartToImage(chart, "Line Chart");
            Toast.makeText(getApplicationContext(), "Your Line Chart has been saved!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void initData(){
        Intent intent = getIntent();
        chart_data = intent.getStringArrayListExtra("chart_data");
        data_num = chart_data.size()/2;
    }

    private void initLineChart(){
        LineChart lineChart = (LineChart)findViewById(R.id.linechart);
        chart = lineChart;
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
        LineDataSet dataSet = new LineDataSet(entries, "Line Data");
        dataSet.setColor(0xFF2196F3);//千万记住是argb颜色，坑死了！
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setDrawValues(true);
        dataSet.setLineWidth(2.5f);
        dataSet.setDrawFilled(true);
        dataSet.setValueTextSize(12);
        dataSet.setHighLightColor(Color.RED);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        //description
        Description description = new Description();
        description.setText("Line Chart");
        description.setTextSize(12);
        description.setTextColor(Color.GREEN);
        lineChart.setDescription(description);

        lineChart.invalidate();
    }
}
