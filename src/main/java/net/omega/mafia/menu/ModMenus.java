package net.omega.mafia.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.omega.mafia.Mafia;
import net.omega.mafia.data.Role;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Mafia.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<NapkinMenu>> NAPKIN_MENU =
            MENUS.register("napkin_menu", () ->
                    IMenuTypeExtension.create((windowId, inv, data) -> {
                        try {
                            String role = data.readUtf();
                            Role roleValue = Role.valueOf(role);
                            return new NapkinMenu(windowId, inv, roleValue);
                        } catch (Exception e) {
                            return new NapkinMenu(windowId, inv, Role.NONE);
                        }
                    })
            );

    public static void register (IEventBus eventBus) {
        MENUS.register(eventBus);
    }


}