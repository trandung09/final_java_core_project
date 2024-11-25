package org.tvd.entity.monster;

import org.tvd.setter.EntitySetter;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

public class Orc extends Monster {

    public Orc(GamePanel gamePanel) {
        super(gamePanel);

        init();
    }

    public void init() {

        super.init();

        this.name = "orc";
        this.maxLife = 15;
        this.life = maxLife;
        this.speed = 1;
        this.damage = 2;
        this.experienceReward = 10;

        EntitySetter.loadDefaultEntityImage(this);
        EntitySetter.loadAttackEntityImage(this, "attack");
    }

    public void update() {

        super.detectPlayerInSight(200, 80);
        super.update();
    }

    @Override
    public void render(Graphics2D g2d) {

        super.render(g2d);
    }
}
