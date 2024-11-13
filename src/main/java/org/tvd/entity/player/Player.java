package org.tvd.entity.player;

import lombok.Getter;
import lombok.Setter;

import org.tvd.asset.EntitySetter;
import org.tvd.asset.FrameAsset;
import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.entity.player.weapon.WeaponType;
import org.tvd.event.KeyHandler;
import org.tvd.event.KeyPressed;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player extends Entity implements EntityActions {

    // For camera settings
    public final int screenX = FrameAsset.SCREEN_WIDTH / 2 - FrameAsset.TILE_SIZE / 2;
    public final int screenY = FrameAsset.SCREEN_HEIGHT / 2 - FrameAsset.TILE_SIZE;

    // Receive event (from keyboard)
    private final KeyHandler input;
    private final KeyPressed pressed ;

    // Player attributes
    private int level = 1;
    private int exp   = 0;
    private int nextLevelExp = 10;
    private int maxEnergy = 100;
    private int energy = maxEnergy;
    private int manas = 0;
    private int maxManas = 10;

    // Index of selected weapon in weapon list
    private int weapon = 0;

    @Override
    public BufferedImage[] getDefaultImages() {
        return super.getDefaultImages();
    }

    private List<WeaponType> weapons = new ArrayList<>();

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        init();

        this.name = "boy";

        this.pressed = keyHandler.pressed;
        this.input = keyHandler;

        this.weapons.add(WeaponType.PICK);

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, weapons.get(weapon).name().toLowerCase());
    }

    public void init() {

        this.speed = 5;
        this.maxLife = 700;
        this.life = maxLife;

        this.isDead = false;

        worldX = FrameAsset.TILE_SIZE * 8;
        worldY = FrameAsset.TILE_SIZE * 7;
    }

    @Override
    public void update() {

        if (life == 0) {

            isDead = true;
        }

        if (isDead) {

            // gamePanel.gameStatus = GameStatus.GAME_OVER;

            return;
        }

        if (isAttacking) {
            attacking();
        }

        moving();
        counter();
    }

    @Override
    public void setAction() {

    }

    @Override
    public void resetAction() {

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
                    image = attackImageChecker ? attackImages[0] : attackImages[1];
                    tempY -= FrameAsset.TILE_SIZE;
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
                    tempX -= FrameAsset.TILE_SIZE;
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

        g2d.drawImage(image, tempX, tempY, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public void moving() {

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

    private void counter() {

        if (!pressed.isMovePressed()) {
            return;
        }

        if (counter.drawCounter++ < 10) {
            return;
        }

        counter.drawCounter = 0;
        imageChecker = !imageChecker;
    }

    @Override
    public void attacking() {

        counter.attackDrawChecker++;

        if (counter.attackDrawChecker < 5) {
            attackImageChecker = true;
        }
        else if (counter.attackDrawChecker <= 25) {
            attackImageChecker = false;
        }
        else {
            isAttacking = false;
            counter.attackDrawChecker = 0;
        }
    }

    @Override
    public void defending() {

    }

    private void switchDirection() {

        if (pressed.up) {
            direction = Direction.UP;
        }
        else if (pressed.down) {
            direction = Direction.DOWN;
        }
        else if (pressed.left) {
            direction = Direction.LEFT;
        }
        else if (pressed.right) {
            direction = Direction.RIGHT;
        }
    }
}
