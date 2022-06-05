package com.example.apple.game;

import android.graphics.Canvas;

import com.example.apple.framework.CollisionHelper;
import com.example.apple.framework.GameObject;

import java.util.ArrayList;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainScene game = MainScene.get();
        ArrayList<GameObject> players = game.objectsAt(MainScene.Layer.player);
        ArrayList<GameObject> enemies = game.objectsAt(MainScene.Layer.enemy);
        ArrayList<GameObject> items = game.objectsAt(MainScene.Layer.item);
        ArrayList<GameObject> bombs = game.objectsAt(MainScene.Layer.bomb);
        ArrayList<GameObject> shields = game.objectsAt(MainScene.Layer.shield);
        ArrayList<GameObject> zones = game.objectsAt(MainScene.Layer.zone);
        ArrayList<GameObject> bullets = game.objectsAt(MainScene.Layer.bullet);

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
                    // freeze 상태인 enemy와 충돌 시 적 제거
                    if (enemy.getFreeze()) {
                        game.remove(enemy);
                        game.score.add(enemy.getScore());   // 제거한 적의 score만큼 점수 추가
                        collided = true;
                    }
                    else
                    {
                        //System.exit(0); // player와 enemy 충돌 시 게임오버    finish()?
                    }
                    break;
                }
            }
            if (collided) {
                continue;
            }
            // enemy - leaf bomb
            for (GameObject o2 : bombs) {
                if (!(o2 instanceof LeafBomb)) {
                    continue;
                }
                LeafBomb bomb = (LeafBomb) o2;
                if (CollisionHelper.collides(enemy, bomb)) {
                    game.remove(enemy);
                    game.score.add(enemy.getScore());   // 제거한 적의 score만큼 점수 추가
                    collided = true;
                    break;
                }
            }
            if (collided) {
                continue;
            }
            // enemy - safe zone
            for (GameObject o2 : zones) {
                if (!(o2 instanceof SafeZone)) {
                    continue;
                }
                SafeZone zone = (SafeZone) o2;
                if (CollisionHelper.collides(enemy, zone)) {
                    game.remove(enemy);
                    game.score.add(enemy.getScore());   // 제거한 적의 score만큼 점수 추가
                    collided = true;
                    break;
                }
            }
            if (collided) {
                continue;
            }
            // enemy - wood shield
            for (GameObject o2 : shields) {
                if (!(o2 instanceof WoodShield)) {
                    continue;
                }
                WoodShield shield = (WoodShield) o2;
                if (CollisionHelper.collides(enemy, shield)) {
                    game.remove(enemy);
                    game.score.add(enemy.getScore());   // 제거한 적의 score만큼 점수 추가
                    collided = true;
                    break;
                }
            }
            if (collided) {
                continue;
            }
            // enemy - seed bullet
            for (GameObject o2 : bullets) {
                if (!(o2 instanceof SeedBullet)) {
                    continue;
                }
                SeedBullet bullet = (SeedBullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    game.remove(enemy);
                    game.score.add(enemy.getScore());   // 제거한 적의 score만큼 점수 추가
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
                    item.useItem(player);
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
