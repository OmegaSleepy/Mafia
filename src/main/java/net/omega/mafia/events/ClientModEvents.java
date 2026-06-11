package net.omega.mafia.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.omega.mafia.Mafia;
import net.omega.mafia.menu.ModMenus;
import net.omega.mafia.screens.NapkinScreen;

@EventBusSubscriber(modid = Mafia.MODID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.NAPKIN_MENU.get(), NapkinScreen::new);
    }
}