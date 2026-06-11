package net.omega.mafia.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Objects;

public class RandomItemCommand {

    public static final java.util.Random RANDOM = new java.util.Random();

    public static final List<Item> randomItems = List.of(Items.ANDESITE, Items.APPLE, Items.DIAMOND, Items.GOLDEN_SWORD, Items.OBSIDIAN);


    public static void register (CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("randomitem")
                .executes(context -> {
                    Objects.requireNonNull(context.getSource().getPlayer())
                            .addItem(
                                    randomItems.get(RANDOM.nextInt(randomItems.size())).getDefaultInstance()
                            );
                    return 1;
                }));
    }


}
