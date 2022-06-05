package com.example.apple.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.apple.framework.Scene;
import com.example.apple.framework.GameView;
import com.example.apple.game.MainScene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainScene game = MainScene.get();
        Scene.push(game);

        setContentView(new GameView(this, null));
    }

    @Override
    protected void onPause() {
        GameView.view.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameView.view.resumeGame();
    }

    @Override
    protected void onDestroy() {
        GameView.view = null;
        Scene.clear();
        super.onDestroy();
    }
}