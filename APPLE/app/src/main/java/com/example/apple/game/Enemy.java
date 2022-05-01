package com.example.apple.game;

import android.graphics.RectF;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;

public class Enemy extends AnimSprite implements CircleCollidable {
    private static final String TAG = Enemy.class.getSimpleName();
    public static float FRAMES_PER_SECOND = 10.0f;  // 초기 애니메이션 속도 (후에 스테이지 증가 시 증가하도록)
    public static float size = Metrics.width / 6;   // enemy의 크기
    protected float dx, dy;
    public int side;    // enemy가 생성된 사이드 (상, 좌, 우) (stage 1 - 상 / 2 - 상, 좌 / 3 - 상, 좌, 우)

    public enum Side {
        top, left, right
    }

    public Enemy(float x, float y, float dx, float dy, int side) {
        super(x, y, size, size, R.mipmap.enemy, FRAMES_PER_SECOND, 0);
        this.dx = dx;
        this.dy = dy;
        this.side = side;

        //Log.d(TAG, "Create Enemy x : " + x + "  y : " + y + "  dx : " + dx + "  dy : " + dy);
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        x += dx * frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();

        // side에 따라서 remove 조건 다르게 적용
//        if (dstRect.top > Metrics.height) {
//            MainGame.getInstance().remove(this);
        //recycleBin.add(this);
//        }
        //Log.d(TAG, "x : " + x + "  y : " + y);
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
