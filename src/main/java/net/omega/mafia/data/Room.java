package net.omega.mafia.data;

import net.minecraft.world.phys.Vec3;

public record Room(
        Vec3 pos,
        int id
) {
}
