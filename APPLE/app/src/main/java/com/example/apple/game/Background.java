package com.example.apple.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.apple.framework.BitmapPool;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.GameView;

public class Background implements GameObject {
    private final Bitmap bitmap;
    private RectF dstRect = new RectF();

    public Background(int bitmapResId){
        bitmap = BitmapPool.get(bitmapResId);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        dstRect.set(0, 0, GameView.view.getWidth(), GameView.view.getHeight());
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
