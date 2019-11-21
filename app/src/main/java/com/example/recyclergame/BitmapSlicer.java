package com.example.recyclergame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class BitmapSlicer {

    public static ArrayList<Bitmap> Slice(Context context, int drawableId, int slices) {
        Bitmap bitmapToSlice = BitmapFactory.decodeResource(context.getResources(), drawableId);

        ArrayList<Bitmap> bitmapSlices = new ArrayList<>();
        slices *= slices;

        int height = bitmapToSlice.getHeight() / slices;
        int width = bitmapToSlice.getWidth();
        int x = 0;
        int y = 0;

        for (int i = 0; i < slices; i++) {

            bitmapSlices.add(Bitmap.createBitmap(bitmapToSlice, x, y, width, height));
            y += height;

        }

        return bitmapSlices;
    }
}
