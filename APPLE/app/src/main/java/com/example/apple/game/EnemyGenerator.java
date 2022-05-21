package com.example.apple.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;

import java.util.Random;

public class EnemyGenerator implements GameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private static final float INITIAL_SPAWN_INTERVAL = 1.5f;   // 초기 스폰 간격
    private static final float DECREMENT_SPAWN_INTERVAL = 0.3f; // 스테이지 증가 시 감소하는 스폰 간격
    private static final int INITIAL_MAX_ENEMY = 30;    // 적의 최대 수 초기값
    private static final int INCREMENT_MAX_ENEMY = 15;    // 스테이지 증가 시 증가하는 적의 최대 수
    private float spawnInterval;
    private float elapsedTime;
    private int maxEnemy;   // 한화면에 존재할 수 있는 적의 최대 수
    private final float speedErrorRange;    // enemy 속도(dx,dy) 오차 범위

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.maxEnemy = INITIAL_MAX_ENEMY;
        this.speedErrorRange = Metrics.size(R.dimen.enemy_speed_diff_range);
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        //Log.d(TAG, "NumOfEnemy : " + MainGame.getInstance().objectsAt(MainGame.Layer.enemy).size());

        // maxEnemy 이상은 enemy spawn하지 않도록
        if (MainGame.getInstance().objectsAt(MainGame.Layer.enemy).size() >= maxEnemy) return;

        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        Random random = new Random();
        float x = 0, y = 0, dx = 0, dy = 0;
        int side = 0;
        float faceAxisSpeed;    // 정면으로 이동하는 속도 (상->하, 좌->우, 우->좌) (화면 가운데로 이동하는)
        boolean error;
        int speedError; // enemy 속도(dx, dy) 오차

        switch(MainGame.getInstance().stage.get()) {
            case 1:
                // stage 1 - 상단에서만 enemy 스폰
                // dy는 양수만 (위로 이동 x)

                // x, y
                x = Metrics.width / 10.0f + random.nextInt((int) (Metrics.width * 0.8f));   // width의 0.1~0.9 사이의 x값
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
