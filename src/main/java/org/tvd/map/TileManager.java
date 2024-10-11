package org.tvd.map;

import lombok.Data;
import org.tvd.config.FrameConfig;
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
    private String mapName = Map.maps.get(0);

    // Utility tool
    private UtilityTool utool = new UtilityTool();

    // Map properties
    private int[][] maps  = new int[FrameConfig.MAX_WORLD_ROW][FrameConfig.MAX_WORLD_COL];
    private final Tile[]  tiles = new Tile[50];

    public TileManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        loadAllTiles();
        loadMap();
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

    public void loadMap() {

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
            System.err.println("Could not load map " + mapName + "(" + e.getMessage() + ")");
        }
    }

    public void render(Graphics2D graphics2D){

        int row = 0, col = 0, worldX, worldY, screenX, screenY;

        while (row < FrameConfig.MAX_WORLD_ROW && col < FrameConfig.MAX_WORLD_COL) {

            int tileIndex = maps[row][col];

            worldX = col * FrameConfig.TILE_SIZE;
            worldY = row * FrameConfig.TILE_SIZE;
            screenX = worldX - gamePanel.player.getWorldX() + gamePanel.player.getScreenX();
            screenY = worldY - gamePanel.player.getWorldY() + gamePanel.player.getScreenY();

            graphics2D.drawImage(tiles[tileIndex].image, screenX, screenY, null);

            col++;
            if (col == FrameConfig.MAX_WORLD_COL) {
                row++;
                col = 0;
            }
        }
    }

    private void setUpForTile(int index, String path, boolean collision) {

        String fullPath = "/tile/" + path;

        tiles[index] = new Tile();
        tiles[index].image = utool.getImage(fullPath, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE);
    }

    @Data
    private static class Map {

        private static final List<String> maps = new ArrayList<String>();

        static {
            maps.add("dungeon01.txt");
            maps.add("dungeon02.txt");
        }
    }
}
