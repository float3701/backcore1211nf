package backcore.blocks;

import backcore.BackcoreBlocks;
import backcore.BackcoreConfig;
import backcore.BackcoreMain;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExtractorBlockEntity extends BaseContainerBlockEntity {

    public int remainingTicks;
    public final NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

    public static final BlockCapability<IItemHandler, Void> EXTRACTOR_ITEM_HANDLER = BlockCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath("backcore", "extractor_item_handler"),
            IItemHandler.class
    );

//    public static class ExtractorItemHandler extends InvWrapper {
//        public ExtractorItemHandler(Container inv) {
//            super(inv);
//        }
//    }

    public ExtractorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public ExtractorBlockEntity(BlockPos pos, BlockState blockState) {
        this(BackcoreMain.EXTRACTOR_BLOCK_ENTITY_TYPE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("remainingTicks", remainingTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
        remainingTicks = tag.getInt("remainingTicks");
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.backcore.extractor");
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> nonNullList) {

    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }


    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return true;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof ExtractorBlockEntity e) {
            e.remainingTicks--;
            if (e.remainingTicks <= 0) {
                e.remainingTicks = BackcoreConfig.EXTRACTOR_RANGE.getAsInt();
                if (level instanceof ServerLevel serverLevel) {
                    if (level.getBlockState(pos.below()).is(BackcoreBlocks.CONCRETE_SUPPLIES_BLOCK.get()) || level.getBlockState(pos.below().below()).is(BackcoreBlocks.CONCRETE_SUPPLIES_BLOCK.get())) {
                        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("backcore:blocks/extractor")));

                        LootParams.Builder builder = new LootParams.Builder(serverLevel);
                        builder.create(LootContextParamSets.EMPTY);

                        ObjectArrayList<ItemStack> items = lootTable.getRandomItems(builder.create(LootContextParamSets.EMPTY));

                        for (ItemStack itemStack : items) {
                            BackcoreMain.LOGGER.info("ExtractorBlockEntity.tick: " + itemStack + Capabilities.ItemHandler.BLOCK.getCapability(level, pos, state, blockEntity, Direction.DOWN));
//                            ItemHandlerHelper.insertItem(e.itemHandler, itemStack, false);
                            ItemHandlerHelper.insertItem(Capabilities.ItemHandler.BLOCK.getCapability(level, pos, state, blockEntity, null), itemStack, false);
//                            HopperBlockEntity.addItem(null, e, itemStack, Direction.DOWN);
                        }
                    }
                }
            }
        }
    }

//    public static boolean addItem(List<ItemStack> container, ItemStack itemStack) {
//        int amount = itemStack.getCount();
//        Item item = itemStack.getItem();
//        for (int i = 0; i < container.size(); i++) {
//            // 合并
//            if (container.get(i).is(item)) {
//                if (container.get(i).getCount() + amount > item.getMaxStackSize(container.get(i))) {
//                    // 超出堆叠
//                    amount -= item.getMaxStackSize(container.get(i)) - container.get(i).getCount();
//                    container.get(i).setCount(item.getMaxStackSize(container.get(i)));
//                } else {
//                    container.get(i).setCount(container.get(i).getCount() + amount);
//                    return true;
//                }
//            }
//
//            // 空位
//            if (container.get(i).isEmpty()) {
//                container.set(i, itemStack);
//                return true;
//            }
//        }
//        return false;
//    }
}
