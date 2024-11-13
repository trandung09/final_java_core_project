package org.tvd.item;

import org.tvd.asset.FrameAsset;
import org.tvd.frame.GamePanel;

public class Coin extends SuperItem {

    public Coin(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "coin";
        this.defaultImage = utool.getImage(path + name, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
    }
}
