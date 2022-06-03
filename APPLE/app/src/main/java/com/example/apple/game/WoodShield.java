package com.example.apple.game;

import android.graphics.Bitmap;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.BitmapPool;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;

public class WoodShield extends AnimSprite implements CircleCollidable {
    public static float MAX_RADIUS = Metrics.width / (float) 5.5f;
    public static float ROTATION_SPEED = 4.5f;
    public static float GROWING_SPEED = Metrics.width / (float) 2.9f;
    private float duration;
    private Bitmap bitmapWoodShield;
    private Bitmap bitmapEmpty;

    public WoodShield(float x, float y, float size, float duration) {
        super(x, y, size, size, R.mipmap.wood_shield, 1.0f, 0);
        this.duration = duration;

        rotate = true;
        bitmapWoodShield = BitmapPool.get(R.mipmap.wood_shield);
        bitmapEmpty = BitmapPool.get(R.mipmap.empty);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        duration -= game.frameTime;
        angle += game.frameTime * ROTATION_SPEED;

        // player에 붙어있도록
        x = game.apple.getCenterX();
        y = game.apple.getCenterY();

        // 최대크기까지 커지도록
        if (radius < MAX_RADIUS) {
            radius += game.frameTime * GROWING_SPEED;
        }

        // 지속시간이 1.5초 남으면 깜빡이다가 끝나면 삭제되도록
        if (duration > 1.5f) {
            if (bitmap != bitmapWoodShield)
                bitmap = bitmapWoodShield;
        } else if (duration > 0.0f) {
            if (bitmap == bitmapWoodShield)
                bitmap = bitmapEmpty;
            else bitmap = bitmapWoodShield;
        } else {
            game.remove(this);
            game.apple.shield = null;
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

    public void setDuration(float newDuration) {
        duration = newDuration;
    }
}
