package org.tvd.item;

import org.tvd.setter.ItemSetter;
import org.tvd.frame.GamePanel;


public class Tent extends SuperItem {

    public Tent(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "tent";

        ItemSetter.loadDefaultItemImage(this);
    }
}
