package backcore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ControllableLongLampBlock extends ControllableLightSourceTransparentBlock {

    public ControllableLongLampBlock(Properties properties) {
        super(properties.lightLevel((state) -> (state.getValue(BlockStateProperties.LIT) ? 15 : 0)));
        registerDefaultState(this.stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();

        if (direction.getAxis().isHorizontal()) {
            Direction direction1 = direction.getOpposite();
            blockstate = (BlockState)blockstate.setValue(HorizontalDirectionalBlock.FACING, direction1);
            if (blockstate.canSurvive(levelreader, blockpos)) {
                return blockstate;
            }
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HorizontalDirectionalBlock.FACING) == Direction.NORTH || state.getValue(HorizontalDirectionalBlock.FACING) == Direction.SOUTH)
            return Block.box(6, 14, 0, 10, 16, 16);
        else return Block.box(0, 14, 6, 16, 16, 10);
    }
}
