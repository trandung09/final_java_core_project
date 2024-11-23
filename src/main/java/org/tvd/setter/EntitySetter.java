package org.tvd.setter;

import org.tvd.entity.Entity;
import org.tvd.entity.monster.SkeletonLord;
import org.tvd.entity.player.Player;
import org.tvd.utility.UtilityTool;

import java.awt.image.BufferedImage;

public class EntitySetter {

    private static final UtilityTool utool = new UtilityTool();

    public static void loadDefaultEntityImage(Entity entity) {

        String instance = entity instanceof Player ? "player" : "monster";
        String name = entity.getName();

        BufferedImage[] images = entity.getDefaultImages();

        int width = FrameAsset.TILE_SIZE, height = FrameAsset.TILE_SIZE;

        if ("monster".equals(instance) && entity instanceof SkeletonLord) {
            width = FrameAsset.TILE_SIZE * 2;
            height = FrameAsset.TILE_SIZE * 2;
        }

        String path = "/" + instance + "/" + name + "_";

        images[0] = utool.getImage(path + "up_1", width, height);
        images[1] = utool.getImage(path + "up_2", width, height);
        images[2] = utool.getImage(path + "down_1", width, height);
        images[3] = utool.getImage(path + "down_2", width, height);
        images[4] = utool.getImage(path + "left_1", width, height);
        images[5] = utool.getImage(path + "left_2", width, height);
        images[6] = utool.getImage(path + "right_1", width, height);
        images[7] = utool.getImage(path + "right_2", width, height);
    }

    public static void loadAttackEntityImage(Entity entity, String weapon) {

        String instance = entity instanceof Player ? "player" : "monster";
        String name = entity.getName();

        BufferedImage[] images = entity.getAttackImages();

        int width = FrameAsset.TILE_SIZE, height = FrameAsset.TILE_SIZE;

        if ("monster".equals(instance) && entity instanceof SkeletonLord) {
            width = FrameAsset.TILE_SIZE * 2;
            height = FrameAsset.TILE_SIZE * 2;
        }

        String path = "/" + instance + "/" + name + "_" + weapon + "_";

        images[0] = utool.getImage(path + "up_1", width, height * 2);
        images[1] = utool.getImage(path + "up_2", width, height * 2);
        images[2] = utool.getImage(path + "down_1", width, height * 2);
        images[3] = utool.getImage(path + "down_2", width, height * 2);
        images[4] = utool.getImage(path + "left_1", width * 2, height);
        images[5] = utool.getImage(path + "left_2", width * 2, height);
        images[6] = utool.getImage(path + "right_1", width * 2, height);
        images[7] = utool.getImage(path + "right_2", width * 2, height);
    }
}
