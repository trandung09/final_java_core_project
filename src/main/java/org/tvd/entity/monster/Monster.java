package org.tvd.entity.monster;

import org.tvd.entity.Entity;
import org.tvd.frame.GamePanel;

public abstract class Monster extends Entity {

    protected boolean abilityAttack;
    protected boolean abilityFly;

    public Monster(GamePanel gamePanel) {
        super(gamePanel);
    }

    public abstract void setAction();
}
