package com.example.apple.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime;

    public static void clear() {
        singleton = null;
    }

    public void init() {

    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        switch (action) {
//        }
        return false;
    }

    public void draw(Canvas canvas) {

    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
    }
}
