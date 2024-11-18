package org.tvd.entity.monster;

import lombok.Getter;
import lombok.Setter;
import org.tvd.asset.FrameAsset;
import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.frame.GamePanel;
import org.tvd.item.ItemFactory;
import org.tvd.item.SuperItem;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

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
    }

    @Override
    public void update() {

        super.update();

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

        counter.actionCounter = 0;

        switch (gamePanel.player.getDirection()) {
            case UP -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.UP;
            case LEFT -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.LEFT;
        }

        // Fix monster position when player damages
        if (!isCollisionOn) {
            switch (direction) {
                case DOWN -> worldY -= speed * 15;
                case UP -> worldY += speed * 15;
                case LEFT -> worldX += speed * 15;
                case RIGHT ->  worldX -= speed * 15;
            }
        }
    }

    @Override
    public void counter() {

        super.counter();

        if (counter.drawCounter++ >= 10) {
            imageChecker = !imageChecker;
            counter.drawCounter = 0;
        }
    }

    @Override
    public void moving() {

        super.moving();
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
        detection.checkCollisionWithPlayer(gamePanel.player);

        if (isCollisionOn) {
            damagePlayer();
        }

        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
    }

    public void detectPlayerInSight(double defaultRadius ,
                                    double visibilityRadius,
                                    int size) {

        int playerWorldX = gamePanel.player.getWorldX() + FrameAsset.TILE_SIZE / 2;
        int playerWorldY = gamePanel.player.getWorldY() + FrameAsset.TILE_SIZE / 2;

        int tempWorldX = worldX + size;
        int tempWorldY = worldY + size;

        if (defaultRadius > visibilityRadius) {
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

    public void attackCounter(double distFromPlayer, double attackRadius) {

        if (distFromPlayer <= attackRadius) {
            if (counter.actionCounter == 100) {
                isAttacking = true;
            }

            if (attackCounter++ >= 120) {
                attackCounter = 0;
            }

            return;
        }

        attackCounter = 0;
        isAttacking = false;
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

        if (!isPlayerAlive && isPlayerInvincible) {
            return;
        }

        gamePanel.player.setInvincible(true);
        gamePanel.player.setLife(playerLife - damage);
    }

    protected SuperItem dying() {

        ItemFactory itemFactory = ItemFactory.getInstance();
        SuperItem item = null;

        switch (name) {
            case "orc" -> item = itemFactory.getItem("diamond", gamePanel);
            case "slimey" -> item = itemFactory.getItem("slimey", gamePanel);
        };

        if (item != null) {

            item.setWorldX(worldX);
            item.setWorldY(worldY);
        }

        return item;
    }

    @Override
    public void render(Graphics2D g2d) {

        int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;

        if (worldX + FrameAsset.TILE_SIZE > gamePanel.player.getWorldX() - gamePanel.player.screenX) {

            for (int i = 0; i < 8; i++) {
                if (defaultImages[i] == null) {
                    System.out.println("Image " + i + " = null");
                }
            }

            if (image == null) {
                image = defaultImages[6];
            }

            switch (direction) {
                case UP -> image = imageChecker ? defaultImages[0] : defaultImages[1];
                case DOWN -> image = imageChecker ? defaultImages[2] : defaultImages[3];
                case LEFT -> image = imageChecker ? defaultImages[4] : defaultImages[5];
                case RIGHT -> image = imageChecker ? defaultImages[6] : defaultImages[7];
            }

            if (isHpBarOn) {
               renderHpBar(g2d, screenX, screenY);
            }

            if (isInvincible) {
                g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f));
            }

            g2d.drawImage(image, screenX, screenY, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE, null);
        }
    }

    protected void renderHpBar(Graphics2D g2d, int screenX, int screenY) {

        double hpBarScale = (double) FrameAsset.TILE_SIZE / maxLife;
        double hpBar = hpBarScale * life;

        g2d.setColor(new Color(35, 35, 35));
        g2d.fillRect(screenX - 1, screenY - 16, FrameAsset.TILE_SIZE + 2, 12);

        g2d.setColor(new Color(255, 35, 35));
        g2d.fillRect(screenX, screenY - 15, (int)hpBar, 10);
    }
}
