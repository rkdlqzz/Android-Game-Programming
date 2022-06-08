package com.example.apple.game;

import android.graphics.Canvas;

import com.example.apple.R;
import com.example.apple.framework.CollisionHelper;
import com.example.apple.framework.GameObject;
import com.example.apple.framework.Sound;

import java.util.ArrayList;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainScene game = MainScene.get();
        ArrayList<GameObject> players = game.objectsAt(MainScene.Layer.player.ordinal());
        ArrayList<GameObject> enemies = game.objectsAt(MainScene.Layer.enemy.ordinal());
        ArrayList<GameObject> items = game.objectsAt(MainScene.Layer.item.ordinal());
        ArrayList<GameObject> bombs = game.objectsAt(MainScene.Layer.bomb.ordinal());
        ArrayList<GameObject> shields = game.objectsAt(MainScene.Layer.shield.ordinal());
        ArrayList<GameObject> zones = game.objectsAt(MainScene.Layer.zone.ordinal());
        ArrayList<GameObject> bullets = game.objectsAt(MainScene.Layer.bullet.ordinal());
        ArrayList<GameObject> obstacles = game.objectsAt(MainScene.Layer.obstacle.ordinal());

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
                        Sound.playEffect(R.raw.bonus_score);
                        collided = true;
                    }
                    else
                    {
                        // player와 enemy 충돌 시 게임오버
                        Sound.playEffect(R.raw.game_over);
                        game.push(GameOverScene.get());
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
                    Sound.playEffect(R.raw.bonus_score);
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
                    Sound.playEffect(R.raw.bonus_score);
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
                    Sound.playEffect(R.raw.bonus_score);
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
                    Sound.playEffect(R.raw.bonus_score);
                    collided = true;
                    break;
                }
            }
            if (collided) {
                continue;
            }
            // enemy - obstacle
            for (GameObject o2 : obstacles) {
                if (!(o2 instanceof Obstacle)) {
                    continue;
                }
                Obstacle obstacle = (Obstacle) o2;
                if (CollisionHelper.collides(enemy, obstacle)) {
                    // 적이 커지거나 작아지도록, 이미 변하는 상태면 작용 x 하도록
                    if (!enemy.getSizeChange())
                        enemy.changeSize(obstacle.getRBool(), obstacle.duration);
                    break;
                }
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
                    Sound.playEffect(R.raw.get_item);
                    item.useItem(player);
                    collided = true;
                    break;
                }
            }
            if (collided) {
                continue;
            }
        }

        // obstacle - player
        for (GameObject o1 : obstacles) {
            if (!(o1 instanceof Obstacle)) {
                continue;
            }
            Obstacle obstacle = (Obstacle) o1;
            for (GameObject o2 : players) {
                if (!(o2 instanceof Apple)) {
                    continue;
                }
                Apple player = (Apple) o2;
                if (CollisionHelper.collides(obstacle, player)) {
                    // 캐릭터가 커지거나 작아지도록, 이미 변하는 상태면 작용 x 하도록
                    if (!player.getSizeChange())
                        player.changeSize(obstacle.getRBool(), obstacle.duration);
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
