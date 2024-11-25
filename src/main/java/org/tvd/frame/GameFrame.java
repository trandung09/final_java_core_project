package org.tvd.frame;

import org.tvd.setter.FrameAsset;
import org.tvd.utility.UtilityTool;

import java.awt.*;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {

        final GamePanel gamePanel = new GamePanel();

        this.add(gamePanel);

        this.addKeyListener(gamePanel.keyHandler);

        this.setTitle("Pixel hunter");
        this.setIconImage(new UtilityTool().getImage("/item/lantern", 30, 30));
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(FrameAsset.SCREEN_WIDTH, FrameAsset.SCREEN_HEIGHT));
        this.setLocationRelativeTo(null);
    }
}
