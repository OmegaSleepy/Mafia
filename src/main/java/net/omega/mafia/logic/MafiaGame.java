package net.omega.mafia.logic;


import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.omega.mafia.MafiaConfig;
import net.omega.mafia.data.GamePlayer;
import net.omega.mafia.data.GameState;
import net.omega.mafia.data.Role;
import net.omega.mafia.data.Room;
import net.omega.mafia.util.RoleDistributor;

import java.util.*;

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
            System.out.println(gamePlayer.player.getName() + " " + gamePlayer.role);
            player.getInventory().clearContent();
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

    public void skipStage() {
        ticksLeft = 0;
        onTimerExpire();
    }

    public void startPhase(GameState newState, int durationInTicks) {
        this.gameState = newState;
        this.ticksLeft = durationInTicks;
        this.isTimerRunning = true;
        systemAnnounce(Component.literal("Phase changed to: " + newState.name() + ". Time starts now!"));
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
        systemAnnounce(Component.literal("Time is up for phase: " + gameState.name()));

        switch (gameState) {
            case DINNER -> startPhase(GameState.VOTE, MafiaConfig.VOTING_TIME);

            case VOTE -> {
                List<Map.Entry<GamePlayer, Integer>> votedOutList = getVotedOut();
                if (votedOutList == null) {
                    announce(Component.translatable("message.game.no-votes"));
                    startPhase(GameState.VOTE, MafiaConfig.DISCUSSION_TIME);
                    return;
                }

                if (votedOutList.size() == 1) {
                    var player = votedOutList.getFirst().getKey();
                    var votesReceived = votedOutList.getFirst().getValue();

                    announce(Component.translatable("message.game.voted-out",
                            player.player.getName().getString(), votesReceived.toString()));
                    player.kill();

                    // TODO: MAFIA win/lose check

                    startPhase(GameState.NIGHT, MafiaConfig.NIGHT_ACTION_TIME);
                    round++;
                } else {
                    announce(Component.translatable("message.game.stalemate",
                            votedOutList.stream().map(entry -> entry.getKey().player.getName().getString()).toList().toString(),
                            votedOutList.getFirst().getValue().toString()));
                    startPhase(GameState.VOTE, MafiaConfig.DISCUSSION_TIME);
                }
            }

            case NIGHT -> {
                // TODO: apply dead players
                startPhase(GameState.MORNING, MafiaConfig.PRE_DEATH_ANNOUNCE);
                announce(Component.translatable("message.phase2.announce-breakfast"));
            }

            case MORNING -> {
                // TODO: announce dead players
                startPhase(GameState.AFTERNOON, MafiaConfig.FREE_ROAM_TIME);
            }

            case AFTERNOON -> {
                announce(Component.translatable("message.game.dinner-begin"));
                startPhase(GameState.DINNER, MafiaConfig.DISCUSSION_TIME);
            }
        }
    }

    private void announce(Component component) {
        players.stream().map(player -> player.player).forEach(player -> player.displayClientMessage(component, false));
    }

    private void systemAnnounce(Component component) {
        server.sendSystemMessage(component);
    }

    private List<Map.Entry<GamePlayer, Integer>> getVotedOut() {
        if (votes == null || votes.isEmpty()) {
            return null;
        }

        Map<GamePlayer, Integer> voteCounts = new HashMap<>();
        for (GamePlayer player : votes) {
            voteCounts.put(player, voteCounts.getOrDefault(player, 0) + 1);
        }

        GamePlayer winner = null;
        int maxVotes = voteCounts.values().stream().max(Integer::compareTo).get();
        boolean isTie = false;

        return voteCounts.entrySet()
                .stream().filter(entry -> entry.getValue() >= maxVotes)
                .toList();


    }


}
