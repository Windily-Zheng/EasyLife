package com.example.xc_voyager.easylife;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by xc_voyager on 2017/12/18.
 */

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder>{

    private Context mContext;
    private List<Statistic> mStatisticList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View rcview;
        CardView cardView;
        ImageView statisticImage;
        TextView statisticName;
        TextView statisticDescription;

        public ViewHolder(View view){
            super(view);
            rcview = view;
            cardView = (CardView)view;
            statisticImage = (ImageView)view.findViewById(R.id.statistic_image);
            statisticName = (TextView)view.findViewById(R.id.statistic_name);
            statisticDescription = (TextView)view.findViewById(R.id.statistic_description);
        }

    }

    public StatisticAdapter(List<Statistic> list){
        mStatisticList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.statistic_cardview, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.rcview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Statistic statistic = mStatisticList.get(position);
                Toast.makeText(v.getContext(), "You click view " + statistic.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.statisticImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Statistic statistic = mStatisticList.get(position);
                Intent intent = new Intent(mContext, StatisticInput.class);
                mContext.startActivity(intent);
                //Toast.makeText(v.getContext(), "You click image " + statistic.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Statistic statistic = mStatisticList.get(position);
        holder.statisticName.setText(statistic.getName());
        //holder.statisticDescription.setText(statistic.getDescription());
        Glide.with(mContext).load(statistic.getImageId()).into(holder.statisticImage);
    }

    @Override
    public int getItemCount(){
        return mStatisticList.size();
    }

}
