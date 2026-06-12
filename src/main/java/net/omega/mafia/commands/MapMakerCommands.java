package net.omega.mafia.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.omega.mafia.logic.MafiaGame;

import java.util.Random;
import java.util.UUID;

public class MapMakerCommands {

    public static final Random random = new Random();

    public static void register (CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mapmaker")
                .then(Commands.literal("start")
                        .executes(context -> {
                            MafiaGame.activeGame = new MafiaGame(context.getSource().getServer());
                            return 1;
                        })
                )
                .then(Commands.literal("skipstage")
                        .executes(MapMakerCommands::skipStage)
                )
                .then(Commands.literal("stage")
                        .executes(MapMakerCommands::getStage)
                )
                .then(Commands.literal("reveal")
                        .executes(MapMakerCommands::revealRoles)
                )
                .then(Commands.literal("vote")
                        .executes(context -> 1)
                        .then(Commands.argument("target-player", EntityArgument.player())
                                .executes(context -> {
                                    ServerPlayer target = EntityArgument.getPlayer(context, "target-player");

                                    java.util.UUID playerUuid = target.getUUID();

                                    return vote(context, playerUuid);
                                })
                        )
                )

        );
    }

    private static int vote (CommandContext<CommandSourceStack> context, UUID UUID) {
        if (MafiaGame.activeGame.gameState == null) {
            context.getSource().sendFailure(Component.literal("Game has not started"));
            return 0;
        }

        if (UUID == null) {
            context.getSource().sendFailure(Component.literal("Player UUID is null"));
            return 0;
        }

        var player = MafiaGame.activeGame.uuidGamePlayerHashSet.get(UUID);

        if (player == null) {
            context.getSource().sendFailure(Component.literal("Player not found"));
            return 0;
        }

        MafiaGame.activeGame.votes.add(player);
        context.getSource().sendSuccess(() ->
                        Component.literal("Successful vote for %s".formatted(
                                context.getSource().getServer().getPlayerList().getPlayer(UUID).getName().getString())
                        ),
                true
        );

        return 1;
    }

    private static int revealRoles (CommandContext<CommandSourceStack> context) {
        if (MafiaGame.activeGame.gameState == null) {
            context.getSource().sendFailure(Component.literal("Game has not started"));
            return 0;
        }

        StringBuilder stringBuilder = new StringBuilder();
        MafiaGame.activeGame.players.forEach(player -> {
            stringBuilder.append(player.player.getName().getString()).append(" : ").append(player.role).append(" : ").append(player.isAlive ? "alive" : "dead").append("\n");
        });

        context.getSource().sendSuccess(() -> Component.literal(stringBuilder.toString()), true);
        return 1;
    }

    private static int getStage (CommandContext<CommandSourceStack> context) {
        if (MafiaGame.activeGame.gameState == null) {
            context.getSource().sendFailure(Component.literal("Game has not started"));
            return 0;
        }

        context.getSource().sendSuccess(() -> Component.literal("%s : %d".formatted(MafiaGame.activeGame.gameState, MafiaGame.activeGame.round)), true);
        return 1;
    }

    private static int skipStage (CommandContext<CommandSourceStack> context) {
        if (MafiaGame.activeGame.gameState == null) {
            context.getSource().sendFailure(Component.literal("Game has not started"));
            return 0;
        }

        MafiaGame.activeGame.skipStage();
        return 1;
    }
}
