package org.tvd.config;

import org.tvd.entity.monster.Monster;
import org.tvd.entity.monster.MonsterFactory;
import org.tvd.entity.monster.MonsterManager;
import org.tvd.frame.GamePanel;
import org.tvd.item.ItemFactory;
import org.tvd.item.ItemManager;
import org.tvd.item.SuperItem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class AssetSetter {

    private static final String resourcePath = "src/main/resources/asset/";

    private static BufferedReader br;

    private static final ItemFactory itemFactory = ItemFactory.getInstance();
    private static final MonsterFactory monsterFactory = MonsterFactory.getInstance();

    public static void loadAllItem(GamePanel gamePanel, ItemManager itemManager) {
        int level = gamePanel.gameLevel;

        String itemInitFilePath = resourcePath + "item" + level + ".txt";
        try {

            br = Files.newBufferedReader(Path.of(itemInitFilePath), StandardCharsets.UTF_8);

            while (true) {

                String content = br.readLine();
                if (content == null) break;

                String[] param = content.split(" ");

                String itemName = param[0];
                int ipX = Integer.parseInt(param[1]);
                int ipY = Integer.parseInt(param[2]);

                SuperItem item = itemFactory.getItem(itemName, gamePanel);

                if (item == null) continue;

                item.setWorldX(ipX);
                item.setWorldY(ipY);

                itemManager.add(item);
            }

        } catch (IOException e) {
            System.err.println("AssetSetter: error reading file.");
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.err.println("AssetSetter: error closing file.");
            }
        }
    }

    public static void loadAllMonster(GamePanel gamePanel, MonsterManager monsterManager) {

        int level = gamePanel.gameLevel;

        String monsterInitFilePath = resourcePath + "monster" + level + ".txt";
        try {

            br = Files.newBufferedReader(Path.of(monsterInitFilePath), StandardCharsets.UTF_8);

            while (true) {

                String content = br.readLine();
                if (content == null) break;

                String[] param = content.split(" ");

                String monsterName = param[0];
                int mpX = Integer.parseInt(param[1]);
                int mpY = Integer.parseInt(param[2]);

                Monster monster = monsterFactory.getMonster(monsterName, gamePanel);

                if (monster == null) continue;

                monster.setWorldX(mpX);
                monster.setWorldY(mpY);

                monsterManager.add(monster);
            }
        } catch (IOException e) {
            System.err.println("AssetSetter: error reading file.");
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.err.println("AssetSetter: error closing file.");
            }
        }
    }
}
