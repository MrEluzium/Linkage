package org.elzzz.linkage.client;

import net.fabricmc.api.ClientModInitializer;
import org.elzzz.linkage.networking.ModNetworking;

import java.util.HashMap;
import java.util.UUID;

public class LinkageClient implements ClientModInitializer {
    public static HashMap<UUID, String> clientAvailablePlayers = new HashMap<>(); // Contains players id and dimension
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModNetworking.registerS2CPackets();
    }
}
