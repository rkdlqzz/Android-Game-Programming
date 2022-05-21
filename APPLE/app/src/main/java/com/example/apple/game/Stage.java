package com.example.apple.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.framework.GameObject;

public class Stage implements GameObject {
    private static final String TAG = Stage.class.getSimpleName();
    private int stage;
    private static float stagePeriod = 5.0f;  // 한 스테이지 플레이 시간
    private final int maxStage = 3;    // 최대 스테이지
    private float playTime;

    public Stage() {
        this.stage = 1;
    }

    public int get() {
        return  stage;
    }

    @Override
    public void update() {
        playTime += MainGame.getInstance().frameTime;
        //stage = 1 + (int) (playTime / stagePeriod);
        stage = stage <= maxStage ? stage : maxStage;

        //Log.d(TAG, "" + stage);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
