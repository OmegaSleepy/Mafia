package net.omega.mafia.data;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayer {
    public final UUID uuid;
    public final Role role;
    public final Room room;
    public final ServerPlayer player;
    public boolean isAlive;

    public final List<Boolean> acts;

    public GamePlayer(UUID uuid, Role role, Room room, ServerPlayer player) {
        this.uuid = uuid;
        this.role = role;
        this.room = room;
        this.player = player;
        this.acts = new ArrayList<>();
        isAlive = true;
    }

    ///Clears acts
    public boolean survives() {
        boolean isDead = true;

        for (Boolean act : acts) {
            isDead = isDead && act;
        }
        acts.clear();

        return isDead;
    }

    public void kill() {
        isAlive = false;
        player.setGameMode(GameType.SPECTATOR);
    }

}
