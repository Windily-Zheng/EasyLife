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
        ImageView noteImage1;
        ImageView noteImage2;
        TextView noteText1;
        TextView noteText2;
        TextView noteText3;

        public ViewHolder(View view){
            super(view);
            noteImage1 = (ImageView)view.findViewById(R.id.common_note_image1);
            noteImage2 = (ImageView)view.findViewById(R.id.common_note_image2);
            noteText1 = (TextView)view.findViewById(R.id.common_note_text1);
            noteText2 = (TextView)view.findViewById(R.id.common_note_text2);
            noteText3 = (TextView)view.findViewById(R.id.common_note_text3);
        }
    }

    public HomeAdapter(List<CommonNote> notelist){
        mNote = notelist;
    }

    @Override
    //构造
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_note_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    //为子项赋值
    public void onBindViewHolder(ViewHolder holder, int position){
        CommonNote commonNote = mNote.get(position);
        holder.noteImage1.setImageResource(commonNote.getImageId(1));
        holder.noteImage2.setImageResource(commonNote.getImageId(2));
        holder.noteText1.setText(commonNote.getTextName(1));
        holder.noteText2.setText(commonNote.getTextName(2));
        holder.noteText3.setText(commonNote.getTextName(3));
    }

    @Override
    //告诉recycler view一共有多少子项
    public int getItemCount(){
        return mNote.size();
    }

}



