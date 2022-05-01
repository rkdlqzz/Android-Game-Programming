package com.example.apple.game;

import android.graphics.Canvas;

import com.example.apple.R;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;

import java.util.Random;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;   // 초기 스폰 간격
    private static final float DECREMENT_SPAWN_INTERVAL = 0.3f; // 스테이지 증가 시 감소하는 스폰 간격
    private static final int INITIAL_MAX_ENEMY = 30;    // 적의 최대 수 초기값
    private static final int INCREMENT_MAX_ENEMY = 15;    // 스테이지 증가 시 증가하는 적의 최대 수
    private float spawnInterval;
    private float elapsedTime;
    private int maxEnemy;   // 한화면에 존재할 수 있는 적의 최대 수

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.maxEnemy = INITIAL_MAX_ENEMY;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
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
        float x, y, dx, dy;
        boolean error;
        int diffRange;

        if (true)   // stage 1
        {
            x = random.nextInt(Metrics.width);
            y = -Enemy.size + random.nextInt((int) (Enemy.size) * 2) * (-1);

            diffRange = random.nextInt((int) Metrics.size(R.dimen.enemy_speed_diff_range));
            float normalSpeed = Metrics.size(R.dimen.enemy_speed_1);

            dx = 0;

            error = random.nextBoolean();
            dy = normalSpeed;
            dy = error ? dy + diffRange : dy - diffRange;
        }

        Enemy enemy = new Enemy(x, y, dx, dy, Enemy.Side.top.ordinal());
        MainGame.getInstance().add(MainGame.Layer.enemy, enemy);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
