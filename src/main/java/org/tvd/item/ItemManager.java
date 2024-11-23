package org.tvd.item;

import lombok.Getter;
import lombok.Setter;
import org.tvd.setter.AssetSetter;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

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
        AssetSetter.loadAllItem(gamePanel, this);
    }

    public void render(Graphics2D g2d) {

        Iterator<SuperItem> it = iterator();
        while (it.hasNext()) {
            SuperItem item = it.next();
            if (item == null) {
                it.remove();
            }
        }

        for (SuperItem item : this) {
            item.render(g2d);
        }
    }
}
