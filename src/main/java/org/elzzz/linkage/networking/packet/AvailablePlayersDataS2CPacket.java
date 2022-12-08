package org.elzzz.linkage.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.data.PlayerData;
import org.elzzz.linkage.item.DebugPieItem;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

import java.util.HashMap;
import java.util.UUID;

public class AvailablePlayersDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender sender) {
        HashMap<UUID, PlayerData> availablePlayersData = AvailablePlayersPacketBufHandler.readBuf(buf);

        if(client.player != null) {
            DebugPieItem.showAvailablePlayers(client.player, availablePlayersData);
        }
        else {
            LinkageMod.LOGGER.error("Received available_players_data packet has no player info");
        }
    }
}
