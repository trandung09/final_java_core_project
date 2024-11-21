package org.tvd.item;

import org.tvd.asset.ItemSetter;
import org.tvd.frame.GamePanel;

public class Coin extends SuperItem {

    public Coin(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "coin";
        ItemSetter.loadDefaultItemImage(this);
    }
}
