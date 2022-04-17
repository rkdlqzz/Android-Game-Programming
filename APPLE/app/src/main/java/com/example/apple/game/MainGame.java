package com.example.apple.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.apple.R;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.GameView;

import java.util.ArrayList;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    public float frameTime;

    public static void clear() {
        singleton = null;
    }

    public void init() {
        gameObjects.clear();

        Background bg = new Background(R.mipmap.background);
        gameObjects.add(bg);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        switch (action) {
//        }
        return false;
    }

    public void draw(Canvas canvas) {
        for (GameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);

        for (GameObject gobj : gameObjects) {
            gobj.update();
        }
    }

    public void add(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                gameObjects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        // 삭제를 예약, 이 코드가 나중에 불림 (trashcan에 담아두고 업데이트 끝난 뒤에 remove하는 방법도 존재)
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                gameObjects.remove(gameObject);
            }
        });
    }

    public int objectCount() {
        return gameObjects.size();
    }
}
