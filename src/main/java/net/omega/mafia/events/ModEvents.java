package net.omega.mafia.events;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.omega.mafia.Mafia;
import net.omega.mafia.components.ModDataComponents;

import static net.omega.mafia.logic.MafiaGame.activeGame;

@EventBusSubscriber(modid = Mafia.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onItemToss (ItemTossEvent event) {
        ItemStack stack = event.getEntity().getItem();

        // Check if the item has a custom 'ROLE' component attached to it
        if (stack.has(ModDataComponents.ROLE.get())) {

            event.setCanceled(true);
            event.getPlayer().getInventory().add(stack);

        }
    }

    @SubscribeEvent
    public static void onServerTick (ServerTickEvent.Post event) {
        if (activeGame != null) {
            activeGame.tick();
        }
    }
}
