package com.example.apple.game;

import android.graphics.Canvas;

import com.example.apple.R;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Sprite;

public class Apple extends Sprite {
    private static final String TAG = Apple.class.getSimpleName();

    private float angle;
    private float dx, dy;
    private float tx, ty;

    public Apple(float x, float y) {
        super(x, y, R.dimen.apple_radius, R.mipmap.apple_red);
        setTargetPosition(x, y);
        angle = -(float) (Math.PI / 2);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void update() {
        if (dx == 0 && dy == 0)
            return;

        boolean arrivedX = false;
        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
            arrivedX = true;
        } else {
            x += dx;
        }

        boolean arrivedY = false;
        if ((dy > 0 && y + dy > ty) || (dy < 0 && y + dy < ty)) {
            dy = ty - y;
            y = ty;
            arrivedY = true;
        } else {
            y += dy;
        }

        dstRect.offset(dx, dy);

        if (arrivedX) {
            this.dx = 0;
        }
        if (arrivedY) {
            this.dy = 0;
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        angle = (float) Math.atan2(ty - y, tx - x);
        float speed = Metrics.size(R.dimen.apple_red_speed);
        float dist = speed * MainGame.getInstance().frameTime;
        dx = (float) (dist * Math.cos(angle));
        dy = (float) (dist * Math.sin(angle));
    }
}
