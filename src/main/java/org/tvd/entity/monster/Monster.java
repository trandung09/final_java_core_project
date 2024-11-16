package org.tvd.entity.monster;

import lombok.Getter;
import lombok.Setter;
import org.tvd.asset.FrameAsset;
import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.frame.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

@Getter
@Setter
public abstract class Monster extends Entity implements EntityActions {

    protected int actionNumber = 0;

    protected int experienceReward;

    protected boolean abilityAttack;
    protected boolean abilityFly;

    public Monster(GamePanel gamePanel) {
        super(gamePanel);
    }

    /**
     * Randomize monster movement direction, update monster animation
     */
    @Override
    public void setAction() {

        // Update monster animation
        if (counter.drawCounter++ >= 10) {
            counter.drawCounter = 0;
            imageChecker = !imageChecker;
        }

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
    public void moving() {

        setAction();
    }

    @Override
    public void attacking() {

    }

    @Override
    public void defending() {

        isInvincible = true;
    }

    /**
     * Use A star algorithm to find the path to the player's position
     */
    public void findPathToPlayer() {

    }

    @Override
    public void update() {

        moving();

        if (counter.drawCounter++ > 10) {
            counter.drawCounter = 0;
            imageChecker = !imageChecker;
        }

        if (isHpBarOn) {

            if (counter.hpBarCounter++ > 500) {
                isHpBarOn = false;
                counter.hpBarCounter = 0;
            }
        }

        if (isInvincible) {

            isHpBarOn = true;

            if (counter.invincibleCounter++ > 60) {
                isInvincible = false;
                counter.invincibleCounter = 0;
            }
        }
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
