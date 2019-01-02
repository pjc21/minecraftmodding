package pjc21.mod.world.types;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import pjc21.mod.world.types.layer.GenLayerCustom;

public class WorldTypeCustom extends WorldType
{

	public WorldTypeCustom() 
	{
		super("Custom");
	}
	
	@Override
	public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, ChunkGeneratorSettings chunkSettings) 
	{
		return new GenLayerCustom(worldSeed, parentLayer, this, chunkSettings);
	}

}
