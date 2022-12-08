package org.elzzz.linkage.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.elzzz.linkage.LinkageMod;

public class MarkPlayerAsAvailableC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity serverPlayerEntity,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        LinkageMod.availablePlayers.put(serverPlayerEntity.getUuidAsString(), serverPlayerEntity);
    }
}
