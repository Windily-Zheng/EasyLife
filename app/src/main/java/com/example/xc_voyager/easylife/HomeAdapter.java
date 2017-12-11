package com.example.xc_voyager.easylife;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xc_voyager on 2017/12/11.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<CommonNote> mNote;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView noteImage;
        TextView textImage;

        public ViewHolder(View view){
            super(view);
            noteImage = (ImageView)view.findViewById(R.id.common_note_image);
            textImage = (TextView)view.findViewById(R.id.common_note_text);

        }
    }

    public HomeAdapter(List<CommonNote> notelist){
        mNote = notelist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_note_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

    }

    @Override
    public int getItemCount(){
        return mNote.size();
    }

}



