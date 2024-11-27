package org.tvd.entity.player;

import lombok.Getter;
import lombok.Setter;

import org.tvd.setter.EntitySetter;
import org.tvd.setter.FrameAsset;
import org.tvd.enums.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.entity.monster.Monster;
import org.tvd.enums.WeaponType;
import org.tvd.event.KeyHandler;
import org.tvd.event.KeyPressed;
import org.tvd.frame.GamePanel;
import org.tvd.enums.GameStatus;
import org.tvd.item.Tent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private int nextLevelExp = 15;
    private int maxEnergy;
    private int energy;
    private int manas = 1;
    private int key = 1;
    private int coin;
    private int maxManas;

    private boolean isSpeedUp = false;

    private Tent tent;

    private int currentSelectedItem = 0;

    private boolean isUsedKey = false;

    // Default index of selected weapon in weapon list
    // + index 0: axe
    // + index 1: pick
    // + index 2: sword
    private int weapon;

    private List<WeaponType> weapons = new ArrayList<>();

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.name = "boy";

        this.pressed = keyHandler.pressed;
        this.input = keyHandler;

        this.tent = new Tent(gamePanel);

        this.init();

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, weapons.get(weapon).name().toLowerCase());
    }

    public void init() {

        if (!this.weapons.isEmpty()) {
            this.weapons.clear();
        }
        this.speed = 5;
        this.maxLife = 70;
        this.life = maxLife;
        this.level = 1;
        this.key = 0;
        this.manas = 2;
        this.weapons.add(WeaponType.AXE);
        this.weapon = 0;
        this.isUsedKey = false;
        this.damage = weapons.get(weapon).getDamage();
        this.nextLevelExp = 10;
        this.experience = 0; 
        this.isAlive = true;
        this.maxEnergy = 120;
        this.energy = maxEnergy;
    }

    @Override
    public void update() {

        super.update();

        if (!isAlive) {
            gamePanel.sound.playSE(2);
            gamePanel.gameStatus = GameStatus.GAME_OVER;
        }

        if (isSleeping) {
            energyRecovery();
        }

        if (experience >= nextLevelExp) {
            levelUp();
        }

        if (energy <= 0) {
            energy = 1;
            gamePanel.renderUI.addMessage("Find a place to rest!");
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

        nextLevelExp += level * 20;

        gamePanel.sound.playSE(4);

        energy = maxEnergy;

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
                gamePanel.renderUI.addMessage("Speed boost time is over!");
            }
        }

        if (isSleeping) {
            counter.energyRecoveryCounter++;
            if (counter.energyRecoveryCounter > 60) {
                counter.energyRecoveryCounter = 0;
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

        if (itemIndex < 0 || itemIndex >= gamePanel.itemManager.size()) {
            return;
        }

        String itemName = gamePanel.itemManager
                .get(itemIndex)
                .getName()
                .toLowerCase();

        switch (itemName) {

            case "coin" -> {
                coin++;
                gamePanel.sound.playSE(7);
                gamePanel.renderUI.addMessage("Coin +1!");
            }
            case "key" -> {
                key++;
                gamePanel.sound.playSE(8);
                gamePanel.renderUI.addMessage("Key +1!");
            }
            case "mana" -> {
                manas++;
                gamePanel.sound.playSE(5);
                gamePanel.renderUI.addMessage("Mana +1!");
            }
            case "stair" -> {
                if (isUsedKey && gamePanel.monsterManager.isEmpty()) {
                    isUsedKey = false;
                    GamePanel.isLevelComplete = true;
                    gamePanel.renderUI.addMessage("Level complete +1!");
                    gamePanel.itemManager.set(itemIndex, null);
                }
            }
            case "sword" -> {
                gamePanel.sound.playSE(9);
                if (!weapons.contains(WeaponType.SWORD)) {
                    this.weapons.add(WeaponType.SWORD);
                    gamePanel.renderUI.addMessage("You have pick up the Sword!");
                }
            }
            case "pick" -> {
                gamePanel.sound.playSE(9);
                if (!weapons.contains(WeaponType.PICK)) {
                    this.weapons.add(WeaponType.PICK);
                    gamePanel.renderUI.addMessage("You have pick up the Pick!");
                }
            }
            case "lantern" -> gamePanel.sound.playSE(9);
            case "boots" -> {
                isSpeedUp = true;
                speed += 2;

                gamePanel.renderUI.addMessage("Speed up!");
                gamePanel.sound.playSE(5);
            }
            default -> isSleeping = false;
        }

        if (!"stair".equals(itemName)) {
            gamePanel.itemManager.set(itemIndex, null);
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

        if (energy <= 1) {
            return;
        }

        if (monster.getLife() > 0) { // monster.isAlive()

            if (monster.isInvincible()) {
                return;
            }

            energy -= damage / 2;

            gamePanel.sound.playSE(6);

            monster.setLife(monster.getLife() - damage);
            monster.setInvincible(true);
            monster.setHpBarOn(true);
            monster.resetAction();

            gamePanel.renderUI.addMessage(damage + " damage!");

            if (monster.getLife() <= 0) {
                experience += monster.getExperienceReward();

                gamePanel.renderUI.addMessage("Killed the " + monster.getName() + "!");
                gamePanel.renderUI.addMessage("Experience +" + monster.getExperienceReward() + "!");
            }
        }
    }

    public void switchWeapon() {

        pressed.back_slash = false;

        weapon++;

        if (weapon == weapons.size()) weapon = 0;

        damage = weapons.get(weapon).getDamage();
        String weaponName = weapons.get(weapon).name();
        gamePanel.renderUI.addMessage("Change weapon to " + weaponName.toLowerCase(Locale.ROOT));
        EntitySetter.loadAttackEntityImage(this, weaponName.toLowerCase());
    }

    public void useCoin() {

        if (coin <= 0) return;
        coin--;
    }

    public void useKey() {

        if (key <= 0) return;
        if (isUsedKey) return;

        isUsedKey = true;
        key--;
    }

    public void energyRecovery() {

        if (energy >= maxEnergy) return;

        if (counter.energyRecoveryCounter == 30) {
            energy += 2;
        }
    }

    public void healing() {

        if (manas <= 0) return;
        if (life == maxLife) return;

        life += 20;
        if (life > maxLife) life = maxLife;

        manas--;
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

        if (life <= 0) {
            dyingAnimation(g2d);
        }

        g2d.drawImage(image, tempX, tempY, image.getWidth(), image.getHeight(), null);
        g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f));

        if (isSleeping) {
            BufferedImage tentImage = tent.getDefaultImage();
            g2d.setColor(Color.WHITE);
            g2d.drawString("z z Z Z", tempX + FrameAsset.TILE_SIZE * 3 / 4, tempY);
            g2d.drawImage(tentImage, tempX, tempY, tentImage.getWidth(), tentImage.getHeight(), null);
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
