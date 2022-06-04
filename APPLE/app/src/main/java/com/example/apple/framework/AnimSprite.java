package com.example.apple.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimSprite extends Sprite {
    private final float framesPerSecond;
    private final int frameCount;
    private final int imageWidth;
    private final int imageHeight;

    private Rect srcRect = new Rect();
    private long createdOn;

    public boolean rotate;
    public float angle;
    private boolean isFreeze;

    public AnimSprite(float x, float y, float w, float h, int bitmapResId, float framesPerSecond, int frameCount) {
        super(x, y, w, h, bitmapResId);
        int imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        this.framesPerSecond = framesPerSecond;
        if (frameCount == 0) {
            frameCount = imageWidth / imageHeight;
            imageWidth = imageHeight;
        } else {
            imageWidth = imageWidth / frameCount;
        }
        this.imageWidth = imageWidth;
        this.frameCount = frameCount;

        createdOn = System.currentTimeMillis();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int index = Math.round(time * framesPerSecond) % frameCount;
        if (!isFreeze)
            srcRect.set(index * imageWidth, 0, (index + 1) * imageWidth, imageHeight);

        if (rotate) {
            canvas.save();
            canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            canvas.restore();
        } else {
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }
    }

    public void setFreeze(boolean value) {
        isFreeze = value;
    }

    public boolean getFreeze() {
        return isFreeze;
    }
}
