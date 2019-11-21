package com.example.recyclergame.helper;

import androidx.recyclerview.widget.RecyclerView;

public interface OnDragListener {

    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onDrag();

    void onShuffle();
}
