package backcore.levels;

import backcore.BackcoreBlocks;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.event.level.ChunkEvent;

public class Level1Generation extends LevelGeneration48x48{

    public static String[] templates = new String[]{
            "backcore:l1/0",
            "backcore:l1/1",
            "backcore:l1/2",
            "backcore:l1/3",
            "backcore:l1/4",
            "backcore:l1/6",
            "backcore:l1/7",
            "backcore:l1/8"
    };

    @Override
    protected String[] getTemplates() {
        return templates;
    }

    @Override
    protected String getLevelID() {
        return "backcore:level1";
    }

    @Override
    public void onChunkLoad(ChunkEvent.Load event) {
        super.onChunkLoad(event);
//        if (event.getChunk().getPos().x == 1 && event.getChunk().getPos().z == 1) {
//            event.getChunk().setBlockState(new BlockPos(29, 18, 27), BackcoreBlocks.CONCRETE_BLOCK.get().defaultBlockState(), true);
//        }
    }
}
