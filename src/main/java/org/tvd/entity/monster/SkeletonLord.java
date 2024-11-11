package org.tvd.entity.monster;

import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

public class SkeletonLord extends Monster {
    
    public SkeletonLord(GamePanel gamePanel) {

        super(gamePanel);

        init();
    }

    public void init() {
        this.name = "skeletonlord";
        this.speed = 4;
        this.maxLife = 50;
        this.life = maxLife;
        this.abilityAttack = true;
        this.abilityFly = false;
    }

    @Override
    public void setAction() {
        
    }

    @Override
    public void render(Graphics2D g2d) {

        if (image == null) {
            image = defaultImages[0];
        }
        g2d.drawImage(image, worldX, worldY, image.getWidth(), image.getHeight(), null);
    }
}
