package org.elzzz.linkage.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.PlayerListEntry;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.item.DebugPieItem;

import java.util.HashMap;
import java.util.UUID;

public class LinkageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();

        /* available_players_data packet receiver */
        ClientPlayNetworking.registerGlobalReceiver(LinkageMod.availablePlayersPacketIdentifier, (client, handler, buf, responseSender) -> {
            HashMap<String, PlayerListEntry> availablePlayers = new HashMap<>();

            int size = buf.readVarInt();
            for(int i = 0; i < size; i++) {
                UUID current_uuid = buf.readUuid();
                availablePlayers.put(current_uuid.toString(), handler.getPlayerListEntry(current_uuid));
            }

            if(client.player != null) {
                DebugPieItem.showAvailablePlayers(client.player, availablePlayers);
            }
            else {
                LinkageMod.LOGGER.warn("Received available_players_data packet has no player info.");
            }
        });

    }
}
