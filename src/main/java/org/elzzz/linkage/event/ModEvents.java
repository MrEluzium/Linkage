package org.elzzz.linkage.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.elzzz.linkage.LinkageMod;

public class ModEvents {
    public static void register() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            LinkageMod.availablePlayers.remove(handler.player.getUuidAsString());
        });
    }
}
