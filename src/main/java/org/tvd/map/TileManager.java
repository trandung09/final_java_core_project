package org.tvd.map;

import lombok.Data;
import lombok.Getter;

import org.tvd.asset.FrameAsset;
import org.tvd.frame.GamePanel;
import org.tvd.utility.UtilityTool;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Data
public class TileManager {

    // Inject
    private final GamePanel gamePanel;

    // Map name
    private String mapName;

    // Utility tool
    private UtilityTool utool = new UtilityTool();

    // Map properties
    private int[][] maps  = new int[FrameAsset.MAX_WORLD_ROW][FrameAsset.MAX_WORLD_COL];
    private final Tile[]  tiles = new Tile[50];

    public TileManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        mapName = Map.maps.get(GamePanel.gameLevel);

        loadAllTiles();
        loadMapNum();
    }

    public void loadAllTiles() {

        setUpForTile(0, "000", false);
        setUpForTile(1, "001", false);
        setUpForTile(2, "002", false);
        setUpForTile(3, "003", false);
        setUpForTile(4, "004", false);
        setUpForTile(5, "005", false);
        setUpForTile(6, "006", false);
        setUpForTile(7, "007", false);
        setUpForTile(8, "008", false);
        setUpForTile(9, "009", false);
        setUpForTile(10, "010", false);
        setUpForTile(11, "011", false);
        setUpForTile(12, "012", false);
        setUpForTile(13, "013", false);
        setUpForTile(14, "014", false);
        setUpForTile(15, "015", false);
        setUpForTile(16, "016", true);
        setUpForTile(17, "017", false);
        setUpForTile(18, "018", true);
        setUpForTile(19, "019", true);
        setUpForTile(20, "020", true);
        setUpForTile(21, "021", true);
        setUpForTile(22, "022", true);
        setUpForTile(23, "023", true);
        setUpForTile(24, "024", true);
        setUpForTile(25, "025", true);
        setUpForTile(26, "026", true);
        setUpForTile(27, "027", true);
        setUpForTile(27, "028", true);
        setUpForTile(29, "029", true);
        setUpForTile(30, "030", true);
        setUpForTile(31, "031", true);
        setUpForTile(32, "032", true);
        setUpForTile(33, "033", true);
        setUpForTile(34, "034", true);
        setUpForTile(35, "035", true);
        setUpForTile(36, "036", false);
        setUpForTile(37, "037", true);
    }

    public void loadMapNum() {

        try {
            File file = new File("src/main/resources/map/" + mapName);
            BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

            int row = 0;
            String line;

            while (true) {

                line = br.readLine();
                if (line == null)
                    break;

                String[] parts = line.split(" ");
                for (int col = 0; col < parts.length; col++) {
                    maps[row][col] = Integer.parseInt(parts[col]);
                }

                row++;
            }

            br.close();

        } catch (IOException e) {
            System.err.println("Tile manager - loadMapNum(): could not load map " + mapName + "(" + e.getMessage() + ")");
        }
    }

    public void render(Graphics2D g2d){

        int row = 0, col = 0, worldX, worldY, screenX, screenY;

        while (row < FrameAsset.MAX_WORLD_ROW && col < FrameAsset.MAX_WORLD_COL) {

            int tileIndex = maps[row][col];

            worldX = col * FrameAsset.TILE_SIZE;
            worldY = row * FrameAsset.TILE_SIZE;
            screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.getScreenX();
            screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.getScreenY();

            if (worldX + FrameAsset.TILE_SIZE > gamePanel.player.getWorldX() - gamePanel.player.screenX &&
                    worldX - FrameAsset.TILE_SIZE < gamePanel.player.getWorldX() + gamePanel.player.screenX &&
                    worldY + FrameAsset.TILE_SIZE > gamePanel.player.getWorldY() - gamePanel.player.screenY &&
                    worldY - FrameAsset.TILE_SIZE < gamePanel.player.getWorldY() + gamePanel.player.screenY
            ) {

                g2d.drawImage(tiles[tileIndex].image, screenX, screenY, null);
            }

            col++;
            if (col == FrameAsset.MAX_WORLD_COL) {
                row++;
                col = 0;
            }
        }
    }

    private void setUpForTile(int index, String path, boolean collision) {

        String fullPath = "/tile/" + path;

        tiles[index] = new Tile();
        tiles[index].collision = collision;
        tiles[index].image = utool.getImage(fullPath, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
    }

    // Class for get map in level
    @Getter
    public static class Map {

        public static final List<String> maps = new ArrayList<>();

        static {
            maps.add("dungeon00.txt");
            maps.add("dungeon01.txt");
            maps.add("dungeon02.txt");
        }
    }
}
