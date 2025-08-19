package backcore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ControllableLampBlock extends ControllableLightSourceTransparentBlock {

    public ControllableLampBlock(Properties properties) {
        super(properties.lightLevel((state) -> (state.getValue(BlockStateProperties.LIT) ? 15 : 0)));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 2, 16, 4, 14);
    }
}
