package pjc21.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import pjc21.mod.objects.fluids.FluidBase;
import pjc21.mod.objects.fluids.fluid.FluidOil;
import pjc21.mod.util.Reference;


public class FluidInit 
{
	public static final List<FluidBase> FLUIDS = new ArrayList<FluidBase>();

	//Fluids
	public static final FluidBase OIL = new FluidOil("oil", new ResourceLocation(Reference.MODID, "blocks/oil_still"), new ResourceLocation(Reference.MODID, "blocks/oil_flow"));

	//BlockFluids
	public static Block BLOCK_OIL = null;
	
}
