package org.tvd.frame;

import org.tvd.asset.FrameAsset;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {

        final GamePanel gamePanel = new GamePanel();

        this.add(gamePanel);

        this.addKeyListener(gamePanel.keyHandler);

        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(FrameAsset.SCREEN_WIDTH, FrameAsset.SCREEN_HEIGHT));
        this.setLocationRelativeTo(null);
    }
}
