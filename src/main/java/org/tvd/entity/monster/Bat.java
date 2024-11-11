package org.tvd.entity.monster;

import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;

public class Bat extends Monster {

    public Bat(GamePanel gamePanel) {

        super(gamePanel);
        
        init();
    }

    private void init() {

        this.name = "bat";
        this.speed = 2;
        this.maxLife = 5;
        this.life = maxLife;
        this.abilityAttack = false;
        this.abilityFly = true;
    }

    public void update() {

        setAction();
    }

    @Override
    public void resetAction() {

    }


    @Override
    public void render(Graphics2D g2d) {

        if (image == null) {
            image = defaultImages[0];
        }
        g2d.drawImage(image, worldX, worldY, image.getWidth(), image.getHeight(), null);
    }
}
