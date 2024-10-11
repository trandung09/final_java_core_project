package org.tvd.render;

import org.tvd.entity.player.PlayerStatus;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

public class RenderUI {

    private final GamePanel gamePanel;

    private Graphics2D g2d;

    public RenderUI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void render(Graphics2D g2d) {

        this.g2d = g2d;

        renderDialogueScreen();
    }

    private void renderSubWindow(int x, int y, int width, int height) {

    }

    private void renderDialogueScreen() {

    }

    private void renderPlayerLife() {

    }

    private void renderPlayerOptionScreen(PlayerStatus option) {

    }
}
