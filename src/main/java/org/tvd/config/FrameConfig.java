package org.tvd.config;

public final class FrameConfig {

    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;

    public static int MAX_SCREEN_COL = 16;
    public static int MAX_SCREEN_ROW = 12;

    public static int SCARE = 3;
    public static int EDGE_TILE_SIZE = 16;
    public static int TILE_SIZE = SCARE * EDGE_TILE_SIZE;

    public static int SCREEN_WIDTH = MAX_SCREEN_COL * TILE_SIZE;
    public static int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE;
}
