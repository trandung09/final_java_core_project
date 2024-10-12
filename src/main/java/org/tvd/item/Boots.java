package org.tvd.item;

import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;

public class Boots extends SuperItem {

    public Boots(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "boots";
        this.image = utool.getImage(path + name, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);
    }
}
