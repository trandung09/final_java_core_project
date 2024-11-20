package org.tvd.entity.monster;

import org.tvd.asset.EntitySetter;
import org.tvd.entity.Direction;
import org.tvd.frame.GamePanel;

import java.awt.*;

public class SkeletonLord extends Monster {
    
    public SkeletonLord(GamePanel gamePanel) {

        super(gamePanel);

        init();
    }

    public void init() {
        this.name = "skeletonlord";
        this.isAlive = true;
        this.maxLife = 70;
        this.life = maxLife;
        this.speed = 1;
        this.experienceReward = 50;
        this.abilityAttack = true;
        this.abilityFly = false;
        this.direction = Direction.RIGHT;
        this.solidArea = new Rectangle(8, 8, 90, 90);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.attackArea = new Rectangle(0, 0, 90, 90);

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, "phase2_attack");;
    }

    @Override
    public void setAction() {

        super.setAction();

        super.detectPlayerInSight(400, 100);
    }

    @Override
    public void update() {

        super.update();
    }

    @Override
    public void render(Graphics2D g2d) {

        super.render(g2d);
    }
}
