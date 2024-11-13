package org.tvd.entity.monster;

import lombok.Getter;
import lombok.Setter;

import org.tvd.asset.AssetSetter;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.util.ArrayList;

@Getter
@Setter
public class MonsterManager extends ArrayList<Monster> {

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

        for (Monster monster : this) {
            if (monster == null) continue;

            monster.update();
        }
    }

    public void render(Graphics2D g2d) {

        for (Monster monster : this) {
            if (monster == null) continue;

            monster.render(g2d);
        }
    }
}
