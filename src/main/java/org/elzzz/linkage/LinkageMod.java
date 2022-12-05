package org.elzzz.linkage;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import org.elzzz.linkage.register.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LinkageMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("linkage");
	public static final String MOD_ID = "linkage";
	public static HashMap<String, PlayerEntity> availablePlayers = new HashMap<>();
	// TODO Setup networking so client can access server's availablePlayers instance. Now it's not working on dedicated server.

	@Override
	public void onInitialize() {
		ModItems.register();

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			availablePlayers.remove(handler.player.getUuidAsString());
		});
	}
}
