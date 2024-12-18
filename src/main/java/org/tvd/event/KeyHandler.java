package org.tvd.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.tvd.frame.GamePanel;
import org.tvd.enums.GameStatus;
import org.tvd.enums.Menu;
import org.tvd.utility.StageOption;

public class KeyHandler implements KeyListener {

    private final GamePanel gamePanel;

    public KeyPressed pressed = new KeyPressed();

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch (gamePanel.gameStatus) {
            case GAME_MENU -> keyHandlerMenuGameStage(keyCode);
            case GAME_RUNNING -> keyHandlerRunningGameStage(keyCode);
            case GAME_PAUSE -> {
                if (GamePanel.gameLevel > GamePanel.maxGameLevel) {
                    keyHandlerWinGameStage(keyCode);
                }
                else {
                    keyHandlerPauseGameStage(keyCode);
                }
            }
            case GAME_WIN -> keyHandlerWinGameStage(keyCode);
            case GAME_OVER -> keyHandlerOverGameStage(keyCode);
            case GAME_DIALOGUE -> keyHandlerDialogueStage(keyCode);
            case CHARACTER_ST -> keyHandlerCharacterGameStage(keyCode);
            default -> {}
        }
    }

    private void keyHandlerRunningGameStage(int keyCode) {

        switch (keyCode) {
            case KeyEvent.VK_SHIFT -> pressed.mini_map = !pressed.mini_map;
            case KeyEvent.VK_W -> pressed.up = true;
            case KeyEvent.VK_S -> pressed.down = true;
            case KeyEvent.VK_A -> pressed.left = true;
            case KeyEvent.VK_D -> pressed.right = true;
            case KeyEvent.VK_P -> {
                if (gamePanel.gameStatus == GameStatus.GAME_RUNNING) {
                    gamePanel.gameStatus = GameStatus.GAME_PAUSE;
                }
            }
            case KeyEvent.VK_SPACE -> {
                pressed.space = true;
                gamePanel.player.setSleeping(!gamePanel.player.isSleeping());
            }
            case KeyEvent.VK_ENTER -> gamePanel.player.setAttacking(true);
            case KeyEvent.VK_BACK_SLASH -> {
                pressed.back_slash = true;
                gamePanel.player.switchWeapon();
            }
            case KeyEvent.VK_L -> pressed.lighting = !pressed.lighting;
            case KeyEvent.VK_QUOTE -> gamePanel.gameStatus = GameStatus.CHARACTER_ST;
            default -> {}
        }
    }

    private void keyHandlerCharacterGameStage(int keyCode) {

        int playerCurrentSelectedItem = gamePanel.player.getCurrentSelectedItem();

        switch (keyCode) {
            case KeyEvent.VK_QUOTE -> gamePanel.gameStatus = GameStatus.GAME_RUNNING;
            case KeyEvent.VK_ENTER -> {

                switch (playerCurrentSelectedItem) {
                    case 0 -> gamePanel.player.useKey();
                    case 1 -> gamePanel.player.useCoin();
                    case 2 -> gamePanel.player.healing();
                }

                gamePanel.gameStatus = GameStatus.GAME_RUNNING;
            }
            case KeyEvent.VK_UP -> {
                playerCurrentSelectedItem--;
                if (playerCurrentSelectedItem < 0) playerCurrentSelectedItem = 2;
                gamePanel.player.setCurrentSelectedItem(playerCurrentSelectedItem);
            }
            case KeyEvent.VK_DOWN -> {
                playerCurrentSelectedItem++;
                if (playerCurrentSelectedItem > 2) playerCurrentSelectedItem = 0;
                gamePanel.player.setCurrentSelectedItem(playerCurrentSelectedItem);
            }
            default -> {}
        }
    }

    private void keyHandlerDialogueStage(int keyCode) {

        switch (keyCode) {
            case KeyEvent.VK_ENTER -> gamePanel.gameStatus = GameStatus.GAME_RUNNING;
            default -> {}
        }
    }

    private void keyHandlerOverGameStage(int keyCode) {

        switch (keyCode) {
            case KeyEvent.VK_ENTER -> {
                if (StageOption.OverStage == Menu.RETRY) {
                    gamePanel.sound.stop();
                    gamePanel.player.init();
                    gamePanel.init();
                    gamePanel.gameStatus = GameStatus.GAME_MENU;
                }
                else if (StageOption.OverStage == Menu.QUIT) System.exit(0);
            }
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> {
                if (StageOption.OverStage == Menu.RETRY) StageOption.OverStage = Menu.QUIT;
                else if (StageOption.OverStage == Menu.QUIT) StageOption.OverStage = Menu.RETRY;
            }
        }
    }

    private void keyHandlerWinGameStage(int keyCode) {

        switch (keyCode) {
            case KeyEvent.VK_ENTER -> {
                if (StageOption.WinStage == Menu.NEW_GAME) {
                    gamePanel.player.init();
                    gamePanel.player.getPressed().lighting = true;
                    gamePanel.sound.stop();
                    gamePanel.init();
                    gamePanel.gameStatus = GameStatus.GAME_MENU;
                }
                else if (StageOption.WinStage == Menu.QUIT) System.exit(0);
            }        
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> {
                if (StageOption.WinStage == Menu.NEW_GAME) StageOption.WinStage = Menu.QUIT;
                else if (StageOption.WinStage == Menu.QUIT) StageOption.WinStage = Menu.NEW_GAME;
            }
            default -> {}
        }
    }

    private void keyHandlerMenuGameStage(int keyCode) {

        switch (keyCode) {
            case KeyEvent.VK_ENTER -> {
                if (StageOption.MenuStage == Menu.QUIT) System.exit(0);
                if (StageOption.MenuStage == Menu.NEW_GAME) gamePanel.gameStatus = GameStatus.GAME_RUNNING;
            }
            case KeyEvent.VK_UP -> {
                if (StageOption.MenuStage == Menu.NEW_GAME) StageOption.MenuStage = Menu.QUIT;
                else if (StageOption.MenuStage == Menu.ABOUT) StageOption.MenuStage = Menu.NEW_GAME;
                else if (StageOption.MenuStage == Menu.QUIT) StageOption.MenuStage = Menu.ABOUT;
            }
            case KeyEvent.VK_DOWN -> {
                if (StageOption.MenuStage == Menu.NEW_GAME) StageOption.MenuStage = Menu.ABOUT;
                else if (StageOption.MenuStage == Menu.ABOUT) StageOption.MenuStage = Menu.QUIT;
                else if (StageOption.MenuStage == Menu.QUIT) StageOption.MenuStage = Menu.NEW_GAME;
            }
        }
    }

    private void keyHandlerPauseGameStage(int keyCode) {

        switch (keyCode) {
            case KeyEvent.VK_ENTER -> {
                if (StageOption.PauseStage == Menu.CONTINUE) gamePanel.gameStatus = GameStatus.GAME_RUNNING;
                else if (StageOption.PauseStage == Menu.QUIT) {
                    StageOption.PauseStage = Menu.CONTINUE;
                    gamePanel.sound.stop();
                    gamePanel.init();
                    gamePanel.player.init();
                    gamePanel.gameStatus = GameStatus.GAME_MENU;
                }
            }
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> {
                if (StageOption.PauseStage == Menu.CONTINUE) StageOption.PauseStage = Menu.QUIT;
                else if (StageOption.PauseStage == Menu.QUIT) StageOption.PauseStage = Menu.CONTINUE;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_A -> pressed.left = false;
            case KeyEvent.VK_D -> pressed.right = false;
            case KeyEvent.VK_W -> pressed.up = false;
            case KeyEvent.VK_S -> pressed.down = false;
            case KeyEvent.VK_ENTER -> pressed.enter = false;
            case KeyEvent.VK_SPACE -> pressed.space = false;
            default -> {}
        }
    }

}
