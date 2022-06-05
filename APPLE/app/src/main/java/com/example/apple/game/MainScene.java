package com.example.apple.game;

import android.view.MotionEvent;

import com.example.apple.R;
import com.example.apple.framework.Scene;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Joystick;
import com.example.apple.framework.Metrics;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private static MainScene singleton;
    public static MainScene get() {
        if (singleton == null) {
            singleton = new MainScene();
        }
        return singleton;
    }

    public enum Layer {
        bg, item, enemy, bomb, zone, shield, bullet, player, cloud, controller, ui, manager, COUNT
    }
    public Apple apple;
    private Joystick joystick;
    public Score score;
    public Stage stage;


    public void init() {
        initLayers(Layer.COUNT.ordinal());

        // manager
        stage = new Stage();
        add(Layer.manager, stage);
        add(Layer.manager, new EnemyGenerator());
        add(Layer.manager, new ItemGenerator());
        add(Layer.manager, new CollisionChecker());

        //joystick
        float jx = Metrics.width / 3.5f;
        float jy = Metrics.height - Metrics.height / 6.0f;
        joystick = new Joystick(jx, jy);
        add(Layer.controller, joystick);

        // apple
        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.apple_y_offset);
        apple = new Apple(fx, fy, joystick);
        add(Layer.player, apple);

        // background, cloud
        add(Layer.bg, new Background(R.mipmap.background, Metrics.size(R.dimen.bg_speed)));
        add(Layer.cloud, new Background(R.mipmap.clouds, Metrics.size(R.dimen.cloud_speed)));

        // score
        score = new Score();
        add(Layer.ui, score);
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

    public void add(Layer layer, GameObject gameObject) {
        add(layer.ordinal(), gameObject);
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }
}
