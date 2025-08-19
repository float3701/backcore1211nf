package backcore.levels;

import backcore.BackcoreBlocks;
import backcore.BackcoreItems;
import backcore.BackcoreMain;
import backcore.utils.DimensionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Random;

public class Level0 {
    // 维度定义在数据包里面

    @SubscribeEvent
    public static void chunkEvent(ChunkEvent.Load event) {
        // 生成区块时添加灯
        if (event.isNewChunk()) {
            ChunkAccess c = event.getChunk();
            Level l = event.getChunk().getLevel();
            if (l==null) return;
            if (l.dimension().location().toString().equals("backcore:level0")) {
                onGeneratingChunkLevel0(l, c);
            }
        }
    }

    private static void onGeneratingChunkLevel0(Level l, ChunkAccess c) {
        int x = c.getPos().getMinBlockX();
        int z = c.getPos().getMinBlockZ();
        for (int xi = 0; xi < 16; xi += 4) {
            for (int zi = 0; zi < 16; zi += 4) {
                c.setBlockState(new BlockPos(x + xi, 4, z + zi), BackcoreBlocks.LAMP_BLOCK.get().defaultBlockState(), false);
            }
        }
    }

    @SubscribeEvent
    public static void playerTickEvent(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Vec3 player_pos = player.position();
            Level level = player.level();
            // 当玩家在主世界被沙子或沙砾掩埋时进入维度
            if (level.dimension() == Level.OVERWORLD && player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
                if (level.getBlockState(BlockPos.containing(player_pos.x, player_pos.y + 1.6, player_pos.z)).is(Blocks.SAND) || level.getBlockState(BlockPos.containing(player_pos.x, player_pos.y + 1.6, player_pos.z)).is(Blocks.GRAVEL)) {
                    BackcoreMain.LOGGER.info("Teleporting to level0...");
                    player.getInventory().clearContent();
                    player.getInventory().add(new ItemStack(BackcoreItems.ALMOND_WATER_BOTTLE_ITEM.get(), 6));
                    Random random = new Random();
                    ServerLevel lv0 = DimensionHelper.getLevel(level.getServer(), "backcore", "level0");
                    player.changeDimension(new DimensionTransition(lv0, new Vec3(random.nextDouble(-1024,1024), 100, random.nextDouble(-1024,1024)), new Vec3(0, 0, 0), 0, 0, true, DimensionTransition.PLACE_PORTAL_TICKET.then(DimensionTransition.PLAY_PORTAL_SOUND)));
                    player.setRespawnPosition(lv0.dimension(), BlockPos.containing(player_pos), 0, true, false);
                }
            }


            // 防出图装置
            BlockPos player_head_block_pos = BlockPos.containing(player_pos.add(new Vec3(0, +1.6, 0)));
            BlockPos player_stepping_block_pos = BlockPos.containing(player_pos.add(new Vec3(0, -1, 0)));
            Block stepping_block = level.getBlockState(player_stepping_block_pos).getBlock();
            Block head_block = level.getBlockState(player_head_block_pos).getBlock();
            if (player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
                if (
                        head_block.equals(BackcoreBlocks.CEILING_BLOCK.get()) ||
                                head_block.equals(BackcoreBlocks.LAMP_BLOCK.get()) ||
                                stepping_block.equals(BackcoreBlocks.CEILING_BLOCK.get()) ||
                                stepping_block.equals(BackcoreBlocks.LAMP_BLOCK.get())
                ) {
                    player.teleportTo(player_pos.x, player_pos.y - 0.5, player_pos.z);
                    player.resetFallDistance();
                }

                BlockPos player_feet_block_pos = BlockPos.containing(player_pos);
                Block feet_block = level.getBlockState(player_feet_block_pos).getBlock();
                if (
                        feet_block.equals(BackcoreBlocks.CARPET_BLOCK.get())
                ) {
                    player.teleportTo(player_pos.x, player_pos.y + 0.5, player_pos.z);
                }
            }
        }

    }
}
