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
            case "bat" -> {
                Bat bat = new Bat(gamePanel);
                EntitySetter.loadDefaultEntityImage(bat);

                yield  bat;
            }
            case "skeletonlord" -> {
                SkeletonLord skeletonLord = new SkeletonLord(gamePanel);
                EntitySetter.loadDefaultEntityImage(skeletonLord);
                EntitySetter.loadAttackEntityImage(skeletonLord, "phase2_attack");

                yield skeletonLord;
            }
            case "slimey" -> {
                Slimey slimey = new Slimey(gamePanel);
                EntitySetter.loadDefaultEntityImage(slimey);

                yield slimey;
            }
            case "orc" -> {
                Orc orc = new Orc(gamePanel);
                EntitySetter.loadDefaultEntityImage(orc);
                EntitySetter.loadAttackEntityImage(orc, "attack");

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
