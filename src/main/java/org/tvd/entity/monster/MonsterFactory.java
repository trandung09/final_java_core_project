package org.tvd.entity.monster;

import org.tvd.asset.EntitySetter;
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
            case "skeletonlord" -> new SkeletonLord(gamePanel);
            case "slimey" -> {
                Slimey slimey = new Slimey(gamePanel);
                EntitySetter.loadDefaultEntityImage(slimey);

                yield slimey;
            }
            case "orc" -> new Orc(gamePanel);
            default -> null;
        };
    }

    public static MonsterFactory getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final MonsterFactory INSTANCE = new MonsterFactory();
    }
}
