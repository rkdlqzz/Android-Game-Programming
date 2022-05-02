package com.example.apple.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

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
    public float durationSpeedUp;
    private Bitmap bitmapRedApple;
    private Bitmap bitmapGreenApple;

    public Apple(float x, float y, Joystick joystick) {
        super(x, y, R.dimen.apple_radius, R.mipmap.apple_red);
        this.joystick = joystick;
        speed = Metrics.size(R.dimen.apple_red_speed);

        durationSpeedUp = 0.0f;
        bitmapRedApple = BitmapPool.get(R.mipmap.apple_red);
        bitmapGreenApple = BitmapPool.get(R.mipmap.apple_green);
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;

        updateSpeedUp(frameTime);

        dx = (float) joystick.GetActuatorX() * speed * frameTime;
        x += dx;
        dy = (float) joystick.GetActuatorY() * speed * frameTime;
        y += dy;
        angle = (float) joystick.GetAngleRadian();
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

            if (durationSpeedUp < 1.5f) {   // SpeedUp 지속시간이 1초 남은 경우 깜빡이도록
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

    public void getItem(int type, float duration) {
        switch (type) {
            case 0:     // speed up
                durationSpeedUp = duration;
                break;
            case 1:     // leaf bomb
                break;
            case 2:     // wood shield
                break;
            default:
                break;
        }
    }
}
