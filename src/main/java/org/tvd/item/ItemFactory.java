package org.tvd.item;

import org.tvd.frame.GamePanel;

public class ItemFactory {

    private ItemFactory() {
    }

    public SuperItem getItem(String name, GamePanel gamePanel) {

        if (name == null || gamePanel == null) {
            throw new IllegalArgumentException("name or gamePanel is null");
        }

        return switch (name.toLowerCase()) {
            case "boots" -> new Boots(gamePanel);
            case "coin" -> new Coin(gamePanel);
            case "diamond" -> new Diamond(gamePanel);
            case "key" -> new Key(gamePanel);
            case "lantern" -> new Lantern(gamePanel);
            case "tent" -> new Tent(gamePanel);
            default -> null;
        };
    }

    public static ItemFactory getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final ItemFactory INSTANCE = new ItemFactory();
    }
}
