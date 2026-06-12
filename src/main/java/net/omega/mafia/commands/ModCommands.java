package net.omega.mafia.commands;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.omega.mafia.Mafia;

@EventBusSubscriber(modid = Mafia.MODID)
public class ModCommands {

    @SubscribeEvent
    private static void registerCommands (RegisterCommandsEvent event) {
        RandomItemCommand.register(event.getDispatcher());
        SetNameCommand.register(event.getDispatcher());
        MapMakerCommands.register(event.getDispatcher());
    }

}
