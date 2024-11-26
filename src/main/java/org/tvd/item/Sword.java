package org.tvd.item;

import org.tvd.frame.GamePanel;
import org.tvd.setter.ItemSetter;

public class Sword extends SuperItem {

    public Sword(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "sword";

        ItemSetter.loadDefaultItemImage(this);
    }
}
