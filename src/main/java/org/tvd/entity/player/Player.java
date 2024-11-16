package org.tvd.entity.player;

import lombok.Getter;
import lombok.Setter;

import org.tvd.asset.EntitySetter;
import org.tvd.asset.FrameAsset;
import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.entity.monster.Monster;
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
    private int experience = 0;
    private int nextLevelExp = 10;
    private int maxEnergy = 100;
    private int energy = maxEnergy;
    private int manas = 0;
    private int maxManas = 10;

    // Default index of selected weapon in weapon list
    // + index 0: axe
    // + index 1: pick
    // + index 2: sword
    private int weapon = 1;

    @Override
    public BufferedImage[] getDefaultImages() {
        return super.getDefaultImages();
    }

    private List<WeaponType> weapons = new ArrayList<>();

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.init();

        this.name = "boy";

        this.pressed = keyHandler.pressed;
        this.input = keyHandler;

        this.weapons.add(WeaponType.AXE);
        this.weapons.add(WeaponType.PICK);
        this.weapons.add(WeaponType.SWORD);

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, weapons.get(weapon).name().toLowerCase());
    }

    public void init() {

        this.speed = 5;
        this.maxLife = 700;
        this.life = maxLife;

        this.isDead = false;

        this.worldX = FrameAsset.TILE_SIZE * 8;
        this.worldY = FrameAsset.TILE_SIZE * 7;
    }

    @Override
    public void update() {

        if (life <= 0) {

            isDead = true;
            isAlive = false;
        }

        if (isDead) {

            // gamePanel.gameStatus = GameStatus.GAME_OVER;

            return;
        }

        if (isAttacking) {
            this.attacking();
        }

        this.switchDirection();

        isCollisionOn = false;

        detection.checkCollisionWithTile(this);

        super.counter();
        super.updateAnimationImage();
        super.moving();
    }

    @Override
    public void attacking() {

    }

    private void collectItem(int itemIndex) {

        String itemName = gamePanel.itemManager
                .get(itemIndex)
                .getName()
                .toLowerCase(); // Lorem ipsum :]

        switch (itemName) {

            case "coin" -> {
                // coin
            }
            case "key" -> {
                // key
            }
            case "boots" -> {
                // boots
            }
            default -> {}
        }
    }

    private void damageMonster(int monsterIndex) {

        Monster monster = gamePanel.monsterManager
                .get(monsterIndex);

        if (monster == null) {
            return;
        }

        if (monster.isAlive()) {
            if (monster.isInvincible()) {
                return;
            }

            monster.setLife(monster.getLife() - damage);
            monster.setInvincible(true);
            monster.resetAction();

            gamePanel.renderUI.messages.add("1 damage!");
        }
        else {
            monster.setAlive(false);
            monster.setDead(true);

            experience += monster.getExperienceReward();

            gamePanel.renderUI.messages.add("Killed the " + monster.getName() + "!");
            gamePanel.renderUI.messages.add("Experience +" + monster.getExperienceReward() + "!");
        }
    }

    @Override
    public void setAction() {

    }

    @Override
    public void resetAction() {

    }

    @Override
    public void defending() {

        isInvincible = true;
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
