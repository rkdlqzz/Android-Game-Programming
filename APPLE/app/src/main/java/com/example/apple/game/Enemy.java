package com.example.apple.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.AnimSprite;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Recyclable;
import com.example.apple.framework.RecycleBin;

public class Enemy extends AnimSprite implements CircleCollidable, Recyclable {
    private static final String TAG = Enemy.class.getSimpleName();
    public static float FRAMES_PER_SECOND = 10.0f;  // 초기 애니메이션 속도 (후에 스테이지 증가 시 증가하도록)
    public static float size = Metrics.width / 6;   // enemy의 크기
    protected float dx, dy;
    public int side;    // enemy가 생성된 사이드 (상, 좌, 우) (stage 1 - 상 / 2 - 상, 우 / 3 - 상, 우, 좌)
    private AnimSprite spriteIceCude;
    private float freezeDuration;
    public static float ICE_CUBE_MAX_ROTATION = 8.0f;
    public static float ICE_CUBE_ROTATION_SPEED = 6.0f;
    protected float iceCubeDA;

    public enum Side {
        top, right, left
    }

    public static Enemy get(float x, float y, float dx, float dy, int side) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.set(x, y, dx, dy, side);
            return enemy;
        }
        return new Enemy(x, y, dx, dy, side);
    }

    private void set(float x, float y, float dx, float dy, int side) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.side = side;
        angle = (float) Math.atan2(-dy, -dx);

        setFreeze(false);
        updateIceCube();
        spriteIceCude.angle = angle;
        iceCubeDA = ICE_CUBE_ROTATION_SPEED;

        //Log.d(TAG, "Recycle Enemy");
    }

    public Enemy(float x, float y, float dx, float dy, int side) {
        super(x, y, size, size, R.mipmap.enemy, FRAMES_PER_SECOND, 0);
        this.dx = dx;
        this.dy = dy;
        this.side = side;

        rotate = true;
        angle = (float) Math.atan2(-dy, -dx);

        spriteIceCude = new AnimSprite(x, y, size * 1.2f, size * 1.2f, R.mipmap.ice_cube, 1.0f, 0);
        spriteIceCude.rotate = true;
        updateIceCube();
        spriteIceCude.angle = angle;
        iceCubeDA = ICE_CUBE_ROTATION_SPEED;

        //Log.d(TAG, "Create Enemy x : " + x + "  y : " + y + "  dx : " + dx + "  dy : " + dy);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;

        updateFreeze(frameTime);
        if (getFreeze()) return;

        x += dx * frameTime;
        y += dy * frameTime;

        setDstRectWithRadius();

        checkOutOfScreen(game);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // freeze 상태인 경우 ice cude를 draw
        if (getFreeze()) {
            spriteIceCude.draw(canvas);
        }
    }

    private void checkOutOfScreen(MainGame game) {
        // side에 따라서 remove 조건 다르게 적용
        switch (side) {
            case 0:     // Side.top - 상단에서 스폰
                if (dstRect.top > Metrics.height || dstRect.right < 0 || dstRect.left > Metrics.width) {
                    game.remove(this);
                    //Log.d(TAG, "remove side 0");
                }
                break;
            case 1:     // Side.right - 우측에서 스폰
                if (dstRect.right < 0 || dstRect.top > Metrics.height || dstRect.bottom < 0) {
                    game.remove(this);
                    //Log.d(TAG, "remove side 1");
                }
                break;
            case 2:     // Side.left - 좌측에서 스폰
                if (dstRect.left > Metrics.width || dstRect.top > Metrics.height || dstRect.bottom < 0) {
                    game.remove(this);
                    //Log.d(TAG, "remove side 2");
                }
                break;
            default:
                break;
        }
    }

    public int getScore() {
        return MainGame.getInstance().stage.get() * 5;
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

    private void updateFreeze(float frameTime) {
        updateIceCube();

        if (freezeDuration <= 0.0f) {
            setFreeze(false);
            freezeDuration = 0.0f;
            spriteIceCude.angle = angle;
            iceCubeDA = ICE_CUBE_ROTATION_SPEED;
        } else {
            freezeDuration -= frameTime;

            if (freezeDuration < 1.5f) {   // freeze 지속시간이 1.5초 남은 경우 좌우로 회전하도록
                if (iceCubeDA > 0 && (spriteIceCude.angle * 180 / Math.PI) > (angle * 180 / Math.PI) + ICE_CUBE_MAX_ROTATION)
                    iceCubeDA = -iceCubeDA;
                if (iceCubeDA < 0 && (spriteIceCude.angle * 180 / Math.PI) < (angle * 180 / Math.PI) - ICE_CUBE_MAX_ROTATION)
                    iceCubeDA = -iceCubeDA;
                spriteIceCude.angle += frameTime * iceCubeDA;
            }
        }
    }

    private void updateIceCube() {
        spriteIceCude.x = x;
        spriteIceCude.y = y;
        spriteIceCude.setDstRectWithRadius();
    }

    public void setFreezeDuration(float value) {
        freezeDuration = value;
    }
}
