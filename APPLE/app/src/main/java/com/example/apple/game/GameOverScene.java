package com.example.apple.game;

import com.example.apple.R;
import com.example.apple.framework.Button;
import com.example.apple.framework.Metrics;
import com.example.apple.framework.Scene;
import com.example.apple.framework.Sprite;

public class GameOverScene extends Scene {
    private static GameOverScene singleton;

    public static GameOverScene get() {
        if (singleton == null) {
            singleton = new GameOverScene();
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
        initLayers(GameOverScene.Layer.COUNT.ordinal());

        add(GameOverScene.Layer.ui.ordinal(), new Sprite(
                Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height, R.mipmap.transparent));

        float score_text_width = Metrics.size(R.dimen.text_score_width);
        float score_text_height = Metrics.size(R.dimen.text_score_height);

        add(GameOverScene.Layer.ui.ordinal(), new Sprite(
                Metrics.width / 2, Metrics.height / 2 - score_text_height,
                score_text_width, score_text_height, R.mipmap.game_over));

        float btn_width = Metrics.size(R.dimen.button_width);
        float btn_height = Metrics.size(R.dimen.button_height);

        add(GameOverScene.Layer.touchUi.ordinal(), new Button(
                Metrics.width / 2, Metrics.height / 2, btn_width, btn_height,
                R.mipmap.ok_button, R.mipmap.ok_button_p, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.released) {
                    finish();
                }
                return true;
            }
        }));
    }

    @Override
    protected int getTouchLayerIndex() {
        return GameOverScene.Layer.touchUi.ordinal();
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
