package backcore.levels;

import backcore.BackcoreBlocks;
import backcore.utils.DimensionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Teleports {
    public static void teleportToLevel0(Level origin_level, ServerPlayer player) {
        player.teleportTo(DimensionHelper.getLevel(player.getServer(), "backcore", "level0"), 0, 2, 0, RelativeMovement.ALL, 0, 0);
    }

    public static void teleportToLevel1(Level origin_level, ServerPlayer player) {
        player.teleportTo(DimensionHelper.getLevel(player.getServer(), "backcore", "level1"), 29.5, 18, 27.5, RelativeMovement.ALL, 0, 0);
        generateConcreteCapsule(DimensionHelper.getLevel(player.getServer(), "backcore", "level1"), new BlockPos(29, 17, 27));
        player.setRespawnPosition(DimensionHelper.getLevel(player.getServer(), "backcore", "level1").dimension(), new BlockPos(29, 18, 27), 0, true, false);
    }

    public static void teleportToLevel2(Level origin_level, ServerPlayer player) {
        player.teleportTo(DimensionHelper.getLevel(player.getServer(), "backcore", "level2"), 24, 8, 20, RelativeMovement.ALL, 0, 0);
        generateConcreteCapsule(DimensionHelper.getLevel(player.getServer(), "backcore", "level2"), new BlockPos(24, 7, 20));
        player.setRespawnPosition(DimensionHelper.getLevel(player.getServer(), "backcore", "level2").dimension(), new BlockPos(24, 8, 20), 0, true, false);
    }

    public static void generateConcreteCapsule(Level level, BlockPos pos_bottom) {
        int x = pos_bottom.getX();
        int y = pos_bottom.getY();
        int z = pos_bottom.getZ();
        placeConcrete(level, new BlockPos(x, y, z));
        placeConcrete(level, new BlockPos(x+1, y+1, z));
        placeConcrete(level, new BlockPos(x-1, y+1, z));
        placeConcrete(level, new BlockPos(x, y+1, z+1));
        placeConcrete(level, new BlockPos(x, y+1, z-1));
        placeConcrete(level, new BlockPos(x, y+3, z));
    }

    private static void placeConcrete(Level level, BlockPos pos) {
        level.setBlock(pos, BackcoreBlocks.CONCRETE_BLOCK.get().defaultBlockState(), 3);
    }
}
