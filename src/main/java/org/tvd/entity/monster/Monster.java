package org.tvd.entity.monster;

import java.awt.*;
import java.util.Random;

import org.tvd.enums.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.frame.GamePanel;
import org.tvd.item.ItemFactory;
import org.tvd.item.SuperItem;
import org.tvd.setter.FrameAsset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Monster extends Entity implements EntityActions {

    protected int actionNumber = 0;
    protected int experienceReward;

    protected int attackCounter;

    protected boolean abilityAttack;
    protected boolean abilityFly;

    public Monster(GamePanel gamePanel) {

        super(gamePanel);

        init();
    }

    public void init() {

        this.isAlive = true;
        this.abilityAttack = true;
        this.abilityFly = false;
        this.direction = Direction.RIGHT;

        this.solidArea = new Rectangle(8, 8, 40, 40);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.attackArea = new Rectangle(0, 0, 45, 45);
    }

    @Override
    public void update() {

        super.update();

        if (isAttacking) {

            attacking();
        }

        isCollisionOn = false;

        detection.checkCollisionWithItems(this, gamePanel.itemManager);
        detection.checkCollisionWithOtherEntity(this, gamePanel.player);
        detection.checkCollisionWithOtherEntities(this, gamePanel.monsterManager);

        detection.checkCollisionWithTile(this);

        moving();
        counter();
    }

    /**
     * Randomize monster movement direction, update monster animation
     */
    @Override
    public void setAction() {

        // Randomize monster movement direction
        if (counter.actionCounter++ >= 150) {

            actionNumber = new Random().nextInt(100) + 1;

            if (actionNumber < 26) direction = Direction.UP;
            else if (actionNumber < 51) direction = Direction.DOWN;
            else if (actionNumber < 76) direction = Direction.LEFT;
            else direction = Direction.RIGHT;

            counter.actionCounter = 0;
        }
    }

    /**
     * Fixed monster actions when attacked by player
     * + Makes monsters move away from the player when damaged
     * + Update the monster's direction facing the player
     */
    @Override
    public void resetAction() {

//        counter.actionCounter = 0;

        switch (gamePanel.player.getDirection()) {
            case UP -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.UP;
            case LEFT -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.LEFT;
        }

        // Fix monster position when player damages

        switch (direction) {
            case DOWN -> worldY -= speed * 15;
            case UP -> worldY += speed * 15;
            case LEFT -> worldX += speed * 15;
            case RIGHT ->  worldX -= speed * 15;
        }
    }

    @Override
    public void counter() {

        super.counter();

        if (counter.drawCounter++ >= 10 && life >= 0) {
            imageChecker = !imageChecker;
            counter.drawCounter = 0;
        }
    }

    @Override
    public void attacking() {

        if (counter.attackCounter <= 5 || counter.attackCounter > 25) {
            return;
        }

        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;

        switch (direction) {
            case UP -> worldY -= attackArea.height;
            case DOWN -> worldY += attackArea.height;
            case LEFT -> worldX -= attackArea.width;
            case RIGHT -> worldX += attackArea.width;
        }

        solidArea.height = attackArea.height;
        solidArea.width = attackArea.width;

        isCollisionOn = false;
        detection.checkCollisionWithOtherEntity(this, gamePanel.player);

        if (isCollisionOn) {
            damagePlayer();
        }

        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
    }

    public void detectPlayerInSight(double visibilityRadius, double attackRadius) {

        int width = worldX - gamePanel.player.getWorldX() + FrameAsset.TILE_SIZE / 2;
        int height = worldY - gamePanel.player.getWorldY() + FrameAsset.TILE_SIZE / 2;
        
        double distFromPlayer = Math.sqrt(
                Math.pow(width, 2) + Math.pow(height, 2)
        );

        enableAttackAction(distFromPlayer, attackRadius);
        findPlayerInSight(distFromPlayer, visibilityRadius, FrameAsset.TILE_SIZE);
    }

    public void findPlayerInSight(double distFromPlayer ,
                                    double visibilityRadius,
                                    int size) {

        int tempWorldX = worldX + size;
        int tempWorldY = worldY + size;

        int playerWorldX = gamePanel.player.getWorldX() + FrameAsset.TILE_SIZE / 2;
        int playerWorldY = gamePanel.player.getWorldY() + FrameAsset.TILE_SIZE / 2;

        if (distFromPlayer > visibilityRadius) {
            return;
        }

        if (gamePanel.player.isSleeping()) {
            return;
        }

        if (tempWorldY > playerWorldY + FrameAsset.TILE_SIZE / 12) {
            direction = Direction.UP;
            if (!isCollisionOn) {
                worldY -= speed;
            }
        }
        else if (tempWorldY < playerWorldY) {
            direction = Direction.DOWN;
            if (!isCollisionOn) {
                worldY += speed;
            }
        }
        else if (tempWorldY == playerWorldY) {
            if (tempWorldX < playerWorldX) {
                direction = Direction.RIGHT;
                if (!isCollisionOn) {
                    worldX += speed;
                }
            }
            else {
                direction = Direction.LEFT;
                if (!isCollisionOn) {
                    worldX -= speed;
                }
            }
        }
    }

    public void enableAttackAction(double distFromPlayer, double attackRadius) {

        if (distFromPlayer <= attackRadius) {

            if (counter.actionCounter == 80 && !gamePanel.player.isSleeping()) {
                isAttacking = true;
            }

            attackCounter++;

            // attackCounter != counter.attackCounter
            if (attackCounter >= 120) {
                attackCounter = 0;
            }
        }
        else {
            attackCounter = 0;
            isAttacking = false;
        }
    }

    @Override
    public void defending() {

        isInvincible = true;
    }

    /**
     * Use DFS algorithm to find the path to the player's position
     */
    public void findPathToPlayer() {

    }

    public void damagePlayer() {

        boolean isPlayerAlive = gamePanel.player.isAlive();
        boolean isPlayerInvincible = gamePanel.player.isInvincible();
        int playerLife = gamePanel.player.getLife();

        if (!isPlayerAlive || playerLife <= 0 || isPlayerInvincible) {
            return;
        }

        gamePanel.sound.playSE(6);
        gamePanel.player.setLife(playerLife - damage);
        gamePanel.player.setInvincible(true);
    }

    protected SuperItem dying(String monsterName) {

        ItemFactory itemFactory = ItemFactory.getInstance();
        SuperItem item = null;

        switch (monsterName) {
            case "slimey" -> item = itemFactory.getItem("coin", gamePanel);
            case "orc" -> item = itemFactory.getItem("mana", gamePanel);
            case "skeletonlord" -> item = itemFactory.getItem("key", gamePanel);
        }

        if (item != null) {

            item.setWorldX(worldX);
            item.setWorldY(worldY);
        }

        return item;
    }
}
