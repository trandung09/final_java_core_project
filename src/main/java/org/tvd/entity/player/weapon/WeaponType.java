package org.tvd.entity.player.weapon;

import lombok.Getter;

@Getter
public enum WeaponType {

    AXE(1), PICK(2), SWORD(3);

    private final int damage;

    WeaponType(int damage) {
        this.damage = damage;
    }
}
