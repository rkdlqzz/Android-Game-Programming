package com.example.apple.framework;

public class CollisionHelper {
    public static boolean collides(CircleCollidable o1, CircleCollidable o2) {
        float cx1 = o1.getCenterX();
        float cy1 = o1.getCenterY();
        float r1 = o1.getRadius();

        float cx2 = o2.getCenterX();
        float cy2 = o2.getCenterY();
        float r2 = o2.getRadius();

        float distance = (float) Math.sqrt(Math.pow(cx1 - cx2, 2) + Math.pow(cy1 - cy2, 2));

        if (distance > r1 + r2) return false;
        return true;
    }
}
