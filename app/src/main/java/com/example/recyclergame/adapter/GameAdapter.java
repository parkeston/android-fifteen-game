package com.example.recyclergame.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclergame.R;
import com.example.recyclergame.helper.GameManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameVH> implements ItemTouchHelperAdapter {

    private ArrayList<Bitmap> data;
    private ArrayList<Bitmap> winScenario;
    private GameManager gameManager;

    private Snackbar winBar;
    private int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;


    public GameAdapter(GameManager gameManager, ArrayList<Bitmap> data) {
        this.gameManager = gameManager;
        this.data = data;
        winScenario = new ArrayList<>(data);
        Collections.shuffle(this.data);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onClearView() {
        gameManager.onStepCompleted();

        if (data.equals(winScenario)) {
            winBar.show();
            dragFlags = 0;
        }
    }


    @Override
    public int getDragFlags() {
        return dragFlags;
    }

    @NonNull
    @Override
    public GameVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        final GameVH holder = new GameVH((view));

        holder.setStartDragListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameManager.onStartDrag(holder);
                }

                return false;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GameVH holder, int position) {
        holder.SetData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        winBar = Snackbar.make(recyclerView, "YOU WIN!", Snackbar.LENGTH_LONG);
        winBar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                restart();
            }
        });
    }

    public void restart() {
        Collections.shuffle(this.data);
        notifyDataSetChanged();

        gameManager.onGameRestart();
        dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    }

    public class GameVH extends RecyclerView.ViewHolder {

        private ImageView image;

        public GameVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.gridItemImage);
        }

        public void setStartDragListener(View.OnTouchListener listener) {
            itemView.setOnTouchListener(listener);
        }

        public void SetData(Bitmap dataPiece) {
            image.setImageBitmap(dataPiece);
        }
    }
}
