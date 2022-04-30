package com.example.apple.framework;

import android.graphics.RectF;

public interface CircleCollidable {
    public RectF getBoundingRect();
    public float getCenterX();
    public float getCenterY();
    public float getRadius();
}
