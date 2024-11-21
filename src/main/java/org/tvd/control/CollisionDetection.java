package org.tvd.control;

import org.tvd.asset.FrameAsset;
import org.tvd.entity.Entity;
import org.tvd.entity.monster.Monster;
import org.tvd.entity.player.Player;
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

        int entityLeftCol = worldLeftX / FrameAsset.TILE_SIZE;
        int entityRightCol = worldRightX / FrameAsset.TILE_SIZE;
        int entityTopRow = worldTopY / FrameAsset.TILE_SIZE;
        int entityBottomRow = worldBottomY / FrameAsset.TILE_SIZE;

        int tileOne, tileTwo;

        switch (entity.getDirection()) {
            case UP -> {
                entityTopRow = (worldTopY - entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[entityTopRow][entityLeftCol];
                tileTwo = gamePanel.tileManager.getMaps()[entityTopRow][entityRightCol];
            }
            case DOWN -> {
                entityBottomRow = (worldBottomY + entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[entityBottomRow][entityLeftCol];
                tileTwo = gamePanel.tileManager.getMaps()[entityBottomRow][entityRightCol];
            }
            case LEFT -> {
                entityLeftCol = (worldLeftX - entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[entityTopRow][entityLeftCol];
                tileTwo = gamePanel.tileManager.getMaps()[entityBottomRow][entityLeftCol];
            }
            case RIGHT -> {
                entityRightCol = (worldRightX + entity.getSpeed()) / FrameAsset.TILE_SIZE;
                tileOne = gamePanel.tileManager.getMaps()[entityTopRow][entityRightCol];
                tileTwo = gamePanel.tileManager.getMaps()[entityBottomRow][entityRightCol];
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

        boolean isCollisionOn = false;

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
            isCollisionOn = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        other.solidArea.x = other.solidAreaDefaultX;
        other.solidArea.y = other.solidAreaDefaultY;

        return isCollisionOn;
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

    public int checkCollisionWithItems(Entity entity, ArrayList<SuperItem> items) {

        int itemIndex = -1;

        for (int i = 0; i < items.size(); i++) {

            SuperItem item = items.get(i);

            if (item == null) {
                continue;
            }

            boolean hasCollision = checkCollisionWithItem(entity, item);

            if (hasCollision && entity instanceof Player) {
                itemIndex = i;
            }
        }

        return itemIndex;
    }

    private boolean checkCollisionWithItem(Entity entity, SuperItem item) {

        boolean isCollisionOn = false;

        if (!isInCollisionArea(entity, item)) {
            return false;
        }
        if (!item.isCollision() && entity instanceof Player) {
            return false;
        }

        entity.solidArea.x += entity.getWorldX();
        entity.solidArea.y += entity.getWorldY();

        item.solidArea.x += item.getWorldX();
        item.solidArea.y += item.getWorldY();

        resetEntityPositionOnCollision(entity);

        if (entity.solidArea.intersects(item.solidArea)) {
            entity.setCollisionOn(true);
            isCollisionOn = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        item.solidArea.x = item.solidAreaDefaultX;
        item.solidArea.y = item.solidAreaDefaultY;

        return isCollisionOn;
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
