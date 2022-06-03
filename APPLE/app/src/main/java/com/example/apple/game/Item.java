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
    protected static int[] bitmapIds = {
            R.mipmap.item_green_apple, R.mipmap.item_leaf_bomb,
            R.mipmap.item_wood_shield, R.mipmap.item_safe_zone,
            R.mipmap.item_seed
    };
    public static float size = Metrics.width / 6;   // item의 크기
    private float dy;
    protected int type;
    protected float[] durations = {0, 0, 0, 0, 0};
    protected float duration;

    public static Item get(float x, float dy, int type) {
        Item item = (Item) RecycleBin.get(Item.class);
        if (item != null) {
            item.set(x, dy, type);
            return item;
        }
        return new Item(x, dy, type);
    }

    private void set(float x, float dy, int type) {
        bitmap = BitmapPool.get(bitmapIds[type]);
        this.x = x;
        this.y = -size;
        this.dy = dy;
        this.type = type;
        setItemDuration();

        //Log.d(TAG, "Recycle Item");
    }

    public Item(float x, float dy, int type) {
        super(x, -size, size, size, bitmapIds[type]);

        this.dy = dy;
        this.type = type;

        durations[0] = Metrics.floatValue(R.dimen.item_speed_up_duration);
        durations[1] = Metrics.floatValue(R.dimen.item_leaf_bomb_duration);
        durations[2] = Metrics.floatValue(R.dimen.item_wood_shield_duration);

        setItemDuration();

        //Log.d(TAG, "Create Item");
    }

    private void setItemDuration() {
        duration = durations[type];
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
                if (player.shield == null) {
                    WoodShield shield = new WoodShield(x, y, size, duration);
                    MainGame.getInstance().add(MainGame.Layer.shield, shield);
                    player.shield = shield;
                }
                else {
                    player.shield.setDuration(duration);
                }
                break;
            case 3:     // safe zone
                MainGame.getInstance().add(MainGame.Layer.zone, new SafeZone(x, y, size));
                break;
            case 4:     // seed bullet
                player.setNumOfBullet(15);
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
