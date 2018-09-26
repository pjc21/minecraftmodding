package pjc21.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import pjc21.mod.objects.fluids.FluidBase;
import pjc21.mod.objects.fluids.FluidOil;
import pjc21.mod.util.Reference;

public class FluidInit 
{
	public static final List<FluidBase> FLUIDS = new ArrayList<FluidBase>();
	//public static final List<Block> FLUIDBLOCKS = new ArrayList<Block>();
	
	//Fluids
	public static final Fluid OIL = new FluidOil("oil", new ResourceLocation(Reference.MODID, "oil_still"), new ResourceLocation(Reference.MODID, "oil_flow"));
	
	/*public static final FluidBase OIL = (FluidBase) new FluidBase("oil", new ResourceLocation(Reference.MODID, "oil_still"), new ResourceLocation(Reference.MODID, "oil_flow"))
			.setHasBucket(true)
			.setDensity(1000)
			.setGaseous(false)
			.setLuminosity(9)
			.setViscosity(25000)
			.setTemperature(300);*/
	
	//public static final Set<FluidBase> SET_FLUIDS = ImmutableSet.of(OIL);
	
	public static void registerFluids() 
	{
		// DEBUG
        System.out.println("Registering fluids");
        for (final FluidBase fluid : FLUIDS)
        {
            FluidRegistry.registerFluid(fluid);
            if ((fluid).isBucketEnabled())
            {
                FluidRegistry.addBucketForFluid(fluid);
            }
            // DEBUG
            System.out.println("Registering fluid: " + fluid.getName()+" with bucket = "+(fluid).isBucketEnabled());
        }

	}
	
}
