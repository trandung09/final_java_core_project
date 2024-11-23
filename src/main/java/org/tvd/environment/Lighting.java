package org.tvd.environment;

import org.tvd.setter.FrameAsset;
import org.tvd.frame.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    private final GamePanel gamePanel;

    private BufferedImage darknessFilter;

    private final Color[] color = new Color[12];
    private final float[] fraction = new float[12];

    public Lighting(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public void setUp() {

        darknessFilter = new BufferedImage(FrameAsset.SCREEN_WIDTH, FrameAsset.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) darknessFilter.getGraphics();

        // duong kinh
        int circleSize = 400;

        Area screenArea = new Area(new Rectangle2D.Double(0, - FrameAsset.SCREEN_HEIGHT, FrameAsset.SCREEN_WIDTH * 2, FrameAsset.SCREEN_HEIGHT * 2));

        int centerX = gamePanel.player.screenX + FrameAsset.TILE_SIZE / 2;
        int centerY = gamePanel.player.screenY + FrameAsset.TILE_SIZE / 2;

        // tam vung sang
        double x = centerX - (double) circleSize / 2;
        double y = centerY - (double) circleSize / 2;

        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);
        Area lightArea = new Area(circleShape);

        screenArea.subtract(lightArea);

        g2d.setPaint(setLightSource(centerX, centerY, circleSize));

        g2d.fill(lightArea);
        g2d.fill(screenArea);

        g2d.dispose();
    }

    public void setColor() {

        if (gamePanel.keyHandler.pressed.lighting) {

            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.22f);
            color[2] = new Color(0, 0, 0.1f, 0.36f);
            color[3] = new Color(0, 0, 0.1f, 0.51f);
            color[4] = new Color(0, 0, 0.1f, 0.59f);
            color[5] = new Color(0, 0, 0.1f, 0.66f);
            color[6] = new Color(0, 0, 0.1f, 0.72f);
            color[7] = new Color(0, 0, 0.1f, 0.77f);
            color[8] = new Color(0, 0, 0.1f, 0.81f);
            color[9] = new Color(0, 0, 0.1f, 0.84f);
            color[10] = new Color(0, 0, 0.1f, 0.86f);
            color[11] = new Color(0, 0, 0.1f, 0.88f);

        } else{
            for (int i = 0; i < 12 ; i++) {
                color[i] = new Color(0, 0, 0.1f, 0.1f);
            }
        }
    }

    public RadialGradientPaint setLightSource(int centerX, int centerY, int circleSize) {

        setColor();

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        return new RadialGradientPaint(centerX, centerY, (float) circleSize / 2, fraction, color);
    }

    public void render(Graphics2D g2d) {

        setUp();
        g2d.drawImage(darknessFilter, 0, 0, null);
    }
}
