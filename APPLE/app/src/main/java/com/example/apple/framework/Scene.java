package com.example.apple.framework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Scene {
    private static final String TAG = Scene.class.getSimpleName();
    public float frameTime, elapsedTime;
    public static Scene getInstance() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex < 0) return null;
        return sceneStack.get(lastIndex);
    }
    public static void clear() {
        sceneStack.clear();
    }
    protected ArrayList<ArrayList<GameObject>> layers;
    private Paint collisionPaint;

    protected static ArrayList<Scene> sceneStack = new ArrayList<>();
    //    public Scene getTopScene() {
//    }
    public static void start(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in start): " + top);
            top.end();
            sceneStack.set(lastIndex, scene);
        } else {
            sceneStack.add(scene);
        }
        Log.d(TAG, "Starting(in start): " + scene);
        scene.start();
    }
    public static void push(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Pausing: " + top);
            top.pause();
        }
        sceneStack.add(scene);
        Log.d(TAG, "Starting(in push): " + scene);
        scene.start();
    }
    public static void popScene() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in pop): " + top);
            top.end();
        }
        lastIndex--;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Resuming: " + top);
            top.resume();
        } else {
            Log.e(TAG, "should end app in popScene()");
        }
    }

    public void init() {
        // bouding circle
        collisionPaint = new Paint();
        collisionPaint.setStrokeWidth(5);
        collisionPaint.setStyle(Paint.Style.STROKE);
        collisionPaint.setColor(Color.RED);

        elapsedTime = 0;
    }

    public void start(){}
    public void pause(){}
    public void resume(){}
    public void end(){}

    protected void initLayers(int count) {
        layers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        elapsedTime += frameTime;

        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                gobj.update();
            }
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject gobj : gameObjects) {
                gobj.draw(canvas);
                if (gobj instanceof CircleCollidable) {
                    CircleCollidable cobj = (CircleCollidable) gobj;
                    //canvas.drawCircle(cobj.getCenterX(), cobj.getCenterY(), cobj.getRadius(), collisionPaint);
                }
            }
        }
    }

    public void add(int layerIndex, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> gameObjects = layers.get(layerIndex);
                gameObjects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        // 삭제를 예약, 이 코드가 나중에 불림 (trashcan에 담아두고 업데이트 끝난 뒤에 remove하는 방법도 존재)
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> gameObjects : layers) {
                    boolean removed = gameObjects.remove(gameObject);
                    if (!removed) continue;
                    if (gameObject instanceof Recyclable) {
                        RecycleBin.add((Recyclable) gameObject);
                    }
                    break;
                }
            }
        });
    }

    public int objectCount() {
        int count = 0;
        for (ArrayList<GameObject> gameObjects : layers) {
            count += gameObjects.size();
        }
        return count;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}

