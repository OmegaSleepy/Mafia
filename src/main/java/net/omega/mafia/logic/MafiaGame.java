package net.omega.mafia.logic;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.omega.mafia.MafiaConfig;
import net.omega.mafia.data.GamePlayer;
import net.omega.mafia.data.GameState;
import net.omega.mafia.data.Role;
import net.omega.mafia.data.Room;
import net.omega.mafia.util.RoleDistributor;

import java.util.List;

public class MafiaGame {
    public GameState gameState;
    public List<GamePlayer> players;
    public MinecraftServer server;
    public int round;

    public MafiaGame (MinecraftServer server) {
        this.server = server;

        int playercount = server.getPlayerList().getPlayerCount();
        if (playercount < 0) {
            throw new IllegalArgumentException(
                    Component.translatable("exception.game.playercount").toString()
                            .formatted(MafiaConfig.MINIMUM_PLAYER_COUNT.get(), playercount));
        }


        List<Role> roles = RoleDistributor.generateRoleList(playercount);

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            players.add(new GamePlayer(
                    player.getUUID(),
                    roles.getFirst(),
                    new Room(new Vec3(0,0,0), 1), //fetch rooms from config/etc
                    player
            ));
            roles.removeFirst();
        }

        //maybe remove this
        beginGame();
    }

    private void beginGame () {
        this.round = 0;
        gameState = GameState.DINNER;
        for (GamePlayer player : players) {
            player.player.addItem(player.role.getNapkin());
            player.player.setPos(player.room.pos());
        }
        announce(Component.translatable("message.game.begin"));

    }

    private void announce(Component component) {
        server.sendSystemMessage(component);
    }


}
