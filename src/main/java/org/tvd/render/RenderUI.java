package org.tvd.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.tvd.setter.FrameAsset;
import org.tvd.frame.GamePanel;
import org.tvd.frame.Menu;
import org.tvd.frame.StageOption;
import org.tvd.utility.StaticImage;
import org.tvd.utility.UtilityTool;

public class RenderUI {

    private final GamePanel gamePanel;

    private UtilityTool utool;
    private Graphics2D g2d;
    private Font font;

    // List of message that can be display on the screen
    public ArrayList<String> messages = new ArrayList<>();

    // Message counter arraylist
    public ArrayList<Integer> messageCounter = new ArrayList<>();

    // Message will be displayed on the screen dialog box.
    public String currentDialogueMessage;

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

        this.utool = new UtilityTool();

        this.g2d.setFont(font.deriveFont(Font.BOLD, 54f));

        switch (gamePanel.gameStatus) {
            case GAME_MENU -> renderGameMenuScreen();
            case GAME_RUNNING -> {
                renderGameRunningScreen();
                renderPlayerInfoScreen();
            }
            case GAME_PAUSE -> renderGamePauseScreen();
            case GAME_OVER -> renderGameOverScreen();
            case GAME_WIN -> renderGameWinScreen();
            case CHARACTER_ST -> renderPlayerStageScreen();
            case GAME_DIALOGUE -> renderDialogueScreen();
        }
    }

    public void addMessage(String message) {

        messages.add(message);
        messageCounter.add(0);
    }

    /**
     * Draw all messages on screen
     */
    private void renderGameRunningScreen() {

        int screeX = FrameAsset.TILE_SIZE;
        int screeY = FrameAsset.TILE_SIZE * 5;

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 25f));

        if (messages.isEmpty()) {
            return;
        }

        for (int i = 0; i < messages.size() ; i++) {

            if (messages.get(i) == null) {
                continue;
            }

            String message = messages.get(i);
            int currentMessageCounter = messageCounter.get(i);

            g2d.drawString(message, screeX, screeY);
            screeY += 50;

            messageCounter.set(i, currentMessageCounter + 1);

            boolean maxMessageCountCheck = message.contains("damage!") || message.contains("change weapon");
            int maxMessageCount = maxMessageCountCheck ? 60 : 150;

            // Handler concurrent modification exception when remove
            // a value in for loop
            if (currentMessageCounter > maxMessageCount) {
                try {
                    messages.remove(i);
                    messageCounter.remove(i);

                } catch (ConcurrentModificationException cme) {
                    System.err.println("RenderUI - render game running screen method: concurrent modification exception.");
                }
            }
        }
    }

    private void renderGameWinScreen() {

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 65f));

        String text = "Congratulations";

        int screenX = getCenterPositionForText(text);
        int screenY = FrameAsset.TILE_SIZE * 2 + FrameAsset.TILE_SIZE / 2;

        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50f));

        text = "on winning";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameAsset.TILE_SIZE;
        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

        text = "New game";
        screenX = getCenterPositionForText(text);
        screenY += FrameAsset.TILE_SIZE * 4;

        if (StageOption.WinStage == Menu.NEW_GAME) {
            g2d.drawString(">", screenX - FrameAsset.TILE_SIZE, screenY);
        }
        g2d.drawString(text, screenX, screenY);

        text = "Quit";
        screenX = getCenterPositionForText(text);
        screenY += FrameAsset.TILE_SIZE;

        if (StageOption.WinStage == Menu.QUIT) {
            g2d.drawString(">", screenX - FrameAsset.TILE_SIZE, screenY);
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
        int screenY = FrameAsset.TILE_SIZE * 4 - (FrameAsset.TILE_SIZE * 3) / 4;
        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

        text = "Retry";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameAsset.TILE_SIZE * 4;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.OverStage == Menu.RETRY) {
            g2d.drawString(">", screenX - FrameAsset.TILE_SIZE, screenY);
        }

        text = "Quit";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameAsset.TILE_SIZE;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.OverStage == Menu.QUIT) {
            g2d.drawString(">", screenX - FrameAsset.TILE_SIZE, screenY);
        }
    }

    /**
     * Draw a notification sub window with height and width
     * starting from x, y position
     */
    private void renderSubWindowScreen(int x, int y, int width, int height) {

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

        int screenX = FrameAsset.TILE_SIZE * 2;
        int screenY = FrameAsset.TILE_SIZE / 2;

        int width = FrameAsset.SCREEN_WIDTH - FrameAsset.TILE_SIZE * 4;
        int height = FrameAsset.TILE_SIZE * 4;

        renderSubWindowScreen(screenX, screenY, width, height);

        screenX += FrameAsset.TILE_SIZE / 2;
        screenY += FrameAsset.TILE_SIZE;

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 30f));

        for (String inlineMessage : currentDialogueMessage.split("\n")) {
            g2d.drawString(inlineMessage, screenX, screenY);
            screenY += 40;
        }
    }

    /**
     * Draw game menu when game starts
     */
    private void renderGameMenuScreen() {

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 76));

        String text = "PIXEL HUNTER";

        // Draw string "PIXEL HUNTER in panel
        int screenX = getCenterPositionForText(text);
        int screenY = FrameAsset.TILE_SIZE * 2;

        g2d.setColor(Color.GRAY);
        g2d.drawString(text, screenX - 2, screenY - 2);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, screenX, screenY);

        g2d.setColor(Color.WHITE);

        // Draw player image in panel
        int counter = gamePanel.player.getCounter().drawCounter++;
        if (counter > 10) {
            gamePanel.player.getCounter().drawCounter = 0;
            gamePanel.player.setImageChecker(!gamePanel.player.isImageChecker());
        }
        boolean playerImage = gamePanel.player.isImageChecker();
        screenX = FrameAsset.SCREEN_WIDTH / 2 - (FrameAsset.TILE_SIZE * 3) / 4;
        screenY = screenY + FrameAsset.TILE_SIZE;
        g2d.drawImage(playerImage ? gamePanel.player.getDefaultImages()[6] : gamePanel.player.getDefaultImages()[7], screenX, screenY, 72, 72, null);
        g2d.drawLine(FrameAsset.SCREEN_WIDTH / 6, screenY + 72, (FrameAsset.SCREEN_WIDTH * 5 ) / 6, screenY + 72);

        // Draw menu choose (new game, quit...)
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 45f));
        text = "NEW GAME";

        BufferedImage image = null;

        screenX = getCenterPositionForText(text);
        screenY = screenY + (FrameAsset.TILE_SIZE * 7) / 2 - 24;

        screenY += FrameAsset.TILE_SIZE;

        String imageRootPath = "/menu/";

        image = utool.getImage(imageRootPath + "play", 200, 55);
        g2d.drawImage(image, screenX - 12, screenY - 48, 200, 55, null);
        if (StageOption.MenuStage == Menu.NEW_GAME) {
            image = utool.getImage(imageRootPath + "play_orange", 200, 55);
            g2d.drawImage(image, screenX - 25, screenY - 55, 225, 65, null);
        }

        screenY = screenY + (FrameAsset.TILE_SIZE * 4) / 3;

        image = utool.getImage(imageRootPath + "option", 200, 55);
        g2d.drawImage(image, screenX - 12, screenY - 48, 200, 55, null);
        if (StageOption.MenuStage == Menu.ABOUT) {
            image = utool.getImage(imageRootPath + "option_orange", 200, 55);
            g2d.drawImage(image, screenX - 25, screenY - 52, 225, 65, null);
        }

        screenY = screenY + (FrameAsset.TILE_SIZE * 4) / 3;
        image = utool.getImage(imageRootPath + "quit", 200, 55);
        g2d.drawImage(image, screenX - 12, screenY - 48, 200, 55, null);
        if (StageOption.MenuStage == Menu.QUIT) {
            image = utool.getImage(imageRootPath + "quit_orange", 200, 55);
            g2d.drawImage(image, screenX - 25, screenY - 52, 225, 65, null);
        }
    }

    /**
     * Draw game pause screen
     */
    private void renderGamePauseScreen() {
        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 85f));

        String text = "GAME PAUSE";
        int screenX = getCenterPositionForText(text);
        int screenY = FrameAsset.TILE_SIZE * 4 - (FrameAsset.TILE_SIZE * 3) / 4;
        g2d.drawString(text, screenX, screenY);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

        text = "Continue";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameAsset.TILE_SIZE * 4;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.PauseStage == Menu.CONTINUE) {
            g2d.drawString(">", screenX - FrameAsset.TILE_SIZE, screenY);
        }

        text = "Quit";
        screenX = getCenterPositionForText(text);
        screenY = screenY + FrameAsset.TILE_SIZE;
        g2d.drawString(text, screenX, screenY);
        if (StageOption.PauseStage == Menu.QUIT) {
            g2d.drawString(">", screenX - FrameAsset.TILE_SIZE, screenY);
        }
    }

    /**
     * Draw all player information: life, enegry, time....
     */
    private void renderPlayerInfoScreen() {

        int screenX = FrameAsset.TILE_SIZE;
        int screenY = FrameAsset.TILE_SIZE;

        // Draw player life and energy
        double lifeOnScale = (double) FrameAsset.TILE_SIZE / gamePanel.player.getMaxLife();
        double hpBar = lifeOnScale * gamePanel.player.getLife();

        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(screenX - 1, screenY - 16, FrameAsset.TILE_SIZE * 3 + 2, 24);

        g2d.setColor(new Color(255, 35, 35));
        g2d.fillRect(screenX, screenY - 15, (int) hpBar * 3, 20);

        double manaOnScale = (double) FrameAsset.TILE_SIZE / gamePanel.player.getMaxEnergy();
        double manaBar = manaOnScale * gamePanel.player.getEnergy();

        screenY += (FrameAsset.TILE_SIZE * 3) / 4;
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(screenX - 1, screenY - 16, FrameAsset.TILE_SIZE * 3 + 2, 24);

        g2d.setColor(new Color(255, 255, 153));
        g2d.fillRect(screenX, screenY - 15, (int) manaBar * 3, 20);

        // Draw play time
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 30f));
        g2d.setColor(Color.WHITE);
        String text = convertGamePlayTimeToString();
        screenX = FrameAsset.SCREEN_WIDTH - FrameAsset.TILE_SIZE * 3;
        screenY = FrameAsset.TILE_SIZE;
        g2d.drawString(text, screenX, screenY);
    }

    private void renderPlayerStageScreen() {

        int frameX = (FrameAsset.TILE_SIZE * 3) / 4;
        int frameY = FrameAsset.TILE_SIZE;
        int height = FrameAsset.TILE_SIZE * 8;
        int width = FrameAsset.TILE_SIZE * 5;

        renderSubWindowScreen(frameX, frameY, width, height);

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 32f));

        int textX = frameX + 20;
        int textY = frameY + FrameAsset.TILE_SIZE;
        int lineHeight = 40;

        g2d.drawString("Level", textX, textY);
        textY += lineHeight;
        g2d.drawString("Life", textX, textY);
        textY += lineHeight;
        g2d.drawString("Energy", textX, textY);
        textY += lineHeight;
        g2d.drawString("Damage", textX, textY);
        textY += lineHeight;
        g2d.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2d.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2d.drawString("Weapon", textX, textY);

        int valueX = frameX + width - 30;
        textY = frameY + FrameAsset.TILE_SIZE;

        String value = "";

        value = String.valueOf(gamePanel.player.getLevel());
        textX = getXForAlignToRightText(value, valueX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.player.getLife() + "/" + gamePanel.player.getMaxLife();
        textX = getXForAlignToRightText(value, valueX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.player.getEnergy() + "/" + gamePanel.player.getMaxEnergy();
        textX = getXForAlignToRightText(value, valueX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.getDamage());
        textX = getXForAlignToRightText(value, valueX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.getExperience());
        textX = getXForAlignToRightText(value, valueX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.getNextLevelExp());
        textX = getXForAlignToRightText(value, valueX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight - 25;

        int currentWeapon = gamePanel.player.getWeapon();
        BufferedImage weaponImage = null;
        switch (currentWeapon) {
            case 0 -> weaponImage = StaticImage.AXE;
            case 1 -> weaponImage = StaticImage.PICK;
            case 2 -> weaponImage = StaticImage.SWORD;
        }
        assert weaponImage != null;
        g2d.drawImage(weaponImage, valueX - 29, textY, weaponImage.getWidth(), weaponImage.getHeight(), null);

        lineHeight += 10;

        int usedItemX = FrameAsset.SCREEN_WIDTH - frameX - width;
        int usedItemY = frameY;

        renderSubWindowScreen(usedItemX, usedItemY, width - 80, height / 2 + 30);
        renderSubWindowScreen(usedItemX, usedItemY + height / 2 + 42, width, height / 4 - 12);

        g2d.drawString("[ENTER] to use!", usedItemX + 20, usedItemY + height / 2 + 90);

        usedItemX += 25;
        usedItemY += 25;

        int playerCurrSelectedItem = gamePanel.player.getCurrentSelectedItem();

        if (playerCurrSelectedItem == 0) {
            renderSubWindowScreen(usedItemX - 15, usedItemY - 15, width - 101, 70);
        }

        g2d.drawImage(StaticImage.KEY, usedItemX + 5, usedItemY, 40, 40, null);
        g2d.drawString("x" + gamePanel.player.getKey(), usedItemX + 65, usedItemY + 32);
        usedItemY += lineHeight + 15;

        if (playerCurrSelectedItem == 1) {
            renderSubWindowScreen(usedItemX - 15, usedItemY - 15, width - 101, 70);
        }
        g2d.drawImage(StaticImage.COIN, usedItemX + 5, usedItemY, 40, 40, null);
        g2d.drawString("x" + gamePanel.player.getCoin(), usedItemX + 65, usedItemY + 32);
        usedItemY += lineHeight + 15;

        if (playerCurrSelectedItem == 2) {
            renderSubWindowScreen(usedItemX - 15, usedItemY - 15, width - 101, 70);
        }
        g2d.drawImage(StaticImage.MANA, usedItemX + 5, usedItemY, 40, 40, null);
        g2d.drawString("x" + gamePanel.player.getManas(), usedItemX + 66, usedItemY + 32);
    }

    /**
     * Returns the time format as a string like this: mm:ss:ll
     * + mm = minutes, ss = seconds, ll = milliseconds
     * + example: 12:12:77 (12 minutes, 12 seconds, 77 millisecond)
     */
    private String convertGamePlayTimeToString() {

        double seconds = GamePanel.gamePlayTime / GamePanel.FPS;
        int miliseconds = (int) ((seconds - (int) seconds) * 100);
        int minutes = (int) seconds / 60;
        seconds = (int) seconds - minutes * 60;

        return String.format("%s:%s:%s",
                ("0".repeat(2 - String.valueOf(minutes).length()) + minutes),
                ("0".repeat(2 - String.valueOf((int)seconds).length()) + (int)seconds),
                ("0".repeat(2 - String.valueOf(miliseconds).length()) + miliseconds)
        );
    }

    /**
     * Returns the horizontal position of a string (taken from the
     * width of the font size set for the graphics object)
     */
    private int getCenterPositionForText(String text) {

        int length = (int) (g2d.getFontMetrics().getStringBounds(text, g2d).getWidth() / 2);
        return FrameAsset.SCREEN_WIDTH / 2 - length;
    }

    private int getXForAlignToRightText(String text, int tailX ) {

        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return tailX - length;
    }
}
