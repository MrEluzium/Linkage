package org.elzzz.linkage.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.data.PlayerData;
import org.elzzz.linkage.item.DebugPieItem;
import org.elzzz.linkage.networking.ModNetworking;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

import java.util.HashMap;
import java.util.UUID;

public class LinkageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModNetworking.registerS2CPackets();
    }
}
