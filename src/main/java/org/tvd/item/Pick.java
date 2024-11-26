package org.tvd.item;

import org.tvd.frame.GamePanel;
import org.tvd.setter.ItemSetter;

public class Pick extends SuperItem
{
    public Pick(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "pick";

        ItemSetter.loadDefaultItemImage(this);
    }
}
