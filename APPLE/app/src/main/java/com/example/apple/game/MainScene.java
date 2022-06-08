package com.example.apple.game;

import com.example.apple.R;
import com.example.apple.framework.Scene;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Joystick;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Sound;

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
        bg, item, enemy, bomb, zone, obstacle, shield, bullet, player, cloud, ui, touchUi, manager, COUNT
    }
    public Apple apple;
    private Joystick joystick;
    public Score score;
    public Stage stage;


    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        // manager
        float sx = Metrics.size(R.dimen.stage_margin_left);
        float sy = Metrics.size(R.dimen.stage_margin_top);
        stage = new Stage(sx, sy);
        add(Layer.manager, stage);
        add(Layer.manager, new EnemyGenerator());
        add(Layer.manager, new ItemGenerator());
        add(Layer.manager, new CollisionChecker());
        add(Layer.manager, new ObstacleGenerator());

        //joystick
        float jx = Metrics.width / 3.5f;
        float jy = Metrics.height - Metrics.height / 6.0f;
        joystick = new Joystick(jx, jy);
        add(Layer.touchUi, joystick);

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

    public void add(Layer layer, GameObject gameObject) {
        add(layer.ordinal(), gameObject);
    }

    @Override
    public boolean handleBackKey() {
        push(PausedScene.get());
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touchUi.ordinal();
    }

    @Override
    public void start() {
        Sound.playMusic(R.raw.bgm);
    }

    @Override
    public void pause() {
        Sound.pauseMusic();
    }

    @Override
    public void resume() {
        Sound.resumeMusic();
    }

    @Override
    public void end() {
        Sound.stopMusic();
    }
}
