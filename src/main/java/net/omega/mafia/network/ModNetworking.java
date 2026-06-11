package net.omega.mafia.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.minecraft.network.chat.Component;
import net.omega.mafia.Mafia;

@EventBusSubscriber(modid = Mafia.MODID)
public class ModNetworking {

    @SubscribeEvent
    public static void registerNetworkPackets(RegisterPayloadHandlersEvent event) {
        // Create a registrar for your mod version
        final PayloadRegistrar registrar = event.registrar("1.0.0");

        // Register the packet to be sent from Client to Server (c2s)
        registrar.playToServer(
                PlayerClickedPayload.TYPE,
                PlayerClickedPayload.CODEC,
                ModNetworking::handlePlayerClickOnServer
        );
    }

    // This code runs strictly on the Server Thread when the packet arrives
    private static void handlePlayerClickOnServer(PlayerClickedPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            // The player who opened the UI and clicked the button
            ServerPlayer sender = (ServerPlayer) context.player(); 
            
            // The UUID sent from the client
            java.util.UUID targetUuid = payload.clickedPlayerUuid();

            // Fetch the actual target player object on the server, if they are still online
            ServerPlayer targetPlayer = sender.server.getPlayerList().getPlayer(targetUuid);

            if (targetPlayer != null) {
                // Execute your server-side logic here!
                sender.sendSystemMessage(Component.literal("Server verified click on: " + targetPlayer.getScoreboardName()));
                
                // Example action: Teleport sender to target
                sender.teleportTo(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ());
            }
        });
    }
}