package com.example.apple.game;

import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;

public class SafeZone extends AnimSprite implements CircleCollidable {
    private static final String TAG = SafeZone.class.getSimpleName();
    public static float MAX_RADIUS = Metrics.width / (float) 6.0f;
    public static float MAX_ROTATION = 15.0f;
    public static float ROTATION_SPEED = 0.5f;
    public static float GROWING_SPEED = Metrics.width / (float) 2.9f;
    protected float dy ,da;

    public SafeZone(float x, float y, float size) {
        super(x, y, size, size, R.mipmap.safe_zone, 1.0f, 0);
        dy = Metrics.size(R.dimen.item_safe_zone_speed);
        da = ROTATION_SPEED;

        rotate = true;
        angle = -(90.0f * (float) Math.PI / 180.0f);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();

        y -= dy * game.frameTime;

        // 좌우로 회전하도록
        if (da > 0 && (angle * 180 / Math.PI + 90.0f) > MAX_ROTATION)
            da = -da;
        if (da < 0 && (angle * 180 / Math.PI + 90.0f) < -MAX_ROTATION)
            da = -da;
        angle += game.frameTime * da;

        // 최대크기까지 커지도록
        if (radius < MAX_RADIUS) {
            radius += game.frameTime * GROWING_SPEED;
        }

        // 화면 밖으로 나가면 삭제되도록
        if (dstRect.bottom < 0)
        {
            game.remove(this);
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
