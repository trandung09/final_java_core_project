package org.tvd;

import org.tvd.frame.GameFrame;

import javax.swing.SwingUtilities;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        SwingUtilities.invokeLater(
                () -> {
                    new GameFrame().setVisible(true);
                });
    }
}
