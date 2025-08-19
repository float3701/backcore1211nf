package backcore.items;

import backcore.utils.DimensionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TeleportItem_2_1 extends AbstractTeleportItem {

    public TeleportItem_2_1(Properties properties) {
        super(properties);
    }

    @Override
    public void teleport(Level level, Player player) {
        level.playSound(player, player.blockPosition(), SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
        if (level.dimension().location().toString().equals("backcore:level2")) {
            player.playSound(SoundEvents.PLAYER_TELEPORT);
            if (player instanceof ServerPlayer) {
                player.teleportTo(DimensionHelper.getLevel(player.getServer(), "backcore", "level1"), 29.5, 18, 27.5, RelativeMovement.ALL, 0, 0);
                ((ServerPlayer) player).setRespawnPosition(DimensionHelper.getLevel(player.getServer(), "backcore", "level1").dimension(), new BlockPos(29, 18, 27), 0, true, false);
            }
        } else {
            player.playSound(SoundEvents.ENDER_EYE_DEATH);
            if (player instanceof ServerPlayer) {
                player.sendSystemMessage(Component.translatable("message.backcore.teleport_fail_level2"));
            }
        }
    }
}
