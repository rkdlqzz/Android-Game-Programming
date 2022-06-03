package com.example.apple.game;

import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;

public class SeedBullet extends AnimSprite implements CircleCollidable {
    private static final String TAG = SeedBullet.class.getSimpleName();
    protected static float size = Metrics.width / 12;
    protected float dx, dy;
    protected final float speed;

    public SeedBullet(float x, float y, float angle) {
        super(x, y, size, size, R.mipmap.seed, 1.0f, 0);
        this.speed = Metrics.size(R.dimen.seed_bullet_speed);
        this.dx = (float) Math.cos(angle);
        this.dy = this.dx * (float) Math.tan(angle);
        this.angle = (float) Math.atan2(-dy, -dx) + (180.0f * (float) Math.PI / 180);

        rotate = true;

        //Log.d(TAG, "Create seed bullet");
    }
    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;

        x += dx * speed * frameTime;
        y += dy * speed * frameTime;

        setDstRectWithRadius();

        // 화면을 벗어나면 제거
        if (dstRect.top > Metrics.height || dstRect.bottom < 0 ||
                dstRect.left > Metrics.width || dstRect.right < 0) {
            game.remove(this);
            //Log.d(TAG, "Remove seed bullet");
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
}
