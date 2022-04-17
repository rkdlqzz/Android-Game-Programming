package com.example.apple.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.apple.R;
import com.example.apple.framework.GameView;
import com.example.apple.game.MainGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        // 재시작 시 이전 것들이 남아있지 않도록
        GameView.view = null;
        MainGame.clear();
        super.onDestroy();
    }
}