package org.tvd.entity;

import lombok.Getter;
import lombok.Setter;
import org.tvd.frame.GamePanel;
import org.tvd.setter.FrameAsset;

import java.awt.Rectangle;

@Getter
@Setter
public class Projectile extends Entity {

    private Entity user;

    public Projectile(GamePanel gamePanel) {

        super(gamePanel);

        solidArea = new Rectangle(0, 0, FrameAsset.TILE_SIZE, FrameAsset.TILE_SIZE);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void init(Entity user, boolean isAlive) {

        this.user = user;
        this.isAlive = isAlive;
        this.worldX = user.getWorldX();
        this.worldY = user.getWorldY();
        this.direction = user.getDirection();
    }

    @Override
    public void update() {

        super.update();

        life--;

        counter();
        moving();
    }

    public void counter() {

        super.counter();

        counter.drawCounter++;
        if (counter.drawCounter > 10) {
            counter.drawCounter = 0;
            imageChecker = !imageChecker;
        }
    }
    @Override
    public void setAction() {

    }

    @Override
    public void resetAction() {

    }
}
