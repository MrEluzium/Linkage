package org.elzzz.linkage;

import it.unimi.dsi.fastutil.Hash;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import org.elzzz.linkage.register.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LinkageMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("linkage");
	public static final String MOD_ID = "linkage";
	public static HashMap<String, PlayerEntity> availablePlayers = new HashMap<String, PlayerEntity>();

	@Override
	public void onInitialize() {
		ModItems.register();
	}
}
