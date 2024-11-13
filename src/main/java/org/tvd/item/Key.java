package org.tvd.item;

import org.tvd.asset.FrameAsset;
import org.tvd.frame.GamePanel;

public class Key extends SuperItem {

    public Key(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "key";
        this.defaultImage = utool.getImage(path + name, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
    }
}
