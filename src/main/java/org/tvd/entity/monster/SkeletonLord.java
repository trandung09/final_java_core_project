package org.tvd.entity.monster;

import org.tvd.setter.EntitySetter;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SkeletonLord extends Monster {
    
    public SkeletonLord(GamePanel gamePanel) {

        super(gamePanel);

        init();
    }

    @Override
    public void init() {

        super.init();

        this.name = "skeletonlord";
        this.maxLife = 80;
        this.life = maxLife;
        this.speed = 1;
        this.damage = 10;
        this.experienceReward = 25;
        this.solidArea = new Rectangle(8, 8, 90, 90);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.attackArea = new Rectangle(0, 0, 90, 90);

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, "phase2_attack");;
    }

    @Override
    public void update() {

        super.detectPlayerInSight(400, 100);
        super.update();
    }

    @Override
    public void render(Graphics2D g2d) {

        super.render(g2d);
    }
}
