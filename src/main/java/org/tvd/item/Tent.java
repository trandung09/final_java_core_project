package org.tvd.item;

import org.tvd.asset.FrameAsset;
import org.tvd.frame.GamePanel;

public class Tent extends SuperItem {

    public Tent(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "tent";
        this.defaultImage = utool.getImage(path + name, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
    }
}
