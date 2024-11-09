package org.tvd.entity.monster;

import org.tvd.config.EntityConfig;
import org.tvd.frame.GamePanel;

public class MonsterFactory {

    private MonsterFactory() {
    }

    public Monster getMonster(String name, GamePanel gamePanel) {

        if (name == null || gamePanel == null) {
            throw new IllegalArgumentException("Monster name or gamePanel is null");
        }

        return switch (name.toLowerCase()) {
            case "bat" -> {
                Bat bat = new Bat(gamePanel);
                EntityConfig.loadDefaultEntityImage(bat);

                yield  bat;
            }
            case "skeleton lord" -> {
                SkeletonLord skeletonLord = new SkeletonLord(gamePanel);
                EntityConfig.loadDefaultEntityImage(skeletonLord);
                EntityConfig.loadAttackEntityImage(skeletonLord, "phase2_attack");

                yield skeletonLord;
            }
            case "slimey" -> {
                Slimey slimey = new Slimey(gamePanel);
                EntityConfig.loadDefaultEntityImage(slimey);

                yield slimey;
            }
            case "orc" -> {
                Orc orc = new Orc(gamePanel);
                EntityConfig.loadDefaultEntityImage(orc);
                EntityConfig.loadAttackEntityImage(orc, "attack");

                yield orc;
            }
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
