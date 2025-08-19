package backcore.datagen;

import backcore.BackcoreBlocks;
import backcore.BackcoreItems;
import backcore.BackcoreMain;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class BackcoreItemModelProvider extends ItemModelProvider {


    public BackcoreItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredItem<Item> item : BackcoreItems.ALL_ITEMS) {
            BackcoreMain.LOGGER.info("Registering item model for {}", item.getId());
            this.basicItem(item.get());
        }

        for (DeferredBlock<? extends Block> block : BackcoreBlocks.SIMPLE_MODEL_BLOCKS) {
            BackcoreMain.LOGGER.info("Registering simple block item model for {}", block.getId());
            this.simpleBlockItem(block.get());
        }

        for (DeferredBlock<? extends Block> block : BackcoreBlocks.PILLAR_MODEL_BLOCKS) {
            BackcoreMain.LOGGER.info("Registering pillar block item model for {}", block.getId());
            this.simpleBlockItem(block.get());
        }
    }

}
