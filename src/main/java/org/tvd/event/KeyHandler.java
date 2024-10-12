package org.tvd.event;

import org.tvd.frame.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private final GamePanel gamePanel;

    public KeyPressed pressed = new KeyPressed();

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_A:
                pressed.left = true;
                break;
            case KeyEvent.VK_D:
                pressed.right = true;
                break;
            case KeyEvent.VK_W:
                pressed.up = true;
                break;
            case KeyEvent.VK_S:
                pressed.down = true;
                break;
            case KeyEvent.VK_L:
                pressed.lighting = !pressed.lighting;
                break;
            case KeyEvent.VK_ENTER:
                pressed.enter = true;
                break;
            default:
                throw new IllegalArgumentException("Unsupported key code: " + code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_A:
                pressed.left = false;
                break;
            case KeyEvent.VK_D:
                pressed.right = false;
                break;
            case KeyEvent.VK_W:
                pressed.up = false;
                break;
            case KeyEvent.VK_S:
                pressed.down = false;
                break;
            case KeyEvent.VK_L:
                break;
            case KeyEvent.VK_ENTER:
                System.out.println(pressed.enter);
                pressed.enter = false;
                break;
            default:
                throw new IllegalArgumentException("Unsupported key code: " + code);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
