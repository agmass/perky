package org.agmas;

import net.fabricmc.api.ModInitializer;
import org.agmas.init.ModAttachments;
import org.agmas.init.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Perky implements ModInitializer {
	public static final String MOD_ID = "perky";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModAttachments.init();
	}
}
