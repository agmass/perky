package org.agmas.client.datagen;

//? if >=1.18 {
//? if >=26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
 //? } else if >1.18 {
/*import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
*///? } else {
/*import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
 *///? }
//? if >=1.21.4 {
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
//? } else {
/*import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
*///? }
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import org.agmas.init.ModBlocks;
import org.agmas.init.ModComponents;
import org.agmas.init.ModItems;
import org.agmas.porting.PPIdentifier;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class PerkyModelDatagen extends FabricModelProvider {

    //? if >=26.1 {
    public PerkyModelDatagen(FabricPackOutput output) {
        super(output);
    }
    //? } else if >1.18 {
    /*public PerkyModelDatagen(FabricDataOutput output) {
        super(output);
    }
    *///? } else {
    /*public PerkyModelDatagen(FabricDataGenerator output) {
        super(output);
    }
    *///? }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.GRIDDIAN_TILES);
        blockModelGenerators.createTrivialCube(ModBlocks.KNUCKLIUM_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        bloodFilledItem(itemModelGenerator, ModItems.SYRINGE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModBlocks.CONSOLE.asItem(), ModelTemplates.FLAT_ITEM);
    }
    public void bloodFilledItem(ItemModelGenerators itemModelGenerator, Item item, ModelTemplate template) {


        //? if >1.21.1 {
        
        PPIdentifier world_id = new PPIdentifier(itemModelGenerator.createFlatItemModel(item, template));
        PPIdentifier filled_id = new PPIdentifier(template.create(
                world_id.id.withPath(world_id.id.getPath() + "_filled"),
                TextureMapping.layer0(TextureMapping.getItemTexture(item, "_filled")),
                itemModelGenerator.modelOutput
        ));
        itemModelGenerator.itemModelOutput.accept(item,
                ItemModelUtils.conditional(
                        ItemModelUtils.hasComponent(ModComponents.BLOOD_COMPONENT.COMPONENT_FORM),
                        ItemModelUtils.plainModel(filled_id.id),
                        ItemModelUtils.plainModel(world_id.id)
                )
        );
        //? } else {
        /*PPIdentifier filled_id = new PPIdentifier(template.create(
                ModelLocationUtils.getModelLocation(item).withPath(ModelLocationUtils.getModelLocation(item).getPath() + "_filled"),
                TextureMapping.layer0(TextureMapping.getItemTexture(item, "_filled")),
                itemModelGenerator.output
        ));

        itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(item), ()->{
            JsonObject jsonObject = template.createBaseTemplate(ModelLocationUtils.getModelLocation(item), Map.of(TextureSlot.LAYER0, ModelLocationUtils.getModelLocation(item)));

            JsonObject bloodOverride = new JsonObject();
            JsonObject predicate = new JsonObject();

            predicate.addProperty("perky:filled_with_blood", 1);
            bloodOverride.add("predicate", predicate);

            bloodOverride.addProperty("model", filled_id.id.toString());

            JsonArray array = new JsonArray();
            array.add(bloodOverride);
            jsonObject.add("overrides", array);
            return jsonObject;
        });
        *///? }
    }
}
//? }
