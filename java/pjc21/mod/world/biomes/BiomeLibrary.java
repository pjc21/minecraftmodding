package pjc21.mod.world.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class BiomeLibrary extends Biome 
{

	public BiomeLibrary() 
	{
		super(new BiomeProperties("Library").setBaseHeight(1.5F).setHeightVariation(1.2F).setTemperature(0.6F).setRainDisabled().setWaterColor(16776983));
	}

}
