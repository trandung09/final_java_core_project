package org.tvd.frame;

import org.tvd.entity.Entity;
import org.tvd.entity.Projectile;
import org.tvd.setter.FrameAsset;
import org.tvd.entity.monster.MonsterManager;
import org.tvd.entity.player.Player;
import org.tvd.environment.EnvironmentManager;
import org.tvd.event.KeyHandler;
import org.tvd.item.ItemManager;
import org.tvd.map.TileManager;
import org.tvd.render.RenderUI;
import org.tvd.sound.Sound;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GamePanel extends JPanel implements Runnable {
    
    // Fps settings
    public static final int FPS = 60;

    // Main game thread
    private final Thread gameThread = new Thread(this);

    public GameStatus gameStatus = GameStatus.GAME_MENU;

    // Object settings
    public MonsterManager monsterManager = new MonsterManager(this);
    public ItemManager itemManager = new ItemManager(this);

    public final TileManager tileManager = new TileManager(this);
    public final RenderUI renderUI = new RenderUI(this);
    public final Sound sound = new Sound();
    public final KeyHandler keyHandler = new KeyHandler(this);

    public final EnvironmentManager eManager = new EnvironmentManager(this);

    public final Player player  = new Player(this, keyHandler);

    // Game level
    public static int gameLevel;
    public static int maxGameLevel;

    public static double gamePlayTime;

    // Thread pool
    public final ExecutorService executor = Executors.newFixedThreadPool(5);

    public GamePanel() {

        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(FrameAsset.SCREEN_WIDTH, FrameAsset.SCREEN_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow(true);

        this.addKeyListener(keyHandler);

        init();

        gameThread.start();
    }

    public void init() {

        sound.play(0);

        gameLevel = 0;
        maxGameLevel = 2;
        gamePlayTime = 0;

        renderUI.messages.clear();
        itemManager = new ItemManager(this);
        monsterManager = new MonsterManager(this);
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

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (gameStatus != GameStatus.GAME_MENU) {

            tileManager.render(g2d);

            monsterManager.renderMonsterCannotFly(g2d);

            player.render(g2d);

            monsterManager.renderProjectileOfMonster(g2d);
            monsterManager.renderMonsterCanFly(g2d);

            itemManager.render(g2d);
            eManager.render(g2d);
        }

        renderUI.render(g2d);

        g.dispose();
    }

    public void update() {

        if (gameStatus == GameStatus.GAME_RUNNING) {

            gamePlayTime++;

            executor.submit(player::update);
            executor.submit(monsterManager::update);

            if (monsterManager.isEmpty()) {

                gameLevelUp();
            }
        }
    }

    public void gameLevelUp() {

        gameLevel++;

        if (gameLevel > maxGameLevel) {

            gameStatus = GameStatus.GAME_WIN;
        }
        else {
            renderUI.currentDialogueMessage = gameLevel == maxGameLevel ? "Game level up" : "Game level down";
            gameStatus = GameStatus.GAME_DIALOGUE;
        }
    }

}
