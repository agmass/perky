package org.agmas.init;


import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
//? if <=1.19
//import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.agmas.block.ConsoleBlock;
import org.agmas.block.ConsoleParentBlock;
import org.agmas.porting.PPRegistrar;

public class ModBlocks {
    public static final Block CONSOLE_CHILD = PPRegistrar.registerBlock(
            "console_child",
            ConsoleBlock::new,
            BlockBehaviour.Properties.of(
                        //? if <=1.19
                            //Material.STONE
                    )
                    .sound(SoundType.LODESTONE).destroyTime(9f).lightLevel((l)->3).noOcclusion(),
            false,
            CreativeModeTabs.FUNCTIONAL_BLOCKS
    );

    public static final Block CONSOLE = PPRegistrar.registerBlock(
            "console",
            ConsoleParentBlock::new,
            BlockBehaviour.Properties.of(
                    //? if <=1.19
                            //Material.STONE
                    )
                    .sound(SoundType.LODESTONE).destroyTime(9f).lightLevel((l)->3).noOcclusion(),
            true,
            CreativeModeTabs.FUNCTIONAL_BLOCKS
    );

    public static final Block KNUCKLIUM_BLOCK = PPRegistrar.registerBlock(
            "knucklium_block",
            Block::new,
            BlockBehaviour.Properties.of(
                            //? if <=1.19
                            //Material.STONE
                    )
                    //? if <1.21.11
                    //.sound(SoundType.STONE)
                    //? if >=1.21.11
                    .sound(SoundType.IRON)
                    .requiresCorrectToolForDrops().strength(5.0f, 6.0f),
            true,
            CreativeModeTabs.BUILDING_BLOCKS
    );

    public static final Block GRIDDIAN_TILES = PPRegistrar.registerBlock(
            "griddian_tiles",
            Block::new,
            BlockBehaviour.Properties.of(
                            //? if <=1.19
                            //Material.STONE
                    )
                    .sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(5.0f, 6.0f),
            true,
            CreativeModeTabs.BUILDING_BLOCKS
    );

    public static void init() {}
}
