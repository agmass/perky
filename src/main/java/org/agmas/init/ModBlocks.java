package org.agmas.init;


import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.agmas.porting.PPRegistrar;

public class ModBlocks {
    public static final Block CONSOLE = PPRegistrar.registerBlock(
            "console",
            Block::new,
            BlockBehaviour.Properties.of()
                    .sound(SoundType.LODESTONE).destroyTime(2f).lightLevel((l)->3),
            true,
            CreativeModeTabs.FUNCTIONAL_BLOCKS
    );

    public static void init() {}
}
