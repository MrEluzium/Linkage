package org.elzzz.linkage.client;

import net.fabricmc.api.ClientModInitializer;
import org.elzzz.linkage.networking.ModNetworking;

public class LinkageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModNetworking.registerS2CPackets();
    }
}
