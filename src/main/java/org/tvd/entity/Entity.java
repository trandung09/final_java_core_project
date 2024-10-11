package org.tvd.entity;

import lombok.Getter;
import lombok.Setter;
import org.tvd.control.CollisionDetection;
import org.tvd.counter.Counter;
import org.tvd.frame.GamePanel;

import java.awt.*;
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

    protected boolean isInvincible;
    protected boolean isHpBarOn;
    protected boolean isCollisionOn;
    protected boolean isDead;
    protected boolean isAlive = true;
    protected boolean isAttacking;
    protected boolean imageChecker;

    protected BufferedImage[] defaultImages = new BufferedImage[8];
    protected BufferedImage[] attackImages = new BufferedImage[64];
    protected BufferedImage image;
    protected CollisionDetection detection;
    protected Direction direction = Direction.DOWN;

    protected Rectangle solidArea;
    protected Rectangle attackArea;
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;

    public Entity(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.detection = new CollisionDetection(gamePanel);

        attackArea = new Rectangle(0, 0, 40, 40);
        solidArea = new Rectangle(12, 8, 24, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void update() {}
    public abstract void render(Graphics2D g2d);
}
