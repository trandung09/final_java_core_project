package org.tvd.item;

import org.tvd.asset.FrameAsset;
import org.tvd.asset.ItemSetter;
import org.tvd.frame.GamePanel;

import java.awt.Rectangle;

public class Tent extends SuperItem {

    public Tent(GamePanel gamePanel) {

        super(gamePanel);

        this.name = "tent";
        this.imageSize = FrameAsset.TILE_SIZE * 2;
        this.solidArea = new Rectangle(0, 0, imageSize, imageSize);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;

        ItemSetter.loadDefaultItemImage(this);
    }
}
