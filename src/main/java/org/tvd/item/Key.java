package org.tvd.item;

import org.tvd.setter.ItemSetter;
import org.tvd.frame.GamePanel;

public class Key extends SuperItem {

    public Key(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "key";

        ItemSetter.loadDefaultItemImage(this);
    }
}
