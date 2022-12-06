package org.elzzz.linkage.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.PlayerListEntry;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.data.PlayerData;
import org.elzzz.linkage.item.DebugPieItem;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

import java.util.HashMap;
import java.util.UUID;

public class LinkageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();

        /* available_players_data packet receiver */
        ClientPlayNetworking.registerGlobalReceiver(LinkageMod.availablePlayersPacketIdentifier, (client, handler, buf, responseSender) -> {
            HashMap<UUID, PlayerData> availablePlayersData = AvailablePlayersPacketBufHandler.readBuf(buf);

            if(client.player != null) {
                DebugPieItem.showAvailablePlayers(client.player, availablePlayersData);
            }
            else {
                LinkageMod.LOGGER.warn("Received available_players_data packet has no player info.");
            }
        });

    }
}
