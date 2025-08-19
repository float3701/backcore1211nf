package backcore.datagen;

import backcore.BackcoreBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BackcoreBlockStateProvider extends BlockStateProvider {
    public BackcoreBlockStateProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (DeferredBlock<? extends Block> block : BackcoreBlocks.SIMPLE_MODEL_BLOCKS) {
            this.simpleBlock(block.get());
        }
        for (DeferredBlock<? extends RotatedPillarBlock> block : BackcoreBlocks.PILLAR_MODEL_BLOCKS) {
            this.axisBlock(block.get());
        }
    }
}
