package backcore.blocks;

import backcore.BackcoreMain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class ControllableLightSourceBlock extends Block implements ControllableLightSource {


    public ControllableLightSourceBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0));
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
