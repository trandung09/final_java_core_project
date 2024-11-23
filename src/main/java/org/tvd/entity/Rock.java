package org.tvd.entity;

import org.tvd.frame.GamePanel;
import org.tvd.setter.EntitySetter;

public class Rock extends Projectile {

    public Rock(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "rock";
        this.speed = 2;
        this.damage = 1;
        this.maxLife = 120;
        this.life = maxLife;

        EntitySetter.loadDefaultEntityImage(this);
    }
}
