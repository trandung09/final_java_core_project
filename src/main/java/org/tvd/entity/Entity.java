package org.tvd.entity;

import lombok.Getter;
import lombok.Setter;
import org.tvd.control.CollisionDetection;
import org.tvd.entity.player.Player;
import org.tvd.frame.GamePanel;
import org.tvd.setter.FrameAsset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Getter
@Setter
public abstract class Entity {

    protected final GamePanel gamePanel;

    protected Counter counter = new Counter();

    protected String name;

    protected int speed;
    protected int damage;
    protected int worldX;
    protected int worldY;

    protected int maxLife;
    protected int life;

    protected boolean isHpBarOn;
    protected boolean isCollisionOn;
    protected boolean isAttacking;
    protected boolean isInvincible;
    protected boolean isAlive = true;
    protected boolean isSleeping = false;
    protected boolean imageChecker;
    protected boolean attackImageChecker;

    protected ArrayList<Projectile> projectiles = new ArrayList<>();

    protected BufferedImage[] defaultImages = new BufferedImage[8];
    protected BufferedImage[] attackImages = new BufferedImage[8];
    protected BufferedImage image;
    protected CollisionDetection detection;
    protected Direction direction = Direction.DOWN;

    public Rectangle solidArea;
    public Rectangle attackArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public Entity(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.detection = new CollisionDetection(gamePanel);

        attackArea = new Rectangle(0, 0, 40, 40);
        solidArea = new Rectangle(12, 8, 24, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void moving() {

        if (isCollisionOn) {
            return;
        }

        if (isSleeping) {
            return;
        }

        switch (direction) {
            case UP -> worldY -= speed;
            case DOWN -> worldY += speed;
            case LEFT -> worldX -= speed;
            case RIGHT -> worldX += speed;
        }
    }

    public void counter() {

        if (isAttacking) {
            counter.attackCounter++;
            if (counter.attackCounter <= 5) {
                attackImageChecker = true;
            }
            else if (counter.attackCounter <= 25) {
                attackImageChecker = false;
            }
            else {
                attackImageChecker = false;
                isAttacking = false;
                counter.attackCounter = 0;
            }
        }

        if (isInvincible) {
            counter.invincibleCounter++;
            if (counter.invincibleCounter >= 60) {
                isInvincible = false;
                counter.invincibleCounter = 0;
            }
          
        }

        if (isHpBarOn && counter.hpBarCounter++ >= 500) {
            isHpBarOn = false;
            counter.hpBarCounter = 0;
        }
    }

    public void update() {

        if (life <= 0) {
            isAlive = false;
        }

        setAction();
    }

    public abstract void setAction();
    public abstract void resetAction();

    public void render(Graphics2D g2d) {

        int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;

        if (worldX + FrameAsset.TILE_SIZE > gamePanel.player.getWorldX() - gamePanel.player.screenX &&
                worldX - FrameAsset.TILE_SIZE < gamePanel.player.getWorldX() + gamePanel.player.screenX &&
                worldY + FrameAsset.TILE_SIZE > gamePanel.player.getWorldY() - gamePanel.player.screenY &&
                worldY - FrameAsset.TILE_SIZE < gamePanel.player.getWorldY() + gamePanel.player.screenY
        ) {

            if (image == null) {
                image = defaultImages[6];
            }

            switch (direction) {

                case UP -> {
                    if (!isAttacking)
                        image = imageChecker ? defaultImages[0] : defaultImages[1];
                    else {
                        image = attackImageChecker ? attackImages[0] : attackImages[1];
                        screenY -= FrameAsset.TILE_SIZE;
                    }
                }
                case DOWN -> {
                    if (!isAttacking)
                        image = imageChecker ? defaultImages[2] : defaultImages[3];
                    else {
                        image = attackImageChecker ? attackImages[2] : attackImages[3];
                    }
                }
                case LEFT -> {
                    if (!isAttacking)
                        image = imageChecker ? defaultImages[4] : defaultImages[5];
                    else {
                        image = attackImageChecker ? attackImages[4] : attackImages[5];
                        screenX -= FrameAsset.TILE_SIZE;
                    }
                }
                case RIGHT -> {
                    if (!isAttacking)
                        image = imageChecker ? defaultImages[6] : defaultImages[7];
                    else {
                        image = attackImageChecker ? attackImages[6] : attackImages[7];
                    }
                }
            }

            if (isHpBarOn) {
                renderHpBar(g2d, screenX - 1, screenY - 16);
            }

            if (isInvincible) {
                g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f));
            }

            g2d.drawImage(image, screenX, screenY, image.getWidth(), image.getHeight(), null);
            g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void renderHpBar(Graphics2D g2d, int screenX, int screenY) {

        double hpBarScale = (double) FrameAsset.TILE_SIZE / maxLife;
        double hpBar = hpBarScale * life;

        g2d.setColor(new Color(35, 35, 35));
        g2d.fillRect(screenX - 1, screenY - 16, FrameAsset.TILE_SIZE  + 2, 12);

        g2d.setColor(new Color(255, 35, 35));
        g2d.fillRect(screenX, screenY - 15, (int)hpBar, 10);
    }
}
