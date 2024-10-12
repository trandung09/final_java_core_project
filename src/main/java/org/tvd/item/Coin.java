package org.tvd.item;

import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;

public class Coin extends SuperItem {

    public Coin(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "coin";
        this.image = utool.getImage(path + name, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);
    }
}
