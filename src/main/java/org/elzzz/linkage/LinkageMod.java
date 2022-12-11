package org.elzzz.linkage;

import net.fabricmc.api.ModInitializer;
import org.elzzz.linkage.register.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkageMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("linkage");
	public static final String MOD_ID = "linkage";

	@Override
	public void onInitialize() {
		ModItems.register();
	}
}
