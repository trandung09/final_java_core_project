package org.tvd.render;

import org.tvd.config.FrameConfig;
import org.tvd.frame.GamePanel;
import org.tvd.frame.Menu;
import org.tvd.frame.StageOption;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RenderUI {

    private final GamePanel gamePanel;

    private Graphics2D g2d;
    private Font font;

    // List of message that can be display on the screen
    public ArrayList<String> message = new ArrayList<>();

    // Message counter arraylist
    public ArrayList<Integer> mcounter = new ArrayList<>();

    public RenderUI(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");

            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (IOException | FontFormatException e) {
            System.out.println("Error loading font " + e.getMessage());
        }
    }

    public void render(Graphics2D g2d) {

        this.g2d = g2d;

        this.g2d.setFont(font.deriveFont(Font.BOLD, 54f));

        switch (gamePanel.gameStatus) {
            case GAME_MENU -> renderGameMenuScreen();
            case GAME_RUNNING -> renderPlayerInfo();
            case GAME_PAUSE -> renderGamePauseScreen();
            case GAME_OVER -> renderGameOverScreen();
            case GAME_WIN -> renderGameWinScreen();
            case CHARACTER_ST -> renderPlayerStageScreen();
            case GAME_DIALOGUE -> renderDialogueScreen();
        }
    }

    private void renderGameWinScreen() {

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 65f));

        String text = "Congratulations";

        int screenX = getCenterPositionForText(text);
        int screenY = FrameConfig.TILE_SIZE * 2 + FrameConfig.TILE_SIZE / 2;

        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50f));

        text = "on winning";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameConfig.TILE_SIZE;
        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

        text = "Menu";
        screenX = getCenterPositionForText(text);
        screenY += FrameConfig.TILE_SIZE * 4;

        if (StageOption.WinStage == Menu.NEW_GAME) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }
        g2d.drawString(text, screenX, screenY);

        text = "Quit";
        screenX = getCenterPositionForText(text);
        screenY += FrameConfig.TILE_SIZE;

        if (StageOption.WinStage == Menu.QUIT) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }
        g2d.drawString(text, screenX, screenY);
    }

    /**
     * Draw winning game screen
     */
    private void renderGameOverScreen() {
        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 85f));

        String text = "GAME OVER";
        int screenX = getCenterPositionForText(text);
        int screenY = FrameConfig.TILE_SIZE * 4 - (FrameConfig.TILE_SIZE * 3) / 4;
        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

        text = "Retry";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameConfig.TILE_SIZE * 4;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.OverStage == Menu.RETRY) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }

        text = "Quit";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameConfig.TILE_SIZE;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.OverStage == Menu.QUIT) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }
    }

    /**
     * Draw a notification sub window with height and width
     * starting from x, y position
     */
    private void renderSubWindow(int x, int y, int width, int height) {

        Color color = new Color(0, 0, 0, 210);
        g2d.setColor(color);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        g2d.setColor(color);

        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x, y, width, height, 35, 35);
    }

    /**
     * Draw notices on the sub window
     */
    private void renderDialogueScreen() {

        renderSubWindow(0, 0, 0, 0);
    }

    /**
     * Draw game menu when game starts
     */
    public void renderGameMenuScreen() {

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 76));
        g2d.setColor(Color.GRAY);

        String text = "PIXEL HUNTER";

        // Draw string "PIXEL HUNTER in panel
        int screenX = getCenterPositionForText(text);
        int screenY = FrameConfig.TILE_SIZE * 2;

        g2d.drawString(text, screenX - 3, screenY - 3);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, screenX, screenY);

        // Draw player image in panel
        int counter = gamePanel.player.getCounter().drawCounter++;
        if (counter > 10) {
            gamePanel.player.getCounter().drawCounter = 0;
            gamePanel.player.setImageChecker(!gamePanel.player.isImageChecker());
        }
        boolean playerImage = gamePanel.player.isImageChecker();
        screenX = FrameConfig.SCREEN_WIDTH / 2 - (FrameConfig.TILE_SIZE * 3) / 4;
        screenY = screenY + FrameConfig.TILE_SIZE;
        g2d.drawImage(playerImage ? gamePanel.player.getDefaultImages()[6] : gamePanel.player.getDefaultImages()[7], screenX, screenY, 72, 72, null);
        g2d.drawLine(FrameConfig.SCREEN_WIDTH / 6, screenY + 72, (FrameConfig.SCREEN_WIDTH * 5 ) / 6, screenY + 72);

        // Draw menu choose (new game, quit...)
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 45f));

        text = "NEW GAME";
        screenX = getCenterPositionForText(text);
        screenY = screenY + (FrameConfig.TILE_SIZE * 7) / 2;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.MenuStage == Menu.NEW_GAME) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }

        text = "ABOUT";
        screenX = getCenterPositionForText(text);
        screenY = screenY + (FrameConfig.TILE_SIZE * 4) / 3;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.MenuStage == Menu.ABOUT) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }

        text = "QUIT";
        screenX = getCenterPositionForText(text);
        screenY = screenY + (FrameConfig.TILE_SIZE * 4) / 3;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.MenuStage == Menu.QUIT) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }
    }

    /**
     * Draw game pause screen
     */
    public void renderGamePauseScreen() {
        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 85f));

        String text = "GAME PAUSE";
        int screenX = getCenterPositionForText(text);
        int screenY = FrameConfig.TILE_SIZE * 4 - (FrameConfig.TILE_SIZE * 3) / 4;
        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

        text = "Continue";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameConfig.TILE_SIZE * 4;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.PauseStage == Menu.CONTINUE) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }

        text = "Quit";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameConfig.TILE_SIZE;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.PauseStage == Menu.QUIT) {
            g2d.drawString(">", screenX - FrameConfig.TILE_SIZE, screenY);
        }
    }

    /**
     * Draw all player information: life, enegry, time....
     */
    private void renderPlayerInfo() {


    }


    public void renderPlayerStageScreen() {

    }

    /**
     * Get the string length and figure out where to place the string
     * so that the string holds the screen vertically.
     *
     * @return center position of text in view
     */
    public int getCenterPositionForText(String text) {

        int length = (int) (g2d.getFontMetrics().getStringBounds(text, g2d).getWidth() / 2);
        return FrameConfig.SCREEN_WIDTH / 2 - length;
    }
}
