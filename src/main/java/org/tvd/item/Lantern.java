package org.tvd.item;

import org.tvd.asset.FrameAsset;
import org.tvd.frame.GamePanel;

public class Lantern extends SuperItem {

    public Lantern(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "lantern";
        this.defaultImage = utool.getImage(path + name, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
    }
}
