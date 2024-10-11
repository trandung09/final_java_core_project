package org.tvd.environment;

import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

public class EnvironmentManager {

    private final Lighting lighting;

    public EnvironmentManager(GamePanel gamePanel) {

        this.lighting = new Lighting(gamePanel);
    }

    public void render(Graphics2D g2d) {

        lighting.render(g2d);
    }

    public void update() {

    }
}
