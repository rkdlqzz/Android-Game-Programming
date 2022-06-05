package com.example.apple.game;

import com.example.apple.R;
import com.example.apple.framework.Button;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Scene;
import com.example.apple.framework.Sprite;

public class PausedScene extends Scene {
    private static PausedScene singleton;
    public static PausedScene get() {
        if (singleton == null) {
            singleton = new PausedScene();
            singleton.init();
        }
        return singleton;
    }

    public enum Layer {
        ui, touchUi, COUNT;
    }

    @Override
    public void init() {
        super.init();
        initLayers(Layer.COUNT.ordinal());

        add(Layer.ui.ordinal(), new Sprite(
                Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height,
                R.mipmap.transparent));

        float btn_width = Metrics.size(R.dimen.button_width);
        float btn_height = Metrics.size(R.dimen.button_height);
        float btn_x = Metrics.width / 2;
        float btn_y = Metrics.height / 2 - btn_height / 2;

        add(Layer.touchUi.ordinal(), new Button(
                btn_x, btn_y, btn_width, btn_height,
                R.mipmap.resume_button, R.mipmap.resume_button_p, new Button.Callback()
        {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.released) {
                    Scene.popScene();
                }
                return true;
            }
        }));
        btn_y += btn_height + Metrics.size(R.dimen.button_diff);
        add(Layer.touchUi.ordinal(), new Button(
                btn_x, btn_y, btn_width, btn_height,
                R.mipmap.quit_button, R.mipmap.quit_button_p, new Button.Callback()
        {
            @Override
            public boolean onTouch(Button.Action action) {
                finish();
                return true;
            }
        }));
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touchUi.ordinal();
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean handleBackKey() {
        Scene.popScene();
        return true;
    }
}
