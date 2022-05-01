package com.example.apple.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.apple.R;
import com.example.apple.framework.CircleCollidable;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.GameView;
import com.example.apple.framework.Joystick;
import com.example.apple.framework.Metrics;

import java.util.ArrayList;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;
    private Paint collisionPaint;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    public static void clear() {
        singleton = null;
    }

    public float frameTime;
    protected ArrayList<ArrayList<GameObject>> layers;
    public enum Layer {
        bg, enemy, player, controller, manager, COUNT
    }
    private Apple apple;
    private Joystick joystick;

    public void init() {
        initLayers(Layer.COUNT.ordinal());

        add(Layer.manager, new EnemyGenerator());

        add(Layer.manager, new CollisionChecker());

        //joystick
        float jx = Metrics.width / 4;
        float jy = Metrics.height - Metrics.height / 7;
        joystick = new Joystick(jx, jy);
        add(Layer.controller, joystick);

        // apple
        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.apple_y_offset);
        apple = new Apple(fx, fy, joystick);
        add(Layer.player, apple);

        add(Layer.bg, new Background(R.mipmap.background, Metrics.size(R.dimen.bg_speed)));

        collisionPaint = new Paint();
        collisionPaint.setStrokeWidth(5);
        collisionPaint.setStyle(Paint.Style.STROKE);
        collisionPaint.setColor(Color.RED);
    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                joystick.SetIsPressed((double) event.getX(), (double) event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.GetIsPressed()) {
                    joystick.SetActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.SetIsPressed(false);
                joystick.ResetActuator();
                return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                gobj.draw(canvas);
                if (gobj instanceof CircleCollidable) {
                    CircleCollidable cobj = (CircleCollidable) gobj;
                    canvas.drawCircle(cobj.getCenterX(), cobj.getCenterY(), cobj.getRadius(), collisionPaint);
                }
            }
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);

        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                gobj.update();
            }
        }
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> gameObjects = layers.get(layer.ordinal());
                gameObjects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        // 삭제를 예약, 이 코드가 나중에 불림 (trashcan에 담아두고 업데이트 끝난 뒤에 remove하는 방법도 존재)
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> gameObjects : layers) {
                    boolean removed = gameObjects.remove(gameObject);
                    if (!removed) continue;
                    break;
                }
            }
        });
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }

    public int objectCount() {
        int count = 0;
        for (ArrayList<GameObject> gameObjects : layers) {
            count += gameObjects.size();
        }
        return count;
    }
}
