package net.omega.mafia.events;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.omega.mafia.Mafia;
import net.omega.mafia.screens.PlayerListScreen;

@EventBusSubscriber(modid = Mafia.MODID, value = Dist.CLIENT)
public class ClientInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().options.keyPlayerList.consumeClick()) {
            Minecraft.getInstance().setScreen(new PlayerListScreen());
        }
    }
}