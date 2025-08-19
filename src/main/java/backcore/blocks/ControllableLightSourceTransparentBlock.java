package backcore.blocks;

import backcore.BackcoreMain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class ControllableLightSourceTransparentBlock extends TransparentBlock implements ControllableLightSource {


    public ControllableLightSourceTransparentBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.LIT, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LIT);
    }

    public static void LightOn(BlockState state, LevelAccessor level, BlockPos pos) {
        level.setBlock(pos, state.setValue(BlockStateProperties.LIT, true), 11);
        level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
    }

    public static void LightOff(BlockState state, LevelAccessor level, BlockPos pos) {
        level.setBlock(pos, state.setValue(BlockStateProperties.LIT, false), 11);
        level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
    }
}
