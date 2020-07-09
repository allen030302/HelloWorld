package com.example.myapplication;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition,int toPosition);
    void onItemDissmiss(int position);
}
