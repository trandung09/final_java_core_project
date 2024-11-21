package org.tvd.item;

import org.tvd.asset.ItemSetter;
import org.tvd.frame.GamePanel;

public class Diamond extends SuperItem {

    public Diamond(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "diamond";
        ItemSetter.loadDefaultItemImage(this);
    }
}
