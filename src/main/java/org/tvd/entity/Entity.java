package org.tvd.entity;

import lombok.Getter;
import lombok.Setter;
import org.tvd.control.CollisionDetection;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
    protected boolean isDead;
    protected boolean isAlive = true;
    protected boolean imageChecker;
    protected boolean attackImageChecker;

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

        if (!gamePanel.keyHandler.pressed.isMovePressed()) {
            return;
        }

        if (isCollisionOn) {
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

            if (counter.attackCounter > 25) {
                isAttacking = false;
                counter.attackCounter = 0;
            }
        }

        if (!gamePanel.keyHandler.pressed.isMovePressed()) {
            return;
        }

        if (counter.drawCounter++ > 10) {
            counter.drawCounter = 0;
        }
    }

    public void updateAnimationImage() {

        if (counter.drawCounter == 10) {
            imageChecker = !imageChecker;
        }

        if (isAttacking) {

            attackImageChecker = counter.attackCounter <= 5;
        }
    }

    public abstract void update();
    public abstract void setAction();
    public abstract void resetAction();
    public abstract void render(Graphics2D g2d);
}
