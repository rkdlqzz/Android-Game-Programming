package com.example.apple.game;

import android.graphics.Canvas;

import com.example.apple.R;
import com.example.apple.framework.Scene;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;

import java.util.Random;

public class ItemGenerator implements GameObject {
    private static final String TAG = ItemGenerator.class.getSimpleName();
    private static final int[] MAX_ITEM = {2, 3, 3}; // 한화면에 존재할 수 있는 아이템의 최대 수 (스테이지별)
    private static final float[] SPAWN_INTERVAL = {5.0f, 4.0f, 4.0f};   // 스폰 간격 (스테이지별)
    private float fallSpeed;
    private float elapsedTime;

    public ItemGenerator() {
        this.fallSpeed = Metrics.size(R.dimen.item_fall_speed);
    }

    @Override
    public void update() {
        float frameTime = Scene.getInstance().frameTime;
        MainScene game = MainScene.get();
        //Log.d(TAG, "NumOfItem : " + game.objectsAt(MainGame.Layer.item).size());

        // maxItem 이상은 item spawn하지 않도록
        if (game.objectsAt(MainScene.Layer.item).size() >= MAX_ITEM[game.stage.get() - 1]) return;

        elapsedTime += frameTime;
        if (elapsedTime > SPAWN_INTERVAL[game.stage.get() - 1]) {
            spawn();
            elapsedTime -= SPAWN_INTERVAL[game.stage.get() - 1];
        }
    }

    private void spawn() {
        Random random = new Random();
        float x;
        int type;

        // x
        x = Metrics.width * 0.1f + random.nextInt((int) (Metrics.width * 0.8f));   // width의 0.1~0.9 사이의 x값

        // type
        type = random.nextInt(6);
        // 디버깅용
        //type = 0;     // speed up
        //type = 1;     // leaf bomb
        //type = 2;     // wood shield
        //type = 3;     // safe zone
        //type = 4;     // seed bullet
        //type = 5;     // freeze

        Item item = Item.get(x, fallSpeed, type);
        MainScene.get().add(MainScene.Layer.item, item);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
