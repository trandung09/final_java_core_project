package org.tvd.item;

import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;

public class Diamond extends SuperItem {

    public Diamond(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "diamond";
        this.image = utool.getImage(path + name, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);
    }
}
