package org.tvd.item;

import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;

public class Key extends SuperItem {

    public Key(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "key";
        this.image = utool.getImage(path + name, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);
    }
}
