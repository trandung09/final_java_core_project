package org.tvd.item;

import org.tvd.setter.ItemSetter;
import org.tvd.frame.GamePanel;

public class Mana extends SuperItem {

    public Mana(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "mana";
        ItemSetter.loadDefaultItemImage(this);
    }
}
