package org.tvd.item;

import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;

public class Lantern extends SuperItem {

    public Lantern(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "lantern";
        this.image = utool.getImage(path + name, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);
    }
}
