package com.example.recyclergame.helper;

import androidx.recyclerview.widget.RecyclerView;

public interface GameManager {

    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onStepCompleted();

    void onGameRestart();
}
