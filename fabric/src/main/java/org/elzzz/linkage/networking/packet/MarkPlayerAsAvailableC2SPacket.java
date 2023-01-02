package org.elzzz.linkage.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.networking.ModNetworking;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

public class MarkPlayerAsAvailableC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity serverPlayerEntity,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        LinkageMod.availablePlayers.put(serverPlayerEntity.getUuidAsString(), serverPlayerEntity);

        LinkageMod.availablePlayers.forEach((uuid, player) -> {
            ServerPlayNetworking.send((ServerPlayerEntity) player, ModNetworking.UPDATE_AVAILABLE_PLAYERS_DATA,
                    AvailablePlayersPacketBufHandler.getBuf(LinkageMod.availablePlayers));
        });
    }
}
