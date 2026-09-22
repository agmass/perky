package org.agmas.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.agmas.client.datagen.PerkyLangDatagen;
import org.agmas.client.datagen.PerkyModelDatagen;import org.agmas.client.datagen.PerkyTagDatagen;

public class PerkyDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(PerkyLangDatagen::new);
		pack.addProvider(PerkyModelDatagen::new);
		pack.addProvider(PerkyTagDatagen.PerkyItemTags::new);
		pack.addProvider(PerkyTagDatagen.ItemTags::new);
	}
}
