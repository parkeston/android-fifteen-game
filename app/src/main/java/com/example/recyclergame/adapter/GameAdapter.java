package com.example.recyclergame.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclergame.R;
import com.example.recyclergame.helper.OnDragListener;
import com.example.recyclergame.utils.GridMapper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameVH> implements ItemTouchHelperAdapter {

    private ArrayList<Bitmap> data;
    private GameVH emptyCell;
    private OnDragListener startDragListener;

    private int winCounter;
    private Snackbar winBar;


    public GameAdapter(OnDragListener startDragListener, ArrayList<Bitmap> data) {
        this.startDragListener = startDragListener;
        winCounter = 0;

        this.data = new ArrayList<>(data);
        Collections.shuffle(this.data);
        this.data.add(null);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        startDragListener.onDrag();

        Collections.swap(data, fromPosition, toPosition);
        winCounter = 0;

        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
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
                    startDragListener.onStartDrag(holder);
                }

                return false;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GameVH holder, int position) {
        holder.SetData(data.get(position));

        if (holder.isEmpty) {
            emptyCell = holder;
        }

//        if(dataPiece!=null && dataPiece == (position+1)) {
//
//            winCounter++;
//
//            if(winCounter == (data.size()-1))
//                winBar.show();
//        }
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

                data.remove(data.size() - 1);
                shuffle(data);
            }
        });
    }

    public void shuffle(ArrayList<Bitmap> data) {
        winCounter = 0;

        this.data = new ArrayList<>(data);
        Collections.shuffle(this.data);
        this.data.add(null);

        notifyDataSetChanged();

        startDragListener.onShuffle();
    }

    public class GameVH extends RecyclerView.ViewHolder {

        private boolean isEmpty;

        private ImageView image;

        public GameVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.gridItemImage);
        }

        public int getDragFlags() {
            if (isEmpty)
                return 0;

            return GridMapper.isNeighbour(getAdapterPosition(), emptyCell.getAdapterPosition());
        }

        public void setStartDragListener(View.OnTouchListener listener) {
            itemView.setOnTouchListener(listener);
        }

        public void SetData(Bitmap dataPiece) {
            if (dataPiece != null)
                image.setImageBitmap(dataPiece);

            isEmpty = dataPiece == null;
        }
    }
}
