package com.example.apple.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;

import java.util.Random;

public class ItemGenerator implements GameObject {
    private static final String TAG = ItemGenerator.class.getSimpleName();
    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;   // 초기 스폰 간격
    private static final int INITIAL_MAX_ITEM = 3;    // 아이템의 최대 수 초기값
    private static final int INCREMENT_MAX_ITEM = 1;    // 스테이지 증가 시 증가하는 아이템의 최대 수
    private float spawnInterval;
    private float fallSpeed;
    private float elapsedTime;
    private int maxItem;   // 한화면에 존재할 수 있는 아이템의 최대 수

    public ItemGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.item_fall_speed);
        this.maxItem = INITIAL_MAX_ITEM;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        //Log.d(TAG, "NumOfItem : " + MainGame.getInstance().objectsAt(MainGame.Layer.item).size());

        // maxItem 이상은 item spawn하지 않도록
        if (MainGame.getInstance().objectsAt(MainGame.Layer.item).size() >= maxItem) return;

        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        Random random = new Random();
        float x;
        int type, bitmapResId = 0;

        // x
        x = Metrics.width / 10.0f + random.nextInt((int) (Metrics.width * 0.8f));   // width의 0.1~0.9 사이의 x값

        // type, bitmapResId
        type = random.nextInt(3);
        switch (type) {
            case 0:
                bitmapResId = R.mipmap.item_green_apple;
                break;
            case 1:
                bitmapResId = R.mipmap.item_leaf_bomb;
                break;
            case 2:
                bitmapResId = R.mipmap.item_wood_shield;
                break;
            default:
                break;
        }

        Item item = Item.get(x, fallSpeed, type, bitmapResId);
        MainGame.getInstance().add(MainGame.Layer.item, item);
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
