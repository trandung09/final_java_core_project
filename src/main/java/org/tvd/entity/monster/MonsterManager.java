package org.tvd.entity.monster;

import lombok.Getter;
import lombok.Setter;

import org.tvd.entity.Projectile;
import org.tvd.setter.AssetSetter;
import org.tvd.entity.Entity;
import org.tvd.frame.GamePanel;

import java.awt.Graphics2D;
import java.util.ArrayList;

@Getter
@Setter
public class MonsterManager extends ArrayList<Entity> {

    private final GamePanel gamePanel;
    private final String[] monsterInMap = new String[10];

    public MonsterManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        init();
    }

    public void init() {
        AssetSetter.loadAllMonster(gamePanel, this);
    }

    public void update() {

       removeIf(o ->  o == null || o.getLife() <= 0);

       for (Entity monster : this) {
           monster.update();
       }

       for (Entity monster : this) {
           if (monster.getProjectiles().isEmpty()) {
               continue;
           }

           ArrayList<Projectile> projectiles = monster.getProjectiles();
           projectiles.removeIf(o -> o.getLife() < 0);

           for (Projectile projectile : projectiles) {
               projectile.update();
           }
       }
    }

    @Override
    public boolean isEmpty() {

        return this.stream().map(o -> (Monster) o)
                .noneMatch(Monster::isAbilityAttack);
    }

    public void renderMonsterCannotFly(Graphics2D g2d) {

        for (Entity entity : this) {
            if (entity instanceof Monster monster && monster.isAbilityAttack()) {
                monster.render(g2d);
            }
        }
    }

    public void renderMonsterCanFly(Graphics2D g2d) {
        
        for (Entity entity : this) {
            if (entity instanceof Monster monster && monster.isAbilityFly()) {
                monster.render(g2d);
            }
        }
    }

    public void renderProjectileOfMonster(Graphics2D g2d) {
        for (Entity entity : this) {
            if (entity.getProjectiles().isEmpty()) {
                continue;
            }
            for (Projectile projectile : entity.getProjectiles()) {
                projectile.render(g2d);
            }
        }
    }
}
