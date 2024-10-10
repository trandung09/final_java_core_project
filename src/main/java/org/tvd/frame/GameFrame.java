package org.tvd.frame;

import org.tvd.config.FrameConfig;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {

        final GamePanel gamePanel = new GamePanel();

        this.add(gamePanel);

        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(FrameConfig.SCREEN_WIDTH, FrameConfig.SCREEN_HEIGHT));
        this.setLocationRelativeTo(null);
    }
}
