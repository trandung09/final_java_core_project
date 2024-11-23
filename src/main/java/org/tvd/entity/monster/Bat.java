package org.tvd.entity.monster;

import org.tvd.setter.EntitySetter;
import org.tvd.frame.GamePanel;

public class Bat extends Monster {

    public Bat(GamePanel gamePanel) {

        super(gamePanel);
        
        init();
    }

    public void init() {

        super.init();

        this.name = "bat";
        this.speed = 1;
        this.maxLife = 5;
        this.life = maxLife;
        this.abilityFly = true;

        EntitySetter.loadDefaultEntityImage(this);
    }

    @Override
    public void update() {

        setAction();

        isCollisionOn = false;

        moving();
        counter();
    }
}
