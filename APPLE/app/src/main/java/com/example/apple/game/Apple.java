package com.example.apple.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.apple.R;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Joystick;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Sprite;

public class Apple extends Sprite implements CircleCollidable {
    private static final String TAG = Apple.class.getSimpleName();

    private float angle;
    private float dx, dy;
    private final Joystick joystick;

    public Apple(float x, float y, Joystick joystick) {
        super(x, y, R.dimen.apple_radius, R.mipmap.apple_red);
        this.joystick = joystick;
    }

    public void draw(Canvas canvas) {
        setDstRectWithRadius();
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        float speed = Metrics.size(R.dimen.apple_red_speed);

        dx = (float) joystick.GetActuatorX() * speed * frameTime;
        x += dx;
        dy = (float) joystick.GetActuatorY() * speed * frameTime;
        y += dy;
        angle = (float) joystick.GetAngleRadian();
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
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
}
