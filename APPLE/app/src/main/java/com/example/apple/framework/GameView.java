package com.example.apple.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;
    private static final String TAG = GameView.class.getSimpleName();
    private long lastTimeNanos;
    private int framesPerSecond;
    private boolean initialized;
    private boolean running;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Metrics.width = w;
        Metrics.height = h;

        if (!initialized) {
            initView();
            initialized = true;
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        if (!running) {
            Log.d(TAG, "Running is false on doFrame()");
            return;
        }

        long now = currentTimeNanos;
        if (lastTimeNanos == 0) {
            lastTimeNanos = now;
        }
        int elapsed = (int) (now - lastTimeNanos);
        if (elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            lastTimeNanos = now;
            BaseGame game = BaseGame.getInstance();
            game.update(elapsed);
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        BaseGame.getInstance().init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return BaseGame.getInstance().onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        BaseGame.getInstance().draw(canvas);
    }

    public void pauseGame() {
        running = false;
    }

    public void resumeGame() {
        if (initialized && !running) {

            running = true;
            lastTimeNanos = 0;
            Choreographer.getInstance().postFrameCallback(this);    // 다시 프레임마다 불리도록
            Log.d(TAG, "Resuming game");
        }
    }
}
