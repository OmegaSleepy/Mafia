package net.omega.mafia.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.omega.mafia.components.ModDataComponents;
import net.omega.mafia.items.Napkin;

public class SetNameCommand {
    public static void register (CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("renameitem")
                .executes(context -> executeRename(context, ""))
                .then(Commands.argument("name", StringArgumentType.greedyString())
                        .executes(context -> {
                            String name = StringArgumentType.getString(context, "name");
                            return executeRename(context, name);
                        })
                )
        );
    }

    private static int executeRename (CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var item = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (item.isEmpty()) {
            context.getSource().sendFailure(Component.literal("You are not holding an item!"));
            return 0;
        }

        if (item.getItem() instanceof Napkin) {
            item.set(ModDataComponents.ROLE, name);
        } else {
            item.set(DataComponents.ITEM_NAME, Component.literal(name));
        }

        player.setItemInHand(InteractionHand.MAIN_HAND, item);
        return Command.SINGLE_SUCCESS;
    }
}

