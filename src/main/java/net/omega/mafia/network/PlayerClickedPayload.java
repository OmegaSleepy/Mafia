package net.omega.mafia.network;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.omega.mafia.Mafia;

import java.util.UUID;

public record PlayerClickedPayload(UUID clickedPlayerUuid) implements CustomPacketPayload {

    public static final Type<PlayerClickedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Mafia.MODID, "player_clicked"));

    // Codec to automatically serialize and deserialize the UUID over the network buffer
    public static final StreamCodec<FriendlyByteBuf, PlayerClickedPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, // Use net.minecraft.core.UUIDUtil for built-in UUID streaming
            PlayerClickedPayload::clickedPlayerUuid,
            PlayerClickedPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}