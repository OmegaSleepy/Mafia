package net.omega.mafia.data;

import net.minecraft.world.item.ItemStack;
import net.omega.mafia.components.ModDataComponents;
import net.omega.mafia.items.ModItems;

public enum Role {
    MAFIA,
    BOSS,
    DETECTIVE,
    MEDIC,
    ESCORT,
    NONE;

    public String translationKey () {
        return "napkin.message." + name().toLowerCase();
    }

    public String titleKey () {
        return "napkin.title." + name().toLowerCase();
    }

    public ItemStack getNapkin () {
        var napkin = new ItemStack(ModItems.NAPKIN.get());
        napkin.set(ModDataComponents.ROLE, this.name().toLowerCase());
        return napkin;
    }

}
