package com.example.apple.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;

import java.util.Random;

public class EnemyGenerator implements GameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private static final int[] MAX_ENEMY = {13, 18, 23}; // 한화면에 존재할 수 있는 적의 최대 수 (스테이지별)
    private static final float[] SPAWN_INTERVAL = {1.5f, 1.2f, 0.9f};   // 스폰 간격 (스테이지별)
    private float elapsedTime;
    private final float speedErrorRange;    // enemy 속도(dx,dy) 오차 범위
    Random random = new Random();
    private static float[] faceAxisSpeed = {0, 0, 0};    // 정면으로 이동하는 속도 (상->하, 좌->우, 우->좌) (화면 가운데로 이동하는)

    public EnemyGenerator() {
        this.speedErrorRange = Metrics.size(R.dimen.enemy_speed_diff_range);
        this.faceAxisSpeed[0] = Metrics.size(R.dimen.enemy_speed_1);
        this.faceAxisSpeed[1] = Metrics.size(R.dimen.enemy_speed_2);
        this.faceAxisSpeed[2] = Metrics.size(R.dimen.enemy_speed_3);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;
        Log.d(TAG, "NumOfEnemy : " + game.objectsAt(MainGame.Layer.enemy).size());

        // maxEnemy 이상은 enemy spawn하지 않도록
        if (game.objectsAt(MainGame.Layer.enemy).size() >= MAX_ENEMY[game.stage.get() - 1]) return;

        elapsedTime += frameTime;
        if (elapsedTime > SPAWN_INTERVAL[game.stage.get() - 1]) {
            spawn();
            elapsedTime -= SPAWN_INTERVAL[game.stage.get() - 1];
        }
    }

    private void spawn() {
        float x = 0, y = 0, dx = 0, dy = 0;
        int side = 0;
        int stage = MainGame.getInstance().stage.get();

        switch (stage) {
            case 1:     // stage 1 - 상단에서만 생성
                side = Enemy.Side.top.ordinal();

                x = getX(side);
                y = getY(side);
                dx = getDx(side, stage);
                dy = getDy(side, stage);
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }

        Enemy enemy = Enemy.get(x, y, dx, dy, side);
        MainGame.getInstance().add(MainGame.Layer.enemy, enemy);
    }

    private float getX(int side) {
        float x = 0;

        switch (side) {
            case 0:     // top
                // width의 0.1~0.9 사이의 x값
                x = Metrics.width * 0.1f + random.nextInt((int) (Metrics.width * 0.8f));
                break;
            case 1:     // left
                break;
            case 2:     // right
                break;
            default:
                break;
        }
        return x;
    }

    private float getY(int side) {
        float y = 0;

        switch (side) {
            case 0:     // top
                y = -Enemy.size + random.nextInt((int) Enemy.size) * (-1);
                break;
            case 1:     // left
                break;
            case 2:     // right
                break;
            default:
                break;
        }
        return y;
    }

    private float getDx(int side, int stage) {
        float dx = 0;
        boolean error;
        int speedError;     // enemy 속도(dx) 오차

        switch (side) {
            case 0:     // top
                error = random.nextBoolean();
                dx = random.nextInt((int) faceAxisSpeed[stage - 1] / 3);
                dx = error ? dx : -dx;
                break;
            case 1:     // left
                break;
            case 2:     // right
                break;
            default:
                break;
        }
        return dx;
    }

    private float getDy(int side, int stage) {
        float dy = 0;
        boolean error;
        int speedError;     // enemy 속도(dy) 오차

        switch (side) {
            case 0:     // top  (dy는 양수만, 아래로만 이동)
                error = random.nextBoolean();
                speedError = random.nextInt((int) speedErrorRange);
                dy = faceAxisSpeed[stage - 1];
                dy = error ? dy + speedError : dy - speedError;
                break;
            case 1:     // left
                break;
            case 2:     // right
                break;
            default:
                break;
        }
        return dy;
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
