package com.example.apple.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Scene;

import java.util.Random;

public class ObstacleGenerator implements GameObject {
    private static final String TAG = ItemGenerator.class.getSimpleName();
    private static final int[] MAX_OBSTACLE = {1, 1, 2}; // 한화면에 존재할 수 있는 장애물의 최대 수 (스테이지별)
    private static final float[] SPAWN_INTERVAL = {5.0f, 5.0f, 4.0f};   // 스폰 간격 (스테이지별)
    private float fallSpeed;
    private float elapsedTime;

    public ObstacleGenerator() {
        this.fallSpeed = Metrics.size(R.dimen.obstacle_fall_speed);
    }

    @Override
    public void update() {
        float frameTime = Scene.getInstance().frameTime;
        MainScene game = MainScene.get();
        //Log.d(TAG, "NumOfObstacle : " + game.objectsAt(MainScene.Layer.obstacle.ordinal()).size());

        // max obstacle 이상은 obstacle spawn하지 않도록
        if (game.objectsAt(MainScene.Layer.obstacle.ordinal()).size() >= MAX_OBSTACLE[game.stage.get() - 1]) return;

        elapsedTime += frameTime;
        if (elapsedTime > SPAWN_INTERVAL[game.stage.get() - 1]) {
            spawn();
            elapsedTime -= SPAWN_INTERVAL[game.stage.get() - 1];
        }
    }

    private void spawn() {
        Random random = new Random();
        float x;

        x = Metrics.width * 0.1f + random.nextInt((int) (Metrics.width * 0.8f));   // width의 0.1~0.9 사이의 x값

        Obstacle obstacle = Obstacle.get(x, fallSpeed);
        MainScene.get().add(MainScene.Layer.obstacle, obstacle);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
