package org.tvd.frame;

import org.tvd.config.FrameConfig;
import org.tvd.entity.player.Player;
import org.tvd.event.KeyHandler;
import org.tvd.render.RenderUI;
import org.tvd.sound.Sound;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Fps settings
    private final int FPS = 60;

    // Main game thread
    private final Thread gameThread = new Thread(this);

    // Object settings
    public final Player player  = new Player(this);
    public final Sound sound = new Sound();
    public final RenderUI renderUI = new RenderUI(this);
    public final KeyHandler keyHandler = new KeyHandler(this);

    public GamePanel() {

        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(FrameConfig.SCREEN_WIDTH, FrameConfig.SCREEN_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow(true);

        this.addKeyListener(keyHandler);

        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS; // nano seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread.isAlive()) {

            update();
            repaint(); // call back: paintComponent(Graphics g)

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000; // milis second

                if (remainingTime < 0) remainingTime = 0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                System.err.println("Game thread interrupted");
            }
        }
    }

    public void update() {

        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        player.render(g2d);

        g.dispose();
    }
}
