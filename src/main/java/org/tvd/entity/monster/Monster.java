package org.tvd.entity.monster;

import org.tvd.entity.Direction;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.frame.GamePanel;

import java.awt.*;
import java.util.Random;

public abstract class Monster extends Entity implements EntityActions {

    protected int actionNumber = 0;

    protected boolean abilityAttack;
    protected boolean abilityFly;

    public Monster(GamePanel gamePanel) {
        super(gamePanel);
    }

    /**
     * Random hướng di chuyển của quái vật, cập nhật hoạt ảnh quái vật
     */
    @Override
    public void setAction() {

        // Cập nhật hình ảnh quái vật
        counter.drawCounter++;
        if (counter.drawCounter == 15) {
            counter.drawCounter = 0;
            imageChecker = !imageChecker;
        }

        // Random hướng di chuyển của quái vật
        counter.actionCounter++;
        if (counter.actionCounter == 150) {

            actionNumber = new Random().nextInt(100) + 1;

            if (actionNumber < 26) direction = Direction.UP;
            else if (actionNumber < 51) direction = Direction.DOWN;
            else if (actionNumber < 76) direction = Direction.LEFT;
            else direction = Direction.RIGHT;

            counter.actionCounter = 0;
        }
    }

    /**
     * Sửa lại action của qúai vật khi bị người chơi tấn công
     * + Làm cho quái vật ra xa khỏi người chơi khi bị gây sát thương
     * + Cập nhật hướng của quái vật quay về phía hướng người chơi
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

        // Sửa vị trí quái vật khi bị người chơi gây sát thương
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
    public void defending() {

        isInvincible = true;
    }

    @Override
    public void moving() {

        setAction();
    }

    @Override
    public void attacking() {

    }

    /**
     * Sử dụng thuật toán A star tìm kiếm đường đi tới vị trí của player
     */
    public void findPathToPlayer() {

    }

    @Override
    public void render(Graphics2D g2d) {

    }

    @Override
    public void update() {
        
    }
}
