package org.tvd.entity.player;

import lombok.Getter;
import lombok.Setter;

import org.tvd.config.EntityConfig;
import org.tvd.config.FrameConfig;
import org.tvd.entity.Entity;
import org.tvd.entity.EntityActions;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

@Getter
@Setter
public class Player extends Entity implements EntityActions {

    // Player status
    private PlayerStatus status = PlayerStatus.PLAY;

    // Player attributes
    private int level = 1;
    private int exp   = 0;
    private int nextLevelExp = 10;
    private int energy = 0;
    private int maxEnergy = 100;
    private int manas = 0;
    private int maxManas = 100;

    public Player(GamePanel gamePanel) {
        super(gamePanel);

        this.name = "boy";

        this.worldY = 264;
        this.worldX = 360;

        EntityConfig.loadDefaultEntityImage(this);
        EntityConfig.loadAttackEntityImage(this, "none");
    }

    @Override
    public void update() {

        counter.invincibleCounter++;

        if (counter.invincibleCounter > 50) {

            counter.invincibleCounter = 0;

            worldY += 20;
        }
    }

    @Override
    public void render(Graphics2D g) {

        counter.drawCounter++;

        if (image == null) image = defaultImages[6];

        if (counter.drawCounter > 12) {
            counter.drawCounter = 0;

            image = image == defaultImages[6] ? defaultImages[7] : defaultImages[6];
        }

        g.drawImage(image, worldX, worldY, FrameConfig.TILE_SIZE, FrameConfig.TILE_SIZE, null);
    }

    @Override
    public void attack() {

    }

    @Override
    public void defense() {

    }
}
