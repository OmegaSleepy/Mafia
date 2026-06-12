package net.omega.mafia.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.omega.mafia.logic.MafiaGame;

import java.util.Random;

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
                
        );
    }
}
