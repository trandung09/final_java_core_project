package org.tvd.item;

import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;

public class Tent extends SuperItem {

    public Tent(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "tent";
        this.image = utool.getImage(path + name, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);
    }
}
