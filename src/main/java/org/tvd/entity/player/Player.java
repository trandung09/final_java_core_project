package org.tvd.entity.player;

import lombok.Getter;
import lombok.Setter;

import org.tvd.setter.EntitySetter;
import org.tvd.setter.FrameAsset;
import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.entity.monster.Monster;
import org.tvd.entity.player.weapon.WeaponType;
import org.tvd.event.KeyHandler;
import org.tvd.event.KeyPressed;
import org.tvd.frame.GamePanel;
import org.tvd.frame.GameStatus;
import org.tvd.item.Tent;

import java.awt.AlphaComposite;
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
    private int maxEnergy;
    private int energy;
    private int manas;
    private int maxManas;

    private boolean isSpeedUp = false;

    private Tent tent;

    // Default index of selected weapon in weapon list
    // + index 0: axe
    // + index 1: pick
    // + index 2: sword
    private int weapon = 1;

    private List<WeaponType> weapons = new ArrayList<>();

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.init();

        this.name = "boy";

        this.pressed = keyHandler.pressed;
        this.input = keyHandler;

        this.tent = new Tent(gamePanel);

        this.weapons.add(WeaponType.AXE);
        this.weapons.add(WeaponType.PICK);
        this.weapons.add(WeaponType.SWORD);

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, weapons.get(weapon).name().toLowerCase());
    }

    public void init() {

        this.speed = 5;
        this.maxLife = 50;
        this.life = maxLife;
        this.damage = 2;
        this.level = 1;
        this.nextLevelExp = 10;
        this.experience = 0; 
        this.isAlive = true;
        this.maxEnergy = 100;
        this.energy = maxEnergy;
        this.worldX = FrameAsset.TILE_SIZE * 8;
        this.worldY = FrameAsset.TILE_SIZE * 7;
    }

    @Override
    public void update() {

        super.update();

        if (!isAlive) {
            gamePanel.gameStatus = GameStatus.GAME_OVER;
        }

        if (experience >= nextLevelExp) {
            levelUp();
        }

        if (isAttacking) {
            attacking();
        }

        isCollisionOn = false;

        detection.checkCollisionWithTile(this);
        detection.checkCollisionWithOtherEntities(this, gamePanel.monsterManager);
        int itemIndex = detection.checkCollisionWithItems(this, gamePanel.itemManager);

        collectItem(itemIndex);

        switchDirection();

        counter();
        moving();
    }

    @Override
    public void moving() {

        if (!gamePanel.keyHandler.pressed.isMovePressed()) {
            return;
        }

        super.moving();
    }

    public void levelUp() {

        level++;

        nextLevelExp += level * 5;

        gamePanel.sound.playSE(4);

        if (damage < 4) {
            damage++;
        }

        gamePanel.renderUI.currentDialogueMessage = "You are level " + level + " now!\n"
                + "You feel stronger!";

        gamePanel.gameStatus = GameStatus.GAME_DIALOGUE;
    }

    @Override
    public void counter() {

        super.counter();

        if (isSpeedUp) {
            if (counter.speedUpCounter++ > 1800) {
                isSpeedUp = false;
                speed -= 2;
                counter.speedUpCounter = 0;
            }
        }

        if (!gamePanel.keyHandler.pressed.isMovePressed()) {
            return;
        }

        if (counter.drawCounter++ > 10) {
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

        int monsterIndex = detection.checkCollisionWithOtherEntities(this, gamePanel.monsterManager);

        damageMonster(monsterIndex);

        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
    }

    private void collectItem(int itemIndex) {

        if (itemIndex < 0 || itemIndex >= weapons.size()) {
            return;
        }

        String itemName = gamePanel.itemManager
                .get(itemIndex)
                .getName()
                .toLowerCase();

        switch (itemName) {

            case "coin" -> {
                // coin
            }
            case "key" -> {
                // key
            }
            case "boots" -> {
                isSpeedUp = true;
                speed += 2;

                gamePanel.renderUI.addMessage("Speed up!");
                gamePanel.sound.playSE(5);
                gamePanel.itemManager.set(itemIndex, null);
            }
            default -> isSleeping = false;
        }
    }

    private void damageMonster(int monsterIndex) {

        if (monsterIndex < 0 || monsterIndex >= gamePanel.monsterManager.size()) {
            return;
        }

        Monster monster = (Monster) gamePanel.monsterManager
                .get(monsterIndex);

        if (monster == null) {
            return;
        }

        if (monster.getLife() > 0) { // monster.isAlive()

            if (monster.isInvincible()) {
                return;
            }

            gamePanel.sound.playSE(6);

            monster.setLife(monster.getLife() - damage);
            monster.setInvincible(true);
            monster.setHpBarOn(true);
            monster.resetAction();

            gamePanel.renderUI.addMessage("1 damage!");

            if (monster.getLife() <= 0) {
                experience += monster.getExperienceReward();

                gamePanel.renderUI.addMessage("Killed the " + monster.getName() + "!");
                gamePanel.renderUI.addMessage("Experience +" + monster.getExperienceReward() + "!");
            }
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

        if (isSleeping) {
            g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0f));
        }
        if (isInvincible) {
            g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f));
        }
        g2d.drawImage(image, tempX, tempY, image.getWidth(), image.getHeight(), null);
        g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f));

        if (isSleeping) {
            BufferedImage tentImage = tent.getDefaultImage();
            g2d.drawImage(tentImage, tempX - FrameAsset.TILE_SIZE / 2, tempY - FrameAsset.TILE_SIZE / 2, tentImage.getWidth(), tentImage.getHeight(), null);
        }
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
