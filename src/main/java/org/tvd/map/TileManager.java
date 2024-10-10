package org.tvd.map;

import org.tvd.config.FrameConfig;

import java.awt.Graphics2D;

public class TileManager {

    public String[] maps = new String[]{"map1", "map2"};
    public Tile[] tiles = new Tile[50];
    public int[][] mapNums = new int[FrameConfig.MAX_WORLD_ROW][FrameConfig.MAX_WORLD_COL];

    private void loadTile() {}

    public void loadMap(String mapName) {

    }

    public void render(Graphics2D graphics2D){}

    private void setUpForTile(int index, String imagePath, boolean collision) {

        int width = FrameConfig.MAX_WORLD_COL;
        int height = FrameConfig.MAX_WORLD_ROW;
    }
}
