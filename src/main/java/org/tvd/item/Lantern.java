package org.tvd.item;

import org.tvd.asset.ItemSetter;
import org.tvd.frame.GamePanel;

public class Lantern extends SuperItem {

    public Lantern(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "lantern";
        ItemSetter.loadDefaultItemImage(this);
    }
}
