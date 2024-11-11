package org.tvd.entity.monster;

import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

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
        this.speed = 4;
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
