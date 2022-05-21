package com.example.apple.game;

import android.util.Log;

import com.example.apple.R;
import com.example.apple.framework.BitmapPool;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Recyclable;
import com.example.apple.framework.RecycleBin;
import com.example.apple.framework.Sprite;

public class Item extends Sprite implements CircleCollidable, Recyclable {
    private static final String TAG = Item.class.getSimpleName();
    public static float size = Metrics.width / 6;   // item의 크기
    private float dy;
    protected int type;
    protected float duration;

    public static Item get(float x, float dy, int type, int bitmapResId) {
        Item item = (Item) RecycleBin.get(Item.class);
        if (item != null) {
            item.set(x, dy, type, bitmapResId);
            return item;
        }
        return new Item(x, dy, type, bitmapResId);
    }

    private void set(float x, float dy, int type, int bitmapResId) {
        bitmap = BitmapPool.get(bitmapResId);
        this.x = x;
        this.y = -size;
        this.dy = dy;
        this.type = type;
        setItemDuration();

        //Log.d(TAG, "Recycle Item");
    }

    public Item(float x, float dy, int type, int bitmapResId) {
        super(x, -size, size, size, bitmapResId);

        this.dy = dy;
        this.type = type;
        setItemDuration();

        //Log.d(TAG, "Create Item");
    }

    private void setItemDuration() {
        switch (type) {
            case 0:     // speed up
                duration = Metrics.floatValue(R.dimen.item_speed_up_duration);
                break;
            case 1:     // leaf bomb
                duration = Metrics.floatValue(R.dimen.item_leaf_bomb_duration);
                break;
            case 2:     // wood shield
                duration = Metrics.floatValue(R.dimen.item_wood_shield_duration);
                break;
        }
    }

    public void useItem(Apple player) {
        switch (type) {
            case 0:     // speed up
                player.useItem(type, duration);
                break;
            case 1:     // leaf bomb
                MainGame.getInstance().add(MainGame.Layer.bomb, new LeafBomb(x, y, size, duration));
                break;
            case 2:     // wood shield
                break;
            default:
                break;
        }

    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();

        if (dstRect.top > Metrics.height) {
            game.remove(this);
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

    @Override
    public void finish() {
    }

    public int getType() {
        return type;
    }
    public float getDuration() {
        return duration;
    }
}
