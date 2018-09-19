package pjc21.mod.world.types;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import pjc21.mod.init.BiomeInit;

public class WorldTypeCopper extends WorldType
{

	public WorldTypeCopper() 
	{
		super("Copper");
	}
	
	@Override
	public BiomeProvider getBiomeProvider(World world) 
	{
		return new BiomeProviderSingle(BiomeInit.COPPER);
	}
	
}
