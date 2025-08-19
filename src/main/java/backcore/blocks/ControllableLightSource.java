package backcore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface ControllableLightSource {
    public static void LightOn(BlockState state, LevelAccessor level, BlockPos pos) {}
    public static void LightOff(BlockState state, LevelAccessor level, BlockPos pos) {}
}
