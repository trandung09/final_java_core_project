package org.tvd.utility;

import java.awt.image.BufferedImage;

public class StaticImage {

    private static final String rootPath = "/item/";

    private static final UtilityTool uTool = new UtilityTool();

    public static final BufferedImage KEY   = uTool.getImage(rootPath + "key", 38, 38);
    public static final BufferedImage MANA  = uTool.getImage(rootPath + "mana", 38, 38);
    public static final BufferedImage COIN  = uTool.getImage(rootPath + "coin", 38, 38);

    public static final BufferedImage SWORD = uTool.getImage(rootPath + "sword", 38, 38);
    public static final BufferedImage PICK  = uTool.getImage(rootPath + "pick", 38, 38);
    public static final BufferedImage AXE   = uTool.getImage(rootPath + "sword", 38, 38);
}
