package org.tvd.entity.monster;

import org.tvd.asset.EntitySetter;
import org.tvd.entity.Direction;
import org.tvd.frame.GamePanel;

public class Bat extends Monster {

    public Bat(GamePanel gamePanel) {

        super(gamePanel);
        
        init();
    }

    private void init() {

        this.name = "bat";
        this.speed = 1;
        this.direction = Direction.RIGHT;
        this.maxLife = 5;
        this.life = maxLife;
        this.abilityAttack = false;
        this.abilityFly = true;

        EntitySetter.loadDefaultEntityImage(this);
    }

    @Override
    public void update() {

        super.update();

        isCollisionOn = false;

        switch (direction) {
            case UP -> worldY -= speed;
            case DOWN -> worldY += speed;
            case LEFT -> worldX -= speed;
            case RIGHT -> worldX += speed;
        }
    }
}
