package org.tvd.control;

import org.tvd.asset.FrameAsset;
import org.tvd.entity.Entity;
import org.tvd.frame.GamePanel;

public class CollisionDetection {

    private final GamePanel gamePanel;

    public CollisionDetection(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    /**
     * Kiểm tra va chạm của thực thể với các tile trên bản đồ
     * @param entity thực thể được kiểm tra
     */
    public void checkCollisionWithTile(Entity entity) {

        int worldLeftX = entity.getWorldX() + entity.solidArea.x;
        int worldRightX = entity.getWorldX() + entity.solidArea.x + entity.solidArea.width;
        int worldTopY = entity.getWorldY() + entity.solidArea.y;
        int worldBottomY = entity.getWorldY() + entity.solidArea.y + entity.solidArea.height;

        int playerLeftCol = worldLeftX / FrameAsset.TILE_SIZE;
        int playerRightCol = worldRightX / FrameAsset.TILE_SIZE;
        int playerTopRow = worldTopY / FrameAsset.TILE_SIZE;
        int playerBottomRow = worldBottomY / FrameAsset.TILE_SIZE;

        int tileOne, tileTwo;

        switch (entity.getDirection()) {

            case UP -> {
                playerTopRow = (worldTopY - entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[playerTopRow][playerLeftCol];
                tileTwo = gamePanel.tileManager.getMaps()[playerTopRow][playerRightCol];
            }

            case DOWN -> {
                playerBottomRow = (worldBottomY + entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[playerBottomRow][playerLeftCol];
                tileTwo = gamePanel.tileManager.getMaps()[playerBottomRow][playerRightCol];
            }

            case LEFT -> {
                playerLeftCol = (worldLeftX - entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[playerTopRow][playerLeftCol];
                tileTwo = gamePanel.tileManager.getMaps()[playerBottomRow][playerLeftCol];
            }

            case RIGHT -> {
                playerRightCol = (worldRightX + entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[playerTopRow][playerRightCol];
                tileTwo = gamePanel.tileManager.getMaps()[playerBottomRow][playerRightCol];
            }

            default ->
                throw new IllegalStateException("Unexpected value: " + entity.getDirection());
        }

        if (gamePanel.tileManager.getTiles()[tileOne].collision || gamePanel.tileManager.getTiles()[tileTwo].collision) {
            entity.setCollisionOn(true);
        }
    }

    public void checkCollisionWithOtherEntity(Entity entity, Entity[] other) {

    }

    public void checkCollisionWithItem(Entity entity, boolean player) {

    }

    public void checkCollisionWithPlayer(Entity entity) {

    }

    private void resetEntityPositionOnCollision(Entity entity) {

        switch (entity.getDirection()) {

            case LEFT -> entity.solidArea.x -= entity.getSpeed();
            case RIGHT -> entity.solidArea.x += entity.getSpeed();
            case UP -> entity.solidArea.y -= entity.getSpeed();
            case DOWN -> entity.solidArea.y += entity.getSpeed();
        }
    }
}
