package org.tvd.utility;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class UtilityTool {

    public BufferedImage getImage(String imagePath, int width, int height) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = scaleImage(image, width, height);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading image " + e.getMessage());
        }
        return image;
    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaleImage = new BufferedImage(width, height, original.getType());
        Graphics2D g = scaleImage.createGraphics();

        g.drawImage(original, 0, 0, width, height, null);
        g.dispose();

        return scaleImage;
    }
}
