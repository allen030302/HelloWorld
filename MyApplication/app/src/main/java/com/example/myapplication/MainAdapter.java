package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<String> datalist=new ArrayList<>();
    private Context view;
    private LayoutInflater layoutInflater;
    private TextView Text1;
    public MainAdapter(List<String> list, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        datalist=list;
        view=context;
    }


    @NonNull
    //新增橘色
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.item_main,parent,false);
        return new ViewHolder(view);
    }

    //依序綁入Text
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Text1.setText(datalist.get(position));
        if(position % 2 == 0) {
            holder.Text1.setBackgroundColor(Color.parseColor("#da70d6"));
        }
        else {
            holder.Text1.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }

    // 抓 list size
    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public void moveItem(int fromPos, int toPos) {
        Collections.swap(datalist, fromPos, toPos);
        this.notifyItemMoved(fromPos, toPos);

    }

    public void removeItem(int position) {
        datalist.remove(position);
        this.notifyItemRemoved(position);
    }

    //綁定黃色
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Text1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Text1 = itemView.findViewById(R.id.DataL);

        }
    }
}
