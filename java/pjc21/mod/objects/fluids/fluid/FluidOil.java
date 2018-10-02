package pjc21.mod.objects.fluids.fluid;

import net.minecraft.util.ResourceLocation;
import pjc21.mod.init.FluidInit;
import pjc21.mod.objects.fluids.FluidBase;

public class FluidOil extends FluidBase 
{

	public FluidOil(String name, ResourceLocation still, ResourceLocation flowing) 
	{
		super(name, still, flowing);
		setHasBucket(true);
		setDensity(300);
		setGaseous(false);
		setLuminosity(9);
		setViscosity(1000);
		setTemperature(300);
		
		FluidInit.FLUIDS.add(this);
	}

}
