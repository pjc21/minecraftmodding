package pjc21.mod.objects.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pjc21.mod.Main;
import pjc21.mod.objects.blocks.tileentities.TileEntityMiner;

public class BlockMiner extends BlockBase implements ITileEntityProvider
{

	public BlockMiner(String name, Material material) 
	{
		super(name, material, Main.PAULSTAB);
		setHardness(1.0f);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public boolean hasTileEntity() 
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) 
	{
		return new TileEntityMiner();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityMiner();
	}
	

}
