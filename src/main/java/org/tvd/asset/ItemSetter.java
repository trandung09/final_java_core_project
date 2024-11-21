package org.tvd.asset;

import org.tvd.item.SuperItem;
import org.tvd.utility.UtilityTool;

public class ItemSetter {

    private static final UtilityTool utool = new UtilityTool();

    public static void loadDefaultItemImage(SuperItem item) {

        String path = "/item/" + item.getName();

        item.setDefaultImage(utool.getImage(path, item.getImageSize(), item.getImageSize()));
    }
}
