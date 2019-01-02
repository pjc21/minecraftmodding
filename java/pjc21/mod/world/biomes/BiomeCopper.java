package pjc21.mod.world.biomes;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import pjc21.mod.entity.unikitty.EntityUniKitty;
import pjc21.mod.init.BlockInit;
import pjc21.mod.objects.blocks.BlockDirts;
import pjc21.mod.objects.blocks.BlockOres;
import pjc21.mod.util.handlers.DirtHandler;
import pjc21.mod.util.handlers.EnumHandler;
import pjc21.mod.world.gen.generators.WorldGenCopperTree;

public class BiomeCopper extends Biome 
{
	protected static final WorldGenAbstractTree TREE = new WorldGenCopperTree();
	
	public BiomeCopper() 
	{
		super(new BiomeProperties("Copper").setBaseHeight(1.5F).setHeightVariation(1.2F).setTemperature(0.6F).setRainDisabled().setWaterColor(16776983));

		topBlock = BlockInit.BLOCK_DIRT.getDefaultState().withProperty(BlockDirts.VARIANT, DirtHandler.EnumType.RED);
		fillerBlock = BlockInit.BLOCK_DIRT.getDefaultState().withProperty(BlockDirts.VARIANT, DirtHandler.EnumType.BLUE);
		
		decorator.coalGen = new WorldGenMinable(BlockInit.ORE_OVERWORLD.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.COPPER), 50);
		decorator.treesPerChunk = 0;
		//decorator.cactiPerChunk = 2;
		
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityUniKitty.class, 10, 1, 5));
	}
	
	
	
	/*@Override
	public BiomeDecorator createBiomeDecorator()
    {
		BiomeDecorator biomeDecorator = new BiomeDecoratorCopper();
        
        //biomeDecorator.coalGen = new WorldGenMinable(BlockInit.ORE_OVERWORLD.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.COPPER), 10);
        biomeDecorator.treesPerChunk = 1;
        biomeDecorator.cactiPerChunk = 2;
		
        return getModdedBiomeDecorator(biomeDecorator);
    }*/
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) 
	{
		return TREE;
	}

}
