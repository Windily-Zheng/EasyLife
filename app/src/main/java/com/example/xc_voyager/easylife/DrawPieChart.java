package com.example.xc_voyager.easylife;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DrawPieChart extends AppCompatActivity {

    ArrayList<String> chart_data = new ArrayList<String>();
    int data_num;
    String color[] = {
            "Green", "Yellow", "Red", "Blue"
    };
    Chart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_pie_chart);
        setTitle("Pie Chart");
        initData();
        initPieChart();
    }

    private void initData(){
        Intent intent = getIntent();
        chart_data = intent.getStringArrayListExtra("chart_data");
        data_num = chart_data.size()/2;
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
            ChartToImage.chartToImage(chart, "Pie Chart");
            Toast.makeText(getApplicationContext(), "Your Pie Chart has been saved!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void initPieChart(){
        PieChart pieChart = (PieChart)findViewById(R.id.piechart);
        chart = pieChart;
        List<PieEntry> entries = new ArrayList<>();
        Float x, y;
        String str;
        for(int i = 0; i < data_num; i++){
            str = chart_data.get(2*i);
            x = Float.parseFloat(str);
            str = chart_data.get(2*i+1);

            entries.add(new PieEntry(x, str));
        }
        PieDataSet pieDataSet = new PieDataSet(entries, "Pie Data");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(12);

        Description description = new Description();
        description.setText("Pie Chart");
        description.setTextSize(12);
        description.setTextColor(Color.DKGRAY);
        pieChart.setDescription(description);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Easy Life");
        pieChart.setCenterTextSize(16);
        pieChart.invalidate();
    }

//    private String dealwithy(float y){
//        int newy = ((int)y) % 4;
//        return color[newy];
//    }

}
