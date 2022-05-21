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

    public EnemyGenerator() {
        this.speedErrorRange = Metrics.size(R.dimen.enemy_speed_diff_range);
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
        Random random = new Random();
        float x = 0, y = 0, dx = 0, dy = 0;
        int side = 0;
        float faceAxisSpeed;    // 정면으로 이동하는 속도 (상->하, 좌->우, 우->좌) (화면 가운데로 이동하는)
        boolean error;
        int speedError; // enemy 속도(dx, dy) 오차

        switch (MainGame.getInstance().stage.get()) {
            case 1:
                // stage 1 - 상단에서만 enemy 스폰
                // dy는 양수만 (위로 이동 x)

                // x, y
                x = Metrics.width * 0.1f + random.nextInt((int) (Metrics.width * 0.8f));   // width의 0.1~0.9 사이의 x값
                y = -Enemy.size + random.nextInt((int) Enemy.size) * (-1);

                faceAxisSpeed = Metrics.size(R.dimen.enemy_speed_1);

                // dx
                error = random.nextBoolean();
                dx = faceAxisSpeed / 4;
                dx = error ? dx : -dx;

                error = random.nextBoolean();
                speedError = random.nextInt((int) speedErrorRange);
                dx = error ? dx + speedError / 4.0f : dx - speedError / 4.0f;

                // dy
                error = random.nextBoolean();
                speedError = random.nextInt((int) speedErrorRange);
                dy = faceAxisSpeed;
                dy = error ? dy + speedError : dy - speedError;

                // side
                side = Enemy.Side.top.ordinal();    // stage 1 - 상단에서만 생성

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

    @Override
    public void draw(Canvas canvas) {
    }
}
