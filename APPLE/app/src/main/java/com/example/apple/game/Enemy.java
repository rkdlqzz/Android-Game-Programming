package com.example.apple.game;

import android.graphics.RectF;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;

public class Enemy extends AnimSprite implements CircleCollidable {
    public static final float FRAMES_PER_SECOND = 10.0f;
    private static final String TAG = Enemy.class.getSimpleName();
    public static float size = Metrics.width / 5.0f * 0.9f;
    protected float dy;

    public Enemy(float x, float speed) {
        super(x, -size, size, size, R.mipmap.enemy, FRAMES_PER_SECOND, 0);
        dy = speed;
    }

    @Override
    public void update() {
//        super.update();

        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    public int getScore() {
        return 100;
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
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
}
