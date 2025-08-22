package backcore.blocks;

import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class TankFluidHandler implements IFluidHandler {

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return new FluidStack(Fluids.WATER, Integer.MAX_VALUE);
    }

    @Override
    public int getTankCapacity(int i) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isFluidValid(int i, FluidStack fluidStack) {
        return true;
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        return 1000;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        return new FluidStack(Fluids.WATER, 1000);
    }

    @Override
    public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
        return new FluidStack(Fluids.WATER, i);
    }
}
