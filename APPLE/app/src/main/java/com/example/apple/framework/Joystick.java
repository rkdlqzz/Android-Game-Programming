package com.example.apple.framework;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.example.apple.R;

public class Joystick implements GameObject, Touchable {
    private static final String TAG = Joystick.class.getSimpleName();
    private Sprite outCircle;
    private Sprite inCircle;
    private float outCircleRadius;
    private float inCircleRadius;
    private float outCircleCY, outCircleCX;
    private double touchDistFromJoystick;
    private boolean isPressed;
    private double actuatorX, actuatorY;
    private double angle;

    public Joystick(float x, float y) {
        outCircleRadius = Metrics.size(R.dimen.joystick_bg_radius);
        inCircleRadius = Metrics.size(R.dimen.joystick_button_radius);

        outCircle = new Sprite(x, y, outCircleRadius, R.mipmap.joystick_background);
        inCircle = new Sprite(x, y, inCircleRadius, R.mipmap.joystick_button);

        outCircleCX = x;
        outCircleCY = y;

        angle = -90.0f / 180.0f * Math.PI;  // 초기 각도 설정, -90도를 라디안으로
    }


    @Override
    public void update() {
        inCircle.x = (float) (outCircleCX + actuatorX * outCircleRadius);
        inCircle.y = (float) (outCircleCY + actuatorY * outCircleRadius);

        //Log.d(TAG, "" + actuatorX + "   " + actuatorY);
    }

    @Override
    public void draw(Canvas canvas) {
        outCircle.draw(canvas);
        inCircle.setDstRectWithRadius();
        inCircle.draw(canvas);
    }

    public void SetIsPressed(double touchX, double touchY) {
        touchDistFromJoystick = Math.sqrt(
                Math.pow(outCircleCX - touchX, 2) + Math.pow(outCircleCY - touchY, 2));

        if (touchDistFromJoystick < outCircleRadius) isPressed = true;
    }

    public void SetIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean GetIsPressed() {
        return isPressed;
    }

    public void SetActuator(double touchX, double touchY) {
        double dx = touchX - outCircleCX;
        double dy = touchY - outCircleCY;
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        if (distance < outCircleRadius) {
            actuatorX = dx / outCircleRadius;
            actuatorY = dy / outCircleRadius;
        } else {
            actuatorX = dx / distance;
            actuatorY = dy / distance;
        }

        angle = Math.atan2(actuatorY, actuatorX);
    }

    public double GetActuatorX() {
        return actuatorX;
    }

    public double GetActuatorY() {
        return actuatorY;
    }

    public void ResetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double GetAngleRadian() {
        return angle;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                SetIsPressed(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                if (GetIsPressed()) {
                    SetActuator(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                SetIsPressed(false);
                ResetActuator();
                return true;
        }
        return false;
    }
}
