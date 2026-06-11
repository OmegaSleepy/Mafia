package net.omega.mafia.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.omega.mafia.Mafia;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = 
            DeferredRegister.create(Registries.MENU, Mafia.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<NapkinMenu>> NAPKIN_MENU = 
            MENUS.register("napkin_menu", () -> IMenuTypeExtension.create((windowId, inv, data) -> new NapkinMenu(windowId, inv)));

    public static void register (IEventBus eventBus) {
        MENUS.register(eventBus);
    }


}