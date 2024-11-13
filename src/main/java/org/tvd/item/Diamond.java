package org.tvd.item;

import org.tvd.asset.FrameAsset;
import org.tvd.frame.GamePanel;

public class Diamond extends SuperItem {

    public Diamond(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "diamond";
        this.image = utool.getImage(path + name, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
    }
}
