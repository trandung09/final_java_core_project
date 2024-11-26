package org.tvd.item;

import org.tvd.frame.GamePanel;
import org.tvd.setter.ItemSetter;

public class Stair extends SuperItem{

    public Stair(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "stair";
        ItemSetter.loadDefaultItemImage(this);
    }
}
