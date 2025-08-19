package backcore.blocks;

import backcore.BackcoreBlocks;
import backcore.BackcoreMain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class Level1LightControllerBlockEntity extends BlockEntity {

    public boolean lit;
    public int remainingTicks;
    public Level1LightControllerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BackcoreMain.LEVEL1_LIGHT_CONTROLLER_BLOCK_ENTITY_TYPE.get(), pos, blockState);
        if (remainingTicks <= 0) {
            remainingTicks = new Random().nextInt(6000, 12000);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("lit", lit);
        tag.putInt("remainingTicks", remainingTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        lit = tag.getBoolean("lit");
        remainingTicks = tag.getInt("remainingTicks");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof Level1LightControllerBlockEntity e) {
            e.remainingTicks--;
            if (e.remainingTicks <= 0) {
                e.toggleLight(level, pos);
            }
        }
    }

    public void onUseWithoutItem(Level level, BlockPos pos) {
        toggleLight(level, pos);
    }

    public void toggleLight(Level level, BlockPos origin) {
        if (lit) {
            BackcoreMain.LOGGER.info("Turning off light of level1 [Controller: {}]", origin);
            lightOff(level, origin.offset(0, -25, 0), origin.offset(48, 0, 48));
            lit = false;
            remainingTicks = new Random().nextInt(6000, 24000);
        } else {
            BackcoreMain.LOGGER.info("Turning on light of level1 [Controller: {}]", origin);
            lightOn(level, origin.offset(0, -25, 0), origin.offset(48, 0, 48));
            lit = true;
            remainingTicks = new Random().nextInt(12000, 36000);
        }
    }

    public static void lightOn(Level level, BlockPos posStart, BlockPos posEnd) {
        for (int x = posStart.getX(); x <= posEnd.getX(); x++) {
            for (int y = posStart.getY(); y <= posEnd.getY(); y++) {
                for (int z = posStart.getZ(); z <= posEnd.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = level.getBlockState(pos);
                    if (blockState.getBlock() instanceof ControllableLightSource) {
                        ControllableLightSourceBlock.LightOn(blockState, level, pos);
                    } else if (blockState.is(Blocks.SPAWNER)) {
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
        List<Zombie> zombies = level.getEntitiesOfClass(Zombie.class, new AABB(posStart.getX(), posStart.getY(), posStart.getZ(), posEnd.getX(), posEnd.getY(), posEnd.getZ()));
        for (Zombie zombie : zombies) {
            if (!zombie.isPersistenceRequired() && !zombie.requiresCustomPersistence()) {
                zombie.remove(Entity.RemovalReason.KILLED);
            }
        }
    }

    public static void lightOff(Level level, BlockPos posStart, BlockPos posEnd) {
        for (int x = posStart.getX(); x <= posEnd.getX(); x++) {
            for (int y = posStart.getY(); y <= posEnd.getY(); y++) {
                for (int z = posStart.getZ(); z <= posEnd.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = level.getBlockState(pos);
                    if (blockState.getBlock() instanceof ControllableLightSource) {
                        ControllableLightSourceBlock.LightOff(blockState, level, pos);
                    } else if (blockState.getBlock().equals(BackcoreBlocks.CONCRETE_SUPPLIES_BLOCK.get())) {
                        generateBoxesAndSpawners(level, new BlockPos(x, y, z));
                    }
                }
            }
        }


    }


    public static void generateBoxesAndSpawners(Level level, BlockPos supplyMarkPos) {
        RandomSource random = level.random;
        float r = random.nextFloat();
        BlockPos tryingPos = supplyMarkPos.above();
        if (r < 0.45) {
            // 生成资源箱，1-2层
            if (level.getBlockState(tryingPos).is(Blocks.AIR)) {
                level.setBlock(tryingPos, BackcoreBlocks.BOX1_BLOCK.get().defaultBlockState(), 3);
                if (random.nextFloat() < 0.3) {
                    tryingPos = tryingPos.above();
                    if (level.getBlockState(tryingPos).is(Blocks.AIR)) {
                        level.setBlock(tryingPos, BackcoreBlocks.BOX1_BLOCK.get().defaultBlockState(), 3);
                    }
                }
            }
        } else if (r < 0.5) {
            if (level.getBlockState(tryingPos).is(Blocks.AIR)) {
                SpawnerBlock spawnerBlock = (SpawnerBlock) Blocks.SPAWNER;
                BlockState spawnerBlockState = Blocks.SPAWNER.defaultBlockState();
                level.setBlock(tryingPos, spawnerBlockState, 3);
                if (level.getBlockEntity(tryingPos) instanceof SpawnerBlockEntity entity) {
                    entity.getSpawner().setEntityId(EntityType.ZOMBIE, level, level.random, tryingPos);
                }
            }
        } else {
            if (level.getBlockState(tryingPos).is(BackcoreBlocks.BOX1_BLOCK.get())) {
                level.setBlock(tryingPos, Blocks.AIR.defaultBlockState(), 3);
                tryingPos = tryingPos.above();
                if (level.getBlockState(tryingPos).is(BackcoreBlocks.BOX1_BLOCK.get())) {
                    level.setBlock(tryingPos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
            if (level.getBlockState(tryingPos).is(Blocks.SPAWNER)) {
                level.setBlock(tryingPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }
}
