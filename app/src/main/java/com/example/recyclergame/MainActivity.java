package com.example.recyclergame;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclergame.adapter.GameAdapter;
import com.example.recyclergame.helper.GameItemTouchHelper;
import com.example.recyclergame.helper.OnDragListener;
import com.example.recyclergame.utils.GridMapper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDragListener {

    private ItemTouchHelper touchHelper;
    private Toolbar toolbar;
    private GameAdapter adapter;
    private ArrayList<Bitmap> data;

    private int stepsCounter;
    private TextView stepsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new GameAdapter(this, BitmapSlicer.Slice(this, R.drawable.android, GridMapper.GRIDSIZE));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new GameItemTouchHelper(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);

        stepsText = menu.findItem(R.id.stepsCounterMenuItem).getActionView().findViewById(R.id.stepsCounter);
        stepsText.setText("0");
        return true;
    }

//    private ArrayList<Bitmap> generateAdapterDataSet(int gridSize)
//    {
//        data = new ArrayList<>();
//        for (int i = 0; i<gridSize*gridSize-1;i++)
//        {
//            data.add(i+1);
//        }
//
//        return  data;
//    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }

    @Override
    public void onDrag() {
        stepsCounter++;
        stepsText.setText(String.valueOf(stepsCounter));
    }

    @Override
    public void onShuffle() {
        stepsText.setText("0");
        stepsCounter = 0;
    }

    public void gameRestart(MenuItem item) {
        adapter.shuffle(data);
    }


}
