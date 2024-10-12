package org.tvd.entity.player.weapon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weapon {

    private String name;
    private int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
}
