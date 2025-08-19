package backcore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ControllableCeilingLampBlock extends ControllableLightSourceTransparentBlock {

    public ControllableCeilingLampBlock(Properties properties) {
        super(properties.lightLevel((state) -> (state.getValue(BlockStateProperties.LIT) ? 10 : 0)));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(6, 15, 6, 10, 16, 10);
    }
}
