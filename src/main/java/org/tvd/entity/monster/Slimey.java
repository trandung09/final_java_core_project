package org.tvd.entity.monster;

import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

public class Slimey extends Monster {

    public Slimey(GamePanel gamePanel) {
        super(gamePanel);

        init();
    }

    public void init() {
        this.name = "slimey";
        this.speed = 2;
        this.maxLife = 7;
        this.life = maxLife;
        this.abilityAttack = true;
        this.abilityFly = false;
    }

    @Override
    public void setAction() {

    }

    @Override
    public void render(Graphics2D g2d) {

    }
}
