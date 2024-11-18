package org.tvd.entity.monster;

import lombok.Getter;
import lombok.Setter;

import org.tvd.asset.AssetSetter;
import org.tvd.entity.Entity;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.util.ArrayList;

@Getter
@Setter
public class MonsterManager extends ArrayList<Entity> {

    private final GamePanel gamePanel;
    private final String[] monsterInMap = new String[10];

    public MonsterManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        init();
    }

    public void init() {
        AssetSetter.loadAllMonster(gamePanel, this);
    }

    public void update() {

       removeIf(o ->  o == null || o.getLife() <=0 || !o.isAlive());

       for (Entity monster : this) {
           monster.update();
       }
    }

    public void render(Graphics2D g2d) {

        for (Entity monster : this) {
            monster.render(g2d);
        }
    }
}
