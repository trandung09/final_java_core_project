package org.tvd.entity.monster;

import org.tvd.asset.EntitySetter;
import org.tvd.entity.Direction;
import org.tvd.frame.GamePanel;

import java.awt.*;

public class Orc extends Monster {

    public Orc(GamePanel gamePanel) {
        super(gamePanel);

        init();
    }

    public void init() {

        this.name = "orc";
        this.isAlive = true;
        this.maxLife = 15;
        this.life = maxLife;
        this.speed = 1;
        this.experienceReward = 10;
        this.abilityAttack = true;
        this.abilityFly = false;
        this.direction = Direction.RIGHT;
        this.solidArea = new Rectangle(8, 8, 40, 40);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.attackArea = new Rectangle(0, 0, 45, 45);

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, "attack");
    }

    public void update() {

        super.update();
    }

    @Override
    public void setAction() {

        super.setAction();

        super.detectPlayerInSight(200, 80);
    }

    @Override
    public void render(Graphics2D g2d) {

        super.render(g2d);
    }
}
