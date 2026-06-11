package net.omega.mafia.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.omega.mafia.components.ModDataComponents;
import net.omega.mafia.items.ModItems;
import net.omega.mafia.util.RoleDistributor;

public class RandomItemCommand {

    public static void register (CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("game")
                .executes(RandomItemCommand::giveNapkins)
        );

    }

    private static int giveNapkins (CommandContext<CommandSourceStack> context) {

        int playercount = context.getSource().getServer().getPlayerCount();

        if (playercount < 6) {
            context.getSource().sendFailure(Component.literal("You can't start the game with " + playercount + " players(S). A minimum of {6} is required."));
            return 0;
        }

        var roles = RoleDistributor.generateRoleList(playercount);
        var players = context.getSource().getServer().getPlayerList().getPlayers();

        for (ServerPlayer serverPlayer : players) {
            ItemStack napkin = ModItems.NAPKIN.toStack();
            napkin.set(ModDataComponents.ROLE, roles.getFirst().name().toLowerCase());
            roles.removeFirst();
            serverPlayer.addItem(napkin);
        }

        return 1;
    }


}
