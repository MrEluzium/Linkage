package org.elzzz.linkage.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.networking.ModNetworking;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

import java.util.HashMap;

public class MarkPlayerAsUnavailableC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity serverPlayerEntity,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        LinkageMod.availablePlayers.remove(serverPlayerEntity.getUuidAsString(), serverPlayerEntity);

        /* tell sender to clear his data since he is removed  */
        ServerPlayNetworking.send(serverPlayerEntity, ModNetworking.UPDATE_AVAILABLE_PLAYERS_DATA,
                    AvailablePlayersPacketBufHandler.getBuf(new HashMap<String, PlayerEntity>()));

        LinkageMod.availablePlayers.forEach((uuid, player) -> {
            ServerPlayNetworking.send((ServerPlayerEntity) player, ModNetworking.UPDATE_AVAILABLE_PLAYERS_DATA,
                    AvailablePlayersPacketBufHandler.getBuf(LinkageMod.availablePlayers));
        });
    }
}
