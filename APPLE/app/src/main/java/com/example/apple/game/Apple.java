package com.example.apple.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.apple.R;
import com.example.apple.framework.BitmapPool;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.Joystick;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Sprite;

public class Apple extends Sprite implements CircleCollidable {
    private static final String TAG = Apple.class.getSimpleName();

    private float angle;
    private float dx, dy;
    private final Joystick joystick;

    private float speed;
    private float durationSpeedUp;
    private Bitmap bitmapRedApple;
    private Bitmap bitmapGreenApple;
    public WoodShield shield;
    private int numOfBullet;
    private float elapsedTimeForFire;
    private float fireInterval = 0.2f;
    private boolean sizeChange;
    private boolean getBigger;
    private float sizeChangeDuration;
    private final float ORI_RADIUS, MAX_RADIUS, MIN_RADIUS;
    public static float GROWING_SPEED = Metrics.width / (float) 2.9f;

    public Apple(float x, float y, Joystick joystick) {
        super(x, y, R.dimen.apple_radius, R.mipmap.apple_red);
        this.joystick = joystick;
        speed = Metrics.size(R.dimen.apple_red_speed);

        durationSpeedUp = 0.0f;
        bitmapRedApple = BitmapPool.get(R.mipmap.apple_red);
        bitmapGreenApple = BitmapPool.get(R.mipmap.apple_green);

        ORI_RADIUS = radius;
        MAX_RADIUS = radius * 1.3f;
        MIN_RADIUS = radius * 0.7f;
    }

    public void update() {
        float frameTime = MainScene.getInstance().frameTime;

        updateSize(frameTime);
        updateSpeedUp(frameTime);

        dx = (float) joystick.GetActuatorX() * speed * frameTime;
        dy = (float) joystick.GetActuatorY() * speed * frameTime;
        checkOutOfScreen();
        x += dx;
        y += dy;
        angle = (float) joystick.GetAngleRadian();

        checkHasBulletAndFire(frameTime);
    }

    private void updateSpeedUp(float frameTime) {
        if (durationSpeedUp <= 0.0f) {  // 아이템 지속시간 끝나면 speed, bitmap 초기화
            speed = Metrics.size(R.dimen.apple_red_speed);
            durationSpeedUp = 0.0f;
            if (bitmap == bitmapGreenApple)
                bitmap = bitmapRedApple;
            return;
        } else {
            durationSpeedUp -= frameTime;
            speed = Metrics.size(R.dimen.apple_green_speed);

            if (durationSpeedUp < 1.5f) {   // SpeedUp 지속시간이 1.5초 남은 경우 깜빡이도록
                if (bitmap == bitmapRedApple)
                    bitmap = bitmapGreenApple;
                else bitmap = bitmapRedApple;
            } else {
                bitmap = bitmapGreenApple;
            }
        }
    }

    public void draw(Canvas canvas) {
        setDstRectWithRadius();
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
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
        return radius - radius * 0.07f;
    }

    public void setDurationSpeedUp(float value) {
        durationSpeedUp = value;
    }

    public void setNumOfBullet(int value) {
        numOfBullet = value;
    }

    private void checkHasBulletAndFire(float frameTime) {
        if (numOfBullet > 0)
        {
            elapsedTimeForFire += frameTime;
            if (elapsedTimeForFire >= fireInterval) {
                fireSeed();
                elapsedTimeForFire -= fireInterval;
            }
        }
    }

    public void fireSeed() {
        MainScene game = MainScene.get();
        SeedBullet bullet = new SeedBullet(x, y, angle);
        game.add(MainScene.Layer.bullet, bullet);
        numOfBullet -= 1;
    }

    private void checkOutOfScreen() {
        if (dstRect.top <= 0 && dy < 0)
            dy = 0;
        if (dstRect.bottom >= Metrics.height && dy > 0)
            dy = 0;
        if (dstRect.left <= 0 && dx < 0)
            dx = 0;
        if (dstRect.right >= Metrics.width && dx > 0)
            dx = 0;
    }

    public boolean getSizeChange() {
        return sizeChange;
    }

    public void changeSize(boolean getBigger, float duration) {
        sizeChange = true;
        this.getBigger = getBigger;
        sizeChangeDuration = duration;
    }

    private void updateSize(float frameTime) {
        if (!sizeChange) return;

        sizeChangeDuration -= frameTime;

        // 최대크기까지 커지거나 최소크기까지 작아지도록
        if (sizeChangeDuration > 0.0f) {
            if (getBigger) {
                if (radius < MAX_RADIUS) {
                    radius += frameTime * GROWING_SPEED;
                }
            } else {
                if (radius > MIN_RADIUS) {
                    radius -= frameTime * GROWING_SPEED;
                }
            }
        } else {
            if (getBigger) {
                if (radius > ORI_RADIUS) {
                    radius -= frameTime * GROWING_SPEED;
                } else {
                    radius = ORI_RADIUS;
                    sizeChange = false;
                }
            } else {
                if (radius < ORI_RADIUS) {
                    radius += frameTime * GROWING_SPEED;
                } else {
                    radius = ORI_RADIUS;
                    sizeChange = false;
                }
            }
        }

        setDstRectWithRadius();
    }
}
