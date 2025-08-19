package backcore.levels;

import backcore.BackcoreMain;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;
import java.util.Random;

public abstract class LevelGeneration48x48 {

    protected int timer = 40;

    protected abstract String[] getTemplates();

    protected abstract String getLevelID();

    protected String getLevelTemplateGenerationFunctionID() {
        return "backcore:room_generate";
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {

        if (event.getEntity().level().dimension().location().toString().equals(getLevelID())) {
            // 生存模式禁区
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL) {
                    if (serverPlayer.getY() >= 25) {
                        event.getEntity().teleportTo(event.getEntity().getX(), 19F, event.getEntity().getZ());
                    } else if (serverPlayer.getY() <= 0) {
                        event.getEntity().teleportTo(event.getEntity().getX(), 1F, event.getEntity().getZ());
                    }
                }
            }

            // 触发地形生成


            if (event.getEntity().level() instanceof ServerLevel level) {
                this.timer--;
                if (this.timer <= 0) {
                    Vec3 pos = event.getEntity().getPosition(0);
                    int target_x = Math.floorDiv((int) pos.x, 48) * 48;
                    int target_z = Math.floorDiv((int) pos.z, 48) * 48;
                    for (int dx = -48; dx <= 48; dx += 48) {
                        for (int dz = -48; dz <= 48; dz += 48) {
                            if (level.isLoaded(new BlockPos(target_x + dx, 59, target_z + dz)) && level.isLoaded(new BlockPos(target_x + dx + 47, 59, target_z + dz + 47))) {
                                BlockPos p = new BlockPos(target_x + dx, 59, target_z + dz);
                                level.setBlock(new BlockPos(target_x + dx, 59, target_z + dz), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
                                level.blockUpdated(new BlockPos(target_x + dx, 59, target_z + dz), Blocks.REDSTONE_BLOCK);
                            }
                            //                            else {
                            //                                BackcoreMain.LOGGER.info("Level1Generation generation prevented");
                            //                            }
                        }
                    }
                    this.timer = 20;
                }


            }
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        onGeneratingChunk(Objects.requireNonNull(event.getChunk().getLevel()), event.getChunk());
    }

    private void onGeneratingChunk(Level l, ChunkAccess c) {
        if (l.dimension().location().toString().equals(getLevelID())) {
            if (c.getPos().x % 3 == 0 && c.getPos().z % 3 == 0) {

                int chunk_x_mod = Math.floorDiv(c.getPos().x, 3);
                int chunk_z_mod = Math.floorDiv(c.getPos().z, 3);
                int x_min = c.getPos().getMinBlockX();
                int z_min = c.getPos().getMinBlockZ();


                // (0, 0) 固定生成
                if (l instanceof ServerLevel level) {
                    if (chunk_x_mod == 0 && chunk_z_mod == 0) {
                        placeTemplateCommandBlock(level, c, x_min, z_min, getTemplates()[0]);
                    } else {
                        int index = randomIntFromChunk(level.getSeed(), 0, getTemplates().length, chunk_x_mod, chunk_z_mod);
                        placeTemplateCommandBlock(level, c, x_min, z_min, getTemplates()[index]);
                    }
                }
            }
        }
    }

    public static int randomIntFromChunk(long seed, int min, int max, int x, int z) {
        Random random = new Random(seed + 114514L * x + 1145141L * z);
        return random.nextInt(min, max);
    }

    private void placeTemplateCommandBlock(ServerLevel l, ChunkAccess c, int x, int z, String template_name) {
        BlockState commandBlock = Blocks.REPEATING_COMMAND_BLOCK.defaultBlockState();
        CommandBlockEntity commandBlockEntity = new CommandBlockEntity(new BlockPos(x, 60, z), commandBlock);
        commandBlockEntity.getCommandBlock().setCommand("function "+getLevelTemplateGenerationFunctionID()+" {\"template\":\""+template_name+"\"}");
//        commandBlockEntity.setAutomatic(true);

        c.setBlockState(new BlockPos(x, 60, z), commandBlockEntity.getBlockState(), false);
        c.setBlockEntity(commandBlockEntity);
//        l.getServer().execute(()->{
//            if (l.isLoaded(commandBlockEntity.getBlockPos())) {
//                BackcoreMain.LOGGER.info("bro thinks its loaded");
//                if (!l.getBlockState(commandBlockEntity.getBlockPos()).is(Blocks.BEDROCK)) {
//                    l.blockUpdated(commandBlockEntity.getBlockPos(), commandBlockEntity.getBlockState().getBlock());
//                }
//            } else {
//                BackcoreMain.LOGGER.info("mojang is doing what");
//            }
//        });
    }
}
