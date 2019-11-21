package com.example.recyclergame.adapter;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);

    void onClearView();
    int getDragFlags();
}
