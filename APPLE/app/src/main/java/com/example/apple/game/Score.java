package com.example.apple.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.apple.R;
import com.example.apple.framework.BitmapPool;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Metrics;

public class Score implements GameObject {
    private final Bitmap bitmap;
    private final int srcCharWidth, srcCharHeight;
    private float right, top;
    private final float dstCharWidth, dstCharHeight;
    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    private float score;
    private int displayScore;

    public Score() {
        this.bitmap = BitmapPool.get(R.mipmap.number_240x32);
        this.right = Metrics.width - Metrics.size(R.dimen.score_margin_right);
        this.top = Metrics.size(R.dimen.score_margin_top);
        this.dstCharWidth = Metrics.size(R.dimen.score_digit_width);
        this.srcCharWidth = bitmap.getWidth() / 10;
        this.srcCharHeight = bitmap.getHeight();
        this.dstCharHeight = dstCharWidth * srcCharHeight / srcCharWidth;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float get() {
        return score;
    }

    @Override
    public void update() {
        add(MainScene.getInstance().frameTime);  // 플레이한 시간만큼 score 추가

        int diff = (int) score - displayScore;
        if (diff == 0) return;
        if (-10 < diff && diff < 0) {
            displayScore--;
        } else if (0 < diff && diff < 10) {
            displayScore++;
        } else {
            displayScore += diff / 10;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.displayScore;
        float x = right;
        while (value > 0) {
            int digit = value % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            value /= 10;
        }
    }

    public void add(float score) {
        this.score += score;
    }
}
