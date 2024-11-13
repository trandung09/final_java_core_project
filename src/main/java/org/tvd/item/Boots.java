package org.tvd.item;

import org.tvd.asset.FrameAsset;
import org.tvd.frame.GamePanel;

public class Boots extends SuperItem {

    public Boots(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "boots";
        this.defaultImage = utool.getImage(path + name, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
    }
}
