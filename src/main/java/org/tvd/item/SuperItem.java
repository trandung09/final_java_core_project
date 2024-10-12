package org.tvd.item;

import lombok.Getter;
import lombok.Setter;
import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;
import org.tvd.utility.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
@Setter
public abstract class SuperItem {

    protected static final String path = "/item/";

    protected GamePanel gamePanel;

    protected BufferedImage image;
    protected UtilityTool utool;

    protected boolean collision;
    protected String name;

    protected int worldX;
    protected int worldY;

    protected Rectangle solidArea;
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;

    public SuperItem(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.collision = true;

        this.utool = new UtilityTool();

        this.solidArea = new Rectangle(0, 0, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);

        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
    }

    public void render(Graphics2D g2d) {

        int screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.screenY;

        if (worldX + FrameConfig.TILE_SIZE > gamePanel.player.getWorldX() - gamePanel.player.screenX &&
                worldX - FrameConfig.TILE_SIZE < gamePanel.player.getWorldX() + gamePanel.player.screenX &&
                worldY + FrameConfig.TILE_SIZE > gamePanel.player.getWorldY() - gamePanel.player.screenY &&
                worldY - FrameConfig.TILE_SIZE < gamePanel.player.getWorldY() + gamePanel.player.screenY
        ) {
            g2d.drawImage(image, screenX, screenY, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE, null);
        }
    }
}
