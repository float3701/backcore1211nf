package backcore;

import backcore.blocks.ExtractorBlockEntity;
import backcore.blocks.Level1LightControllerBlockEntity;
import backcore.datagen.BackcoreBlockStateProvider;
import backcore.datagen.BackcoreItemModelProvider;
import backcore.levels.Level0;
import backcore.levels.Level1Generation;
import backcore.levels.Level2Generation;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Mod(BackcoreMain.MODID)
public class BackcoreMain {
    public static final String MODID = "backcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    // 方块实体
    public static final Supplier<BlockEntityType<Level1LightControllerBlockEntity>> LEVEL1_LIGHT_CONTROLLER_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register(
            "level1_light_controller_block_entity",
            () -> BlockEntityType.Builder.of(
                            Level1LightControllerBlockEntity::new,
                            BackcoreBlocks.LEVEL1_LIGHT_CONTROLLER_BLOCK.get()
                    )
                    .build(null)
    );
    public static final Supplier<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register(
            "extractor_entity",
            () -> BlockEntityType.Builder.of(
                            ExtractorBlockEntity::new,
                            BackcoreBlocks.EXTRACTOR_BLOCK.get()
                    )
                    .build(null)
    );

    // 实体(实在是没查到neoforge里的注册方式)
    // BuiltInRegistries <- Registries
    // ResourceLocation <- Identifier
    // EntityType.Builder <- FabricEntityTypeBuilder
//    public static final EntityType<DullerEntityType> DULLER_ENTITY = Registry.register(
//            BuiltInRegistries.ENTITY_TYPE,
//            ResourceLocation.fromNamespaceAndPath("backcore", "duller"),
//            EntityType.Builder.of(DullerEntityType::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build("duller")
//    );


    // 物品组
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BACKCORE_MODE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.backcore")) //The language key for the title of your CreativeModeTab
            .icon(() -> BackcoreBlocks.CONTROLLABLE_LAMP_BLOCK.get().asItem().getDefaultInstance())
            .displayItems((parameters, output)->{
                // 向物品组内添加物品
                for(DeferredItem<? extends Item> item : BackcoreItems.ALL_ITEMS) {
                    output.accept(item.get());
                }

                for(DeferredBlock<? extends Block> block : BackcoreBlocks.ALL_BLOCKS) {
                    output.accept(block.get());
                }
            })
            .build()
    );


    public BackcoreMain(IEventBus modEventBus, ModContainer modContainer) {
        initItemsAndBlocks(modEventBus);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::gatherData);

        modContainer.registerConfig(ModConfig.Type.SERVER, BackcoreConfig.SPEC);

        // level0事件
        NeoForge.EVENT_BUS.addListener(Level0::chunkEvent);
        NeoForge.EVENT_BUS.addListener(Level0::playerTickEvent);

        // level1 - level2 地形生成
        Level1Generation level1Generation = new Level1Generation();
        NeoForge.EVENT_BUS.addListener(level1Generation::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(level1Generation::onChunkLoad);

        Level2Generation level2Generation = new Level2Generation();
        NeoForge.EVENT_BUS.addListener(level2Generation::onChunkLoad);
        NeoForge.EVENT_BUS.addListener(level2Generation::onPlayerTick);
    }

    @SubscribeEvent
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
//        event.registerBlock(
//                Capabilities.ItemHandler.BLOCK,
//                (level, pos, state, be, side) -> {
//                    if (be instanceof ExtractorBlockEntity) {
//                        BackcoreMain.LOGGER.info("yes");
//                        return ((ExtractorBlockEntity) be).itemHandler;
//                    }
//                    BackcoreMain.LOGGER.info("what");
//                    return null;
//                },
//                BackcoreBlocks.EXTRACTOR_BLOCK.get());
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BackcoreMain.EXTRACTOR_BLOCK_ENTITY_TYPE.get(),
                (be, side) -> new InvWrapper(be)
        );
    }

    // 数据生成 [[NeoForge1.21.1教程]]
    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Register the provider.
        generator.addProvider(
                event.includeClient(),
                new BackcoreBlockStateProvider(output, MODID, existingFileHelper)
        );
        generator.addProvider(
                // A boolean that determines whether the data should actually be generated.
                // The event provides methods that determine this:
                // event.includeClient(), event.includeServer(),
                // event.includeDev() and event.includeReports().
                // Since recipes are server data, we only run them in a server datagen.
                event.includeClient(),
                // Our provider.
                new BackcoreItemModelProvider(output, MODID, existingFileHelper)
        );
    }

    private void initItemsAndBlocks(IEventBus modEventBus) {
        LOGGER.info("Registering Backcore items and blocks");
        BackcoreBlocks.BLOCKS.register(modEventBus);
        BackcoreBlocks.registerBlockItems();
        BackcoreItems.ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }


}
