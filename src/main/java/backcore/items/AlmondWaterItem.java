package backcore.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class AlmondWaterItem extends Item {
    public AlmondWaterItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }
}
