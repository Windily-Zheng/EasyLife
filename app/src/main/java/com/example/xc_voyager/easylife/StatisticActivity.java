package com.example.xc_voyager.easylife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatisticActivity extends AppCompatActivity {

    private Statistic[] statistics = new Statistic[]{
            new Statistic("Chart1", R.drawable.chart1),
            new Statistic("Chart2", R.drawable.chart2),
            new Statistic("Chart3", R.drawable.chart3),
            new Statistic("Chart4", R.drawable.chart4)
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
        for(int i = 0; i < 50; i++){
            Random random = new Random();
            int index = random.nextInt(statistics.length);
            statisticList.add(statistics[index]);
        }
    }
}
