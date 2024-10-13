package org.tvd.item;

import lombok.Getter;
import lombok.Setter;
import org.tvd.config.AssetSetter;
import org.tvd.frame.GamePanel;

import java.awt.*;
import java.util.ArrayList;

@Setter
@Getter
public class ItemManager extends ArrayList<SuperItem> {

    private final GamePanel gamePanel;
    private final String[] itemInMaps = new String[10];

    public ItemManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        init();
    }

    public void init() {
        AssetSetter.loadAllItem(gamePanel.level, this);
    }

    public void render(Graphics2D g2d) {

        for (SuperItem item : this) {
            if (item == null) continue;
            item.render(g2d);
        }
    }
}