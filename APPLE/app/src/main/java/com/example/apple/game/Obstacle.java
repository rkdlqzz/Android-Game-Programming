package com.example.apple.game;

import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Recyclable;
import com.example.apple.framework.RecycleBin;
import com.example.apple.framework.Scene;

public class Obstacle extends AnimSprite implements CircleCollidable, Recyclable {
    private static final String TAG = Obstacle.class.getSimpleName();
    public static float size = Metrics.width / 6;   // obstacle의 크기
    public static float FRAMES_PER_SECOND = 13.0f;  // 애니메이션 속도
    private float dy;
    protected float duration;

    public static Obstacle get(float x, float dy) {
        Obstacle obstacle = (Obstacle) RecycleBin.get(Obstacle.class);
        if (obstacle != null) {
            obstacle.set(x, dy);
            return obstacle;
        }
        return new Obstacle(x, dy);
    }

    private void set(float x, float dy) {
        this.x = x;
        this.y = -size;
        this.dy = dy;

        //Log.d(TAG, "Recycle Obstacle");
    }

    public Obstacle(float x, float dy) {
        super(x, -size, size, size, R.mipmap.obstacle, FRAMES_PER_SECOND,0);

        this.dy = dy;

        duration = Metrics.floatValue(R.dimen.obstacle_duration);

        //Log.d(TAG, "Create Obstacle");
    }

    @Override
    public void update() {
        Scene game = Scene.getInstance();
        float frameTime = game.frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();

        if (dstRect.top > Metrics.height) {
            game.remove(this);
        }
    }

    @Override
    public float getCenterX() {
        return x;
    }

    @Override
    public float getCenterY() {
        return y;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public void finish() {
    }
}
