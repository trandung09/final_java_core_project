package org.tvd.entity.monster;

import org.tvd.frame.GamePanel;

public class MonsterFactory {

    private MonsterFactory() {
    }

    public Monster getMonster(String name, GamePanel gamePanel) {

        if (name == null || gamePanel == null) {
            throw new IllegalArgumentException("Monster name or gamePanel is null");
        }

        return switch (name.toLowerCase()) {
            case "bat" -> new Bat(gamePanel);
            case "skeleton lord" -> new SkeletonLord(gamePanel);
            case "slimey" -> new Slimey(gamePanel);
            case "orc" -> new Orc(gamePanel);
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };
    }

    public static MonsterFactory getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final MonsterFactory INSTANCE = new MonsterFactory();
    }
}
