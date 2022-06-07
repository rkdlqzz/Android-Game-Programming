package com.example.apple.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.BitmapPool;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Sprite;

import java.util.ArrayList;

public class Stage implements GameObject {
    private static final String TAG = Stage.class.getSimpleName();
    private int stage;
    //private static float stagePeriod = 60.0f;  // 한 스테이지 플레이 시간
    private static float stagePeriod = 5.0f;  // 한 스테이지 플레이 시간
    private final int maxStage = 3;    // 최대 스테이지
    private float playTime;
    private Sprite spriteStage;
    protected static int[] bitmapIds = {
            R.mipmap.stage1, R.mipmap.stage2, R.mipmap.stage3
    };
    private ArrayList<Bitmap> bitmaps;


    public Stage(float x, float y) {
        this.stage = 1;

        bitmaps = new ArrayList<>();
        for (int i = 0; i < bitmapIds.length; ++i)
            bitmaps.add(BitmapPool.get(bitmapIds[i]));

        spriteStage = new Sprite(x, y, Metrics.size(R.dimen.text_stage_width),
                Metrics.size(R.dimen.text_stage_height), bitmapIds[stage - 1]);
    }

    public int get() {
        return  stage;
    }

    @Override
    public void update() {
        playTime += MainScene.getInstance().frameTime;
        // 디버깅용 - 주석 해제
        stage = 1 + (int) (playTime / stagePeriod);
        stage = stage <= maxStage ? stage : maxStage;

        if (spriteStage.getBitmap() != bitmaps.get(stage - 1))
            spriteStage.setBitmap(bitmaps.get(stage - 1));

        //Log.d(TAG, "" + stage);
    }

    @Override
    public void draw(Canvas canvas) {
        spriteStage.draw(canvas);
    }
}
