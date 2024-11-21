package org.tvd.item;

import org.tvd.asset.ItemSetter;
import org.tvd.frame.GamePanel;

public class Boots extends SuperItem {

    public Boots(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "boots";
        ItemSetter.loadDefaultItemImage(this);
    }
}
