package org.tvd.render;

import org.tvd.entity.player.PlayerStatus;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

public class RenderUI {

    private final GamePanel gamePanel;

    private Graphics2D graphics2D;

    public RenderUI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void render(Graphics2D graphics2D) {

        this.graphics2D = graphics2D;

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
