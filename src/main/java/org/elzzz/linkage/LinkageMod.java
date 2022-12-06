package org.elzzz.linkage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.elzzz.linkage.event.ModEvents;
import org.elzzz.linkage.register.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LinkageMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Linkage");
	public static final String MOD_ID = "linkage";
	public static final Identifier availablePlayersPacketIdentifier = new Identifier(MOD_ID, "available_players_data");
	public static HashMap<String, PlayerEntity> availablePlayers = new HashMap<>();

	@Override
	public void onInitialize() {
		ModItems.register();
		ModEvents.register();
	}
}
