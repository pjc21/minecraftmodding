package pjc21.mod.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import pjc21.mod.Main;

public class BlockSantaHat extends BlockBase 
{

	public static final AxisAlignedBB SANTA_HAT_AABB = new AxisAlignedBB(0.1875D, 0, 0.1875D, 0.8125D, 0.625D, 0.8125D);
	
	public BlockSantaHat(String name) 
	{
		super(name, Material.CLOTH, Main.PAULSTAB);
		setSoundType(SoundType.CLOTH);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return SANTA_HAT_AABB;
	}
	
}
