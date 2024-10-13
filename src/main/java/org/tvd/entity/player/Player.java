package org.tvd.entity.player;

import lombok.Getter;
import lombok.Setter;

import org.tvd.config.EntityConfig;
import org.tvd.config.FrameConfig;
import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.event.KeyHandler;
import org.tvd.event.KeyPressed;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player extends Entity implements EntityActions {

    // For camera settings
    public final int screenX = FrameConfig.SCREEN_WIDTH / 2 - FrameConfig.TILE_SIZE / 2;
    public final int screenY = FrameConfig.SCREEN_HEIGHT / 2 - FrameConfig.TILE_SIZE;

    // Receive event (from keyboard)
    private final KeyHandler input;
    private final KeyPressed pressed ;

    // Player status
    private PlayerStatus status = PlayerStatus.NONE;

    // Player attributes
    private int level = 1;
    private int exp   = 0;
    private int nextLevelExp = 10;
    private int energy = 0;
    private int maxEnergy = 100;
    private int manas = 0;
    private int maxManas = 10;

    // Index of selected weapon in weapon list
    private int weapon = 1;

    private List<String> weapons = new ArrayList<>();

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.name = "boy";

        this.speed = 5;

        worldX = FrameConfig.TILE_SIZE * 8;
        worldY = FrameConfig.TILE_SIZE * 7;

        this.input = keyHandler;

        this.pressed = keyHandler.pressed;

        this.weapons.add("sword");
        this.weapons.add("axe");

        EntityConfig.loadDefaultEntityImage(this);
        EntityConfig.loadAttackEntityImage(this, weapons.get(weapon));
    }

    @Override
    public void update() {

        if (!isAlive) {
            return;
        }

        switch (status) {

            case NONE -> {
            }
            case ATTACKING -> attacking();
            case INVINCIBLE -> defending();
        }

        if (pressed.isMovePressed()) {

            move();
        }

        counter();

        isAttacking = pressed.enter;
    }

    @Override
    public void render(Graphics2D g2d) {

        if (image == null) image = defaultImages[6];

        int tempX = screenX;
        int tempY = screenY;

        switch (direction) {

            case UP -> {
                if (!isAttacking)
                    image = imageChecker ? defaultImages[0] : defaultImages[1];
                else {
                    image = imageChecker ? attackImages[0] : attackImages[1];
                    tempY -= FrameConfig.TILE_SIZE;
                }
            }
            case DOWN -> {
                if (!isAttacking)
                    image = imageChecker ? defaultImages[2] : defaultImages[3];
                else {
                    image = imageChecker ? attackImages[2] : attackImages[3];
                }
            }
            case LEFT -> {
                if (!isAttacking)
                    image = imageChecker ? defaultImages[4] : defaultImages[5];
                else {
                    image = imageChecker ? attackImages[4] : attackImages[5];
                    tempX -= FrameConfig.TILE_SIZE;
                }
            }
            case RIGHT -> {
                if (!isAttacking)
                    image = imageChecker ? defaultImages[6] : defaultImages[7];
                else {
                    image = imageChecker ? attackImages[6] : attackImages[7];
                }
            }
        }

        g2d.drawImage(image, tempX, tempY, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public void move() {

        if (!pressed.isMovePressed()) {
            return;
        }

        switchDirection();

        isCollisionOn = false;

        detection.checkCollisionWithTile(this);

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

    @Override
    public void attacking() {

    }

    @Override
    public void defending() {

    }

    private void counter() {

        if (counter.drawCounter++ < 10) {
            return;
        }

        counter.drawCounter = 0;
        imageChecker = !imageChecker;
    }

    private void switchDirection() {

        if (pressed.up)
            direction = Direction.UP;
        else if (pressed.down)
            direction = Direction.DOWN;
        else if (pressed.left)
            direction = Direction.LEFT;
        else if (pressed.right)
            direction = Direction.RIGHT;
    }
}
