package com.example.apple.game;

import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;

public class LeafBomb extends AnimSprite implements CircleCollidable {
    private static final String TAG = LeafBomb.class.getSimpleName();
    public static float MAX_RADIUS = Metrics.width / (float) 3.3f;
    public static float ROTATION_SPEED = 3.5f;
    public static float GROWING_SPEED = Metrics.width / (float) 2.9f;
    private float duration;

    public LeafBomb(float x, float y, float size, float duration) {
        super(x, y, size, size, R.mipmap.leaf_bomb, 1.0f, 0);
        this.duration = duration;

        rotate = true;
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        duration -= game.frameTime;
        angle += game.frameTime * ROTATION_SPEED;

        // 최대크기까지 커지도록 & 지속시간이 끝나면 작아지고 삭제되도록
        if (duration > 0.0f) {
            if (radius < MAX_RADIUS) {
                radius += game.frameTime * GROWING_SPEED;
            }
        } else {
            if (radius > 0.0f) {
                radius -= game.frameTime * GROWING_SPEED;
            } else {
                game.remove(this);
            }
        }
        setDstRectWithRadius();
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
