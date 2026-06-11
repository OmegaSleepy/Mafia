package net.omega.mafia.items;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.omega.mafia.Mafia;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Mafia.MODID);

    public static final DeferredItem<Item> NAPKIN = ITEMS.register("napkin", Napkin::new);


    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
