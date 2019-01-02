package pjc21.mod.util.helpers;

import net.minecraft.nbt.NBTTagCompound;
import pjc21.mod.energy.CustomEnergyStorage;
import pjc21.mod.objects.blocks.tileentities.TileEntityGlowstoneGenerator;
import pjc21.mod.objects.blocks.tileentities.TileEntityMiner;

public class NBTHelper 
{
	public static NBTTagCompound toNBT(Object o)
	{
		if(o instanceof TileEntityMiner)
		{
			return writeMiner((TileEntityMiner)o);
		}
		
		
		return null;
	}

	

	

	private static NBTTagCompound writeMiner(TileEntityMiner o) 
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("y", o.y);
		compound.setInteger("x", o.x);
		compound.setInteger("z", o.z);
		return compound;
	}
	

}
