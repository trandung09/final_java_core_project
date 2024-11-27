package org.tvd.enums;

import lombok.Getter;

@Getter
public enum WeaponType {

    AXE(5), PICK(7), SWORD(10);

    private final int damage;

    WeaponType(int damage) {
        this.damage = damage;
    }
}
