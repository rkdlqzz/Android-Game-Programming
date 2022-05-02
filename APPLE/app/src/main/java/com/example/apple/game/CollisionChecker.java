package com.example.apple.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.apple.app.MainActivity;
import com.example.apple.framework.CollisionHelper;
import com.example.apple.framework.GameObject;

import java.util.ArrayList;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        ArrayList<GameObject> players = game.objectsAt(MainGame.Layer.player);
        ArrayList<GameObject> enemies = game.objectsAt(MainGame.Layer.enemy);
        ArrayList<GameObject> items = game.objectsAt(MainGame.Layer.item);

        // enemy - player
        for (GameObject o1 : enemies) {
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
            boolean collided = false;
            for (GameObject o2 : players) {
                if (!(o2 instanceof Apple)) {
                    continue;
                }
                Apple player = (Apple) o2;
                if (CollisionHelper.collides(enemy, player)) {
                    //game.remove(enemy);
                    //System.exit(0); // player와 enemy 충돌 시 게임오버
                    collided = true;
                    break;
                }
            }
            if (collided) {
                continue;
            }
        }

        // item - player
        for (GameObject o1 : items) {
            if (!(o1 instanceof Item)) {
                continue;
            }
            Item item = (Item) o1;
            boolean collided = false;
            for (GameObject o2 : players) {
                if (!(o2 instanceof Apple)) {
                    continue;
                }
                Apple player = (Apple) o2;
                if (CollisionHelper.collides(item, player)) {
                    game.remove(item);
                    player.getItem(item.getType(), item.getDuration());
                    collided = true;
                    break;
                }
            }
            if (collided) {
                continue;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
