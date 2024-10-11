package org.tvd.entity.player;

import lombok.Getter;
import lombok.Setter;

import org.tvd.config.EntityConfig;
import org.tvd.config.FrameConfig;
import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.event.KeyHandler;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

@Getter
@Setter
public class Player extends Entity implements EntityActions {

    // For camera settings
    public final int screenX = FrameConfig.SCREEN_WIDTH / 2 - FrameConfig.TILE_SIZE / 2;
    public final int screenY = FrameConfig.SCREEN_HEIGHT / 2 - FrameConfig.TILE_SIZE;

    // Receive event input
    private final KeyHandler input;

    // Player status
    private PlayerStatus status = PlayerStatus.PLAY;

    // Player attributes
    private int level = 1;
    private int exp   = 0;
    private int nextLevelExp = 10;
    private int energy = 0;
    private int maxEnergy = 100;
    private int manas = 0;
    private int maxManas = 10;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.name = "boy";

        this.speed = 5;
        this.worldY = 264;
        this.worldX = 360;

        this.input = keyHandler;

        EntityConfig.loadDefaultEntityImage(this);
        EntityConfig.loadAttackEntityImage(this, "none");
    }

    @Override
    public void update() {

        if (status == PlayerStatus.PLAY) {

            counter.drawCounter++;

            if (counter.drawCounter > 12) {

                counter.drawCounter = 0;
                // Switch image
                imageChecker = !imageChecker;
            }

            move();
        }
    }

    @Override
    public void render(Graphics2D g2d) {

        if (image == null) image = defaultImages[6];

        switch (direction) {
            case UP -> image = imageChecker ? defaultImages[0] : defaultImages[1];
            case DOWN -> image = imageChecker ? defaultImages[2] : defaultImages[3];
            case LEFT -> image = imageChecker ? defaultImages[4] : defaultImages[5];
            case RIGHT -> image = imageChecker ? defaultImages[6] : defaultImages[7];
        }

        g2d.drawImage(image, screenX, screenY, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE, null);
    }

    @Override
    public void move() {

       if (input.pressed.isMovePressed()) {

           switchDirection();

           isCollisionOn = false;

           if (!isCollisionOn) {

               switch (direction) {
                   case UP -> worldY -= speed;
                   case DOWN -> worldY += speed;
                   case LEFT -> worldX -= speed;
                   case RIGHT -> worldX += speed;
               }
           }
       }
    }

    private void switchDirection() {

        if (input.pressed.up)
            direction = Direction.UP;
        else if (input.pressed.down)
            direction = Direction.DOWN;
        else if (input.pressed.left)
            direction = Direction.LEFT;
        else if (input.pressed.right)
            direction = Direction.RIGHT;
    }

    @Override
    public void attack() {

    }

    @Override
    public void defense() {

    }
}
