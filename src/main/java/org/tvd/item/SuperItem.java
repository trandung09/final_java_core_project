package org.tvd.item;

import lombok.Getter;
import lombok.Setter;
import org.tvd.entity.player.Player;
import org.tvd.setter.FrameAsset;
import org.tvd.frame.GamePanel;
import org.tvd.utility.UtilityTool;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@Getter
@Setter
public abstract class SuperItem {

    protected static final String path = "/item/";

    protected GamePanel gamePanel;

    protected BufferedImage defaultImage;
    protected int imageSize;
    protected UtilityTool utool;

    protected boolean collision;
    protected String name;

    protected int worldX;
    protected int worldY;

    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public SuperItem(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        init();
    }

    public void init() {

        this.collision = true;
        this.imageSize = FrameAsset.TILE_SIZE;
        this.utool = new UtilityTool();

        this.solidArea = new Rectangle(0, 0, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
    }

    public void render(Graphics2D g2d) {

        int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;

        if (worldX + FrameAsset.TILE_SIZE > gamePanel.player.getWorldX() - gamePanel.player.screenX &&
                worldX - FrameAsset.TILE_SIZE < gamePanel.player.getWorldX() + gamePanel.player.screenX &&
                worldY + FrameAsset.TILE_SIZE > gamePanel.player.getWorldY() - gamePanel.player.screenY &&
                worldY - FrameAsset.TILE_SIZE < gamePanel.player.getWorldY() + gamePanel.player.screenY
        ) {
            g2d.drawImage(defaultImage, screenX, screenY, defaultImage.getWidth(), defaultImage.getHeight(), null);
        }
    }
}
