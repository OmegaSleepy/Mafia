package net.omega.mafia.logic;


import com.mojang.authlib.GameProfile;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.omega.mafia.MafiaConfig;
import net.omega.mafia.data.GamePlayer;
import net.omega.mafia.data.GameState;
import net.omega.mafia.data.Role;
import net.omega.mafia.data.Room;
import net.omega.mafia.util.RoleDistributor;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MafiaGame {
    public static MafiaGame activeGame = null;

    public GameState gameState;
    public List<GamePlayer> players = new ArrayList<>();
    public HashMap<UUID, GamePlayer> uuidGamePlayerHashSet = new HashMap<>();
    public MinecraftServer server;
    public static final Random RANDOM = new Random();
    public int round;

    public List<GamePlayer> votes = new ArrayList<>();

    private int ticksLeft;
    private boolean isTimerRunning = false;

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
            var gamePlayer = new GamePlayer(
                    player.getUUID(),
                    roles.getFirst(),
                    new Room(new Vec3(0,0,0), 1), //fetch rooms from config/etc
                    player
            );
            players.add(gamePlayer);
            uuidGamePlayerHashSet.put(player.getUUID(), gamePlayer);

            roles.removeFirst();
        }

        beginGame();
    }

    private void beginGame () {
        this.round = 0;
        startPhase(GameState.DINNER, MafiaConfig.DISCUSSION_TIME);

        for (GamePlayer player : players) {
            player.player.addItem(player.role.getNapkin());
            player.player.setPos(player.room.pos());
        }
        announce(Component.translatable("message.game.dinner-begin"));
    }

    private void skipStage() {
        gameState = GameState.values()[((gameState.ordinal()+1)%GameState.values().length)];
    }

    public void startPhase(GameState newState, int durationInTicks) {
        this.gameState = newState;
        this.ticksLeft = durationInTicks;
        this.isTimerRunning = true;
        announce(Component.literal("Phase changed to: " + newState.name() + ". Time starts now!"));
    }

    public void startPhase(GameState newState, ModConfigSpec.IntValue durationInTicks) {
        startPhase(newState, durationInTicks.get());
    }

    public void tick() {
        if (!isTimerRunning) return;

        ticksLeft--;

        if (ticksLeft % 20 == 0 && ticksLeft > 0) {
            int secondsLeft = ticksLeft / 20;
            for (GamePlayer gp : players) {
                gp.player.displayClientMessage(Component.literal("Time remaining: " + secondsLeft + "s"), true);
            }
        }

        if (ticksLeft <= 0) {
            onTimerExpire();
        }
    }

    private void onTimerExpire() {
        isTimerRunning = false;
        announce(Component.literal("Time is up for phase: " + gameState.name()));

        if (gameState == GameState.DINNER) {
            startPhase(GameState.VOTE, MafiaConfig.VOTING_TIME);
        }

        if (gameState == GameState.VOTE) {
            GamePlayer votedOut = getVotedOut();
            if (votedOut != null) {
                startPhase(GameState.NIGHT, MafiaConfig.NIGHT_ACTION_TIME);
            } else {
                announce(Component.translatable("message.game.stalemate"));
                startPhase(GameState.VOTE, MafiaConfig.DISCUSSION_TIME);
            }
        }

        if (gameState == GameState.NIGHT) {
            //TODO apply dead players
            startPhase(GameState.MORNING, MafiaConfig.PRE_DEATH_ANNOUNCE);
            announce(Component.translatable("message.phase2.announce-breakfast"));
        }

        if (gameState == GameState.MORNING) {
            //TODO announce dead players
            startPhase(GameState.AFTERNOON, MafiaConfig.FREE_ROAM_TIME);
        }

        if (gameState == GameState.AFTERNOON) {
            announce(Component.translatable("message.game.dinner-begin"));
            startPhase(GameState.DINNER, MafiaConfig.DISCUSSION_TIME);
        }

    }

    private void announce(Component component) {
        server.sendSystemMessage(component);
    }

    private @Nullable GamePlayer getVotedOut() {
        if (votes == null || votes.isEmpty()) {
            return null;
        }

        Map<GamePlayer, Integer> voteCounts = new HashMap<>();
        for (GamePlayer player : votes) {
            voteCounts.put(player, voteCounts.getOrDefault(player, 0) + 1);
        }

        GamePlayer winner = null;
        int maxVotes = 0;
        boolean isTie = false;

        for (Map.Entry<GamePlayer, Integer> entry : voteCounts.entrySet()) {
            int currentVotes = entry.getValue();

            if (currentVotes > maxVotes) {
                maxVotes = currentVotes;
                winner = entry.getKey();
                isTie = false;
            } else if (currentVotes == maxVotes) {
                isTie = true;
            }
        }

        return isTie ? null : winner;
    }


}
