package org.tvd.entity.monster;

import lombok.Getter;
import lombok.Setter;
import org.tvd.entity.Direction;
import org.tvd.entity.Projectile;
import org.tvd.entity.Rock;
import org.tvd.frame.GamePanel;
import org.tvd.setter.EntitySetter;
import org.tvd.setter.FrameAsset;

@Setter
@Getter
public class Slimey extends Monster {

    public Slimey(GamePanel gamePanel) {

        super(gamePanel);

        init();
    }

    public void init() {

        super.init();

        this.name = "slimey";
        this.speed = 1;
        this.damage = 5;
        this.experienceReward = 8;
        this.maxLife = 17;
        this.life = maxLife;

        EntitySetter.loadDefaultEntityImage(this);
    }

    @Override
    public void update() {

        if (life <= 0 && counter.dyingCounter > 40) {
            isAlive = false;
        }

        setAction();

        isCollisionOn = false;

        detection.checkCollisionWithItems(this, gamePanel.itemManager);
        detection.checkCollisionWithOtherEntity(this, gamePanel.player);
        detection.checkCollisionWithOtherEntities(this, gamePanel.monsterManager);

        detection.checkCollisionWithTile(this);

        handleProjectileShooting();
        attacking();

        counter();
        moving();
    }

    @Override
    public void setAction() {

        super.setAction();
    }

    @Override
    public void attacking() {

       for (Projectile projectile : projectiles) {
           projectile.setCollisionOn(false);
           detection.checkCollisionWithOtherEntity(projectile, gamePanel.player);

           if (projectile.isCollisionOn()) {
               damagePlayer();
               projectile.setCollisionOn(false);
           }
       }
    }

    private void addProjectile() {

        Projectile projectile = new Rock(gamePanel);
        projectile.init(this, true);

        projectiles.add(projectile);

        counter.shootCounter = 0;
    }

    private void handleProjectileShooting() {

        if (counter.shootCounter++ >= (int) 1e9) {
            counter.shootCounter = 0;
        }

        int playerWorldX = gamePanel.player.getWorldX();
        int playerWorldY = gamePanel.player.getWorldY();

        int distanceX = Math.abs(playerWorldX - worldX);
        int distanceY = Math.abs(playerWorldY - worldY);

        if (distanceX < FrameAsset.TILE_SIZE) {
            if ((playerWorldY > worldY && direction == Direction.DOWN) || (playerWorldY <= worldY && direction == Direction.UP)) {
                if (counter.shootCounter > 300 && Math.abs(playerWorldY - worldY) <= 300) {
                    addProjectile();
                }
            }
        }

        if (distanceY < FrameAsset.TILE_SIZE) {
            if ((playerWorldX > worldX && direction == Direction.RIGHT || (playerWorldX <= worldX && direction == Direction.LEFT))) {
                if (counter.shootCounter > 300 && Math.abs(playerWorldY - worldY) <= 300) {
                    addProjectile();
                }
            }
        }
    }
}
