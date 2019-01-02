package pjc21.mod.util.handlers;

import net.minecraftforge.fluids.FluidRegistry;
import pjc21.mod.init.FluidInit;
import pjc21.mod.objects.fluids.FluidBase;
import pjc21.mod.objects.fluids.FluidMaterials;
import pjc21.mod.objects.fluids.block.BlockFluidOil;

public class FluidHandler 
{

	public static void registerFluids() 
	{
        for (final FluidBase fluid : FluidInit.FLUIDS)
        {
            FluidRegistry.registerFluid(fluid);
            if ((fluid).isBucketEnabled())
            {
                FluidRegistry.addBucketForFluid(fluid);
            }
            
        }
        registerFluidBlocks();
	}
	
	public static void registerFluidBlocks()
	{
		FluidInit.BLOCK_OIL = new BlockFluidOil(FluidInit.OIL, FluidMaterials.OIL);
	}
}
