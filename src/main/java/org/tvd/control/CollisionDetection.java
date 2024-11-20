package org.tvd.control;

import org.tvd.asset.FrameAsset;
import org.tvd.entity.Entity;
import org.tvd.entity.monster.Monster;
import org.tvd.frame.GamePanel;
import org.tvd.item.SuperItem;

import java.util.ArrayList;

public class CollisionDetection {

    private final GamePanel gamePanel;

    public CollisionDetection(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

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

    public int checkCollisionWithOtherEntities(Entity entity, ArrayList<Entity> other) {

        int entityIndex = -1;

        for (int i = 0; i < other.size(); i++) {

            Monster otherEntity = (Monster) other.get(i);

            if (otherEntity == null) {
                continue;
            }
            if (otherEntity.isAbilityFly()) {
                continue;
            }
            if (otherEntity == entity) {
                continue;
            }

            boolean hasCollision = checkCollisionWithOtherEntity(entity, otherEntity);

            if (hasCollision) {
                entityIndex = i;
            }
        }

        return entityIndex;
    }

    public boolean checkCollisionWithOtherEntity(Entity entity, Entity other) {

        if (!isInCollisionArea(entity, other)) {
            return false;
        }

        entity.solidArea.x += entity.getWorldX();
        entity.solidArea.y += entity.getWorldY();

        other.solidArea.x += other.getWorldX();
        other.solidArea.y += other.getWorldY();

        resetEntityPositionOnCollision(entity);

        if (entity.solidArea.intersects(other.solidArea)) {
            entity.setCollisionOn(true);

            return true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        other.solidArea.x = other.solidAreaDefaultX;
        other.solidArea.y = other.solidAreaDefaultY;

        return false;
    }

    private boolean isInCollisionArea(Entity entity, Entity other) {

        double detectionRadius = FrameAsset.TILE_SIZE * 10;

        double width = Math.abs(entity.getWorldX() - other.getWorldX());
        double height = Math.abs(entity.getWorldY() - other.getWorldY());

        double distanceBetweenTowEntity = Math.sqrt(
                Math.pow(width, 2) + Math.pow(height, 2)
        );

        return distanceBetweenTowEntity <= detectionRadius;
    }

    public void checkCollisionWithItem(Entity entity, boolean isPlayer) {


    }

    private boolean isInCollisionArea(Entity entity, SuperItem item) {

        double detectionRadius = FrameAsset.TILE_SIZE * 3;

        double width = Math.abs(entity.getWorldX() - item.getWorldX());
        double height = Math.abs(entity.getWorldY() - item.getWorldY());

        double distanceBetweenEntityAndItem = Math.sqrt(
                Math.pow(width, 2) + Math.pow(height, 2)
        );

        return distanceBetweenEntityAndItem <= detectionRadius;
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
