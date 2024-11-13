package org.tvd.asset;

import org.tvd.entity.Entity;
import org.tvd.entity.player.Player;
import org.tvd.utility.UtilityTool;

import java.awt.image.BufferedImage;

public class EntitySetter {

    private static final UtilityTool utool = new UtilityTool();

    /**
     * Khởi tạo các thuộc tính là ảnh di chuyển bình thường cho một entity
     * @param entity đối tượng cần khởi tạo các hình ảnh
     */
    public static void loadDefaultEntityImage(Entity entity) {

        // Kiểm tra entity là một player hay monster để lấy path đọc ảnh
        String instance = entity instanceof Player ? "player" : "monster";
        String name = entity.getName();

        BufferedImage[] images = entity.getDefaultImages();

        int width = FrameAsset.TILE_SIZE, height = FrameAsset.TILE_SIZE;
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

    /**
     * Khởi tạo các thuộc tính là ảnh di chuyển khi tấn công cho một entity
     * @param entity thực thể cần được khởi tạo các ảnh khi tấn công
     * @param weapon hình ảnh vũ khí khi tấn công
     */
    public static void loadAttackEntityImage(Entity entity, String weapon) {

        String instance = entity instanceof Player ? "player" : "monster";
        String name = entity.getName();

        BufferedImage[] images = entity.getAttackImages();

        int width = FrameAsset.TILE_SIZE, height = FrameAsset.TILE_SIZE;
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
