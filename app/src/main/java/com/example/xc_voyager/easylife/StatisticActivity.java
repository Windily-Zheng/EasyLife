package com.example.xc_voyager.easylife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {

    private Statistic[] statistics = new Statistic[]{
            new Statistic("Line Chart", "You will draw a line chart.",R.drawable.chart1),
            new Statistic("Pie Chart", "You will draw a pie chart.", R.drawable.chart2),
            new Statistic("Horizontal Chart", "You will draw a horizontal chart.", R.drawable.chart3),
            new Statistic("Bar Chart", "You will draw a Bar chart.", R.drawable.chart4)
    };

    private List<Statistic> statisticList = new ArrayList<>();

    private StatisticAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        initChart();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.statistic_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StatisticAdapter(statisticList);
        recyclerView.setAdapter(adapter);
    }

    private void initChart(){
        statisticList.clear();
        for(int i = 0; i < 4; i++){
            statisticList.add(statistics[i]);
        }
    }
}
