package backcore;

import backcore.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class BackcoreBlocks {
    // 方块组、物品组、创造模式物品栏
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BackcoreMain.MODID);
    // 方块与对应物品形式
    public static final DeferredBlock<Block> CARPET_BLOCK = BLOCKS.registerSimpleBlock("carpet_block", Block.Properties.of().sound(SoundType.WOOL).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> WALLPAPER_BLOCK = BLOCKS.registerSimpleBlock("wallpaper_block", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> CEILING_BLOCK = BLOCKS.registerSimpleBlock("ceiling_block", Block.Properties.of().sound(SoundType.DEEPSLATE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> LAMP_BLOCK = BLOCKS.registerSimpleBlock("lamp_block", Block.Properties.of().sound(SoundType.GLASS).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false).lightLevel((state)->12));
    public static final DeferredBlock<Block> WALLPAPER_BLOCK_2 = BLOCKS.registerSimpleBlock("wallpaper_block_2", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> CONCRETE_BLOCK = BLOCKS.registerSimpleBlock("concrete", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> CONCRETE_SUPPLIES_BLOCK = BLOCKS.registerSimpleBlock("concrete_supplies", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> WALL_BLOCK = BLOCKS.registerSimpleBlock("wall", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> LEVEL1_LIGHT_CONTROLLER_BLOCK = BLOCKS.registerBlock("level1_light_controller", Level1LightControllerBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> EXTRACTOR_BLOCK = BLOCKS.registerBlock("extractor", ExtractorBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> CONCRETE_CEILING_BLOCK = BLOCKS.registerSimpleBlock("concrete_ceiling", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> RUSTED_WALL_BLOCK = BLOCKS.registerBlock("rusted_wall", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> WALL_DECORATED1_BLOCK = BLOCKS.registerBlock("wall_decorated1", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> WALL_DECORATED2_BLOCK = BLOCKS.registerBlock("wall_decorated2", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> WALL_DECORATED3_BLOCK = BLOCKS.registerBlock("wall_decorated3", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> RUSTED_CONCRETE_BLOCK = BLOCKS.registerBlock("rusted_concrete", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> CONCRETE_DECORATED1_BLOCK = BLOCKS.registerBlock("concrete_decorated1", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> CONCRETE_DECORATED2_BLOCK = BLOCKS.registerBlock("concrete_decorated2", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<RotatedPillarBlock> CONCRETE_DECORATED3_BLOCK = BLOCKS.registerBlock("concrete_decorated3", RotatedPillarBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> BOX1_BLOCK = BLOCKS.registerSimpleBlock("box1", Block.Properties.of().sound(SoundType.STONE).strength(2.5F, 1.0F).isValidSpawn((state, level, pos, type) -> false));
    public static final DeferredBlock<Block> WALL_STEEL_BLOCK = BLOCKS.registerSimpleBlock("wall_steel", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> WALL_STEEL_BLUE_BLOCK = BLOCKS.registerSimpleBlock("wall_steel_blue", Block.Properties.of().sound(SoundType.STONE).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> TANK_BLOCK = BLOCKS.registerBlock("tank", TankBlock::new, Block.Properties.of().sound(SoundType.STONE).strength(1.0F, 3600000.0F));


    // 特殊方块
    public static final DeferredBlock<ControllableLightSourceTransparentBlock> CONTROLLABLE_LAMP_BLOCK = BLOCKS.registerBlock("controllable_lamp", ControllableLampBlock::new, BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.0F, 0.5F));
    public static final DeferredBlock<ControllableCeilingLampBlock> CONTROLLABLE_CEILING_LAMP_BLOCK = BLOCKS.registerBlock("controllable_ceiling_lamp", ControllableCeilingLampBlock::new, BlockBehaviour.Properties.of().strength(3.0F, 0.5F).noCollission());
    public static final DeferredBlock<ControllableLongLampBlock> CONTROLLABLE_LONG_LAMP_BLOCK = BLOCKS.registerBlock("controllable_long_lamp", ControllableLongLampBlock::new, BlockBehaviour.Properties.of().strength(3.0F, 0.5F));

    // 列表
    // 自动生成：对应物品注册
    // 自动生成：创造模式列表
    public static final List<DeferredBlock<? extends Block>> ALL_BLOCKS = List.of(
            CARPET_BLOCK,
            WALLPAPER_BLOCK,
            CEILING_BLOCK,
            LAMP_BLOCK,
            WALLPAPER_BLOCK_2,
            CONCRETE_BLOCK,
            CONCRETE_SUPPLIES_BLOCK,
            WALL_BLOCK,
            EXTRACTOR_BLOCK,
            LEVEL1_LIGHT_CONTROLLER_BLOCK,
            CONTROLLABLE_LAMP_BLOCK,
            CONTROLLABLE_CEILING_LAMP_BLOCK,
            CONTROLLABLE_LONG_LAMP_BLOCK,
            CONCRETE_CEILING_BLOCK,
            RUSTED_WALL_BLOCK,
            WALL_DECORATED1_BLOCK,
            WALL_DECORATED2_BLOCK,
            WALL_DECORATED3_BLOCK,
            RUSTED_CONCRETE_BLOCK,
            CONCRETE_DECORATED1_BLOCK,
            CONCRETE_DECORATED2_BLOCK,
            CONCRETE_DECORATED3_BLOCK,
            BOX1_BLOCK,
            WALL_STEEL_BLOCK,
            WALL_STEEL_BLUE_BLOCK,
            TANK_BLOCK
    );

    // 自动生成：六面相同模型
    // 自动生成：物品模型
    public static final List<DeferredBlock<? extends Block>> SIMPLE_MODEL_BLOCKS = List.of(
            CARPET_BLOCK,
            WALLPAPER_BLOCK,
            CEILING_BLOCK,
            LAMP_BLOCK,
            WALLPAPER_BLOCK_2,
            CONCRETE_BLOCK,
            CONCRETE_SUPPLIES_BLOCK,
            WALL_BLOCK,
            LEVEL1_LIGHT_CONTROLLER_BLOCK,
            CONCRETE_CEILING_BLOCK,
            BOX1_BLOCK,
            WALL_STEEL_BLOCK,
            WALL_STEEL_BLUE_BLOCK,
            TANK_BLOCK
    );

    // 自动生成：_side _end 柱子模型
    // 自动生成：物品模型
    public static final List<DeferredBlock<? extends RotatedPillarBlock>> PILLAR_MODEL_BLOCKS = List.of(
            RUSTED_WALL_BLOCK,
            WALL_DECORATED1_BLOCK,
            WALL_DECORATED2_BLOCK,
            WALL_DECORATED3_BLOCK,
            RUSTED_CONCRETE_BLOCK,
            CONCRETE_DECORATED1_BLOCK,
            CONCRETE_DECORATED2_BLOCK,
            CONCRETE_DECORATED3_BLOCK
    );

    public static void registerBlockItems() {
        for (DeferredBlock<? extends Block> block : ALL_BLOCKS) {
            BackcoreItems.ITEMS.registerSimpleBlockItem(block);
        }
    }

}
