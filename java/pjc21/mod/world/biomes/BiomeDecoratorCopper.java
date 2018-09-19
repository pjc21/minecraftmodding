package pjc21.mod.world.biomes;

import java.util.Random;
import java.util.function.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import pjc21.mod.init.BlockInit;
import pjc21.mod.objects.blocks.BlockOres;
import pjc21.mod.util.handlers.EnumHandler;

public class BiomeDecoratorCopper extends BiomeDecorator
{
	//Predicate<IBlockState> replaceablePredicate = new CloudPredicate();
	
	//private int coalSize = 17;
	
	public BiomeDecoratorCopper()
	{
		super();
		
		coalGen = new WorldGenMinable(BlockInit.ORE_OVERWORLD.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.COPPER), 10);
		//coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), coalSize);
	}
	
	/*private void generateTrees(World worldIn, Biome biomeIn, Random random, BlockPos chunkPos)
    {
        int treesToGen = treesPerChunk;

        if (random.nextFloat() < extraTreeChance)
        {
            ++treesToGen;
        }

        if(TerrainGen.decorate(worldIn, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
        for (int numTreesGenerated = 0; numTreesGenerated < treesToGen; ++numTreesGenerated)
        {
            int treeX = random.nextInt(16) + 8;
            int treeZ = random.nextInt(16) + 8;
            WorldGenAbstractTree treeGen = biomeIn.getRandomTreeFeature(random);
            treeGen.setDecorationDefaults();
            BlockPos blockpos = worldIn.getHeight(chunkPos.add(treeX, 0, treeZ));

            if (treeGen.generate(worldIn, random, blockpos))
            {
                treeGen.generateSaplings(worldIn, random, blockpos);
            }
        }
    }*/
	
	
}
