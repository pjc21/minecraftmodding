package pjc21.mod.objects.blocks.machines.miner.te;

import static pjc21.mod.util.helpers.NBTHelper.*;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class TileEntityMiner extends TileEntity implements ITickable, ICapabilityProvider
{

	public int x, y, z, tick;
	boolean initalized = false;
	
	@Override
	public void update() 
	{
		System.out.println("Ticking!!" + tick);
		System.out.println("X" + x);
		System.out.println("Y" + y);
		System.out.println("Z" + z);
		if(!initalized)
		{
			init();
		}
		tick++;
		
		if(tick == 40)
		{
			tick = 0;
			if(y > 1)
			{
				execute();
			}
		}
	}
	
	private void execute()
	{
		for(int x = 0; x < 3; x++)
		{
			for(int z = 0; z < 3; z++)
			{
				BlockPos posToBreak = new BlockPos(this.x + x, this.y, this.z + z);
				IBlockState broken = world.getBlockState(posToBreak);
				world.setBlockToAir(posToBreak);
				NonNullList<ItemStack> drops = NonNullList.create();
				broken.getBlock().getDrops(drops, world, posToBreak, broken, 0);
				
				if(!world.isRemote)
				{
					for(ItemStack stack : drops)
					{
						InventoryHelper.spawnItemStack(world, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), stack);
					}
				}
			}
		}
		this.y--;
	}
	
	private void init()
	{
		initalized = true;
		x = this.pos.getX() - 1;
		y = this.pos.getY() - 1;
		z = this.pos.getZ() - 1;
		tick = 0;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setTag("initvalues", toNBT(this));
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		NBTTagCompound initValues = compound.getCompoundTag("initvalues");
		if(initValues != null) 
		{
			this.x = initValues.getInteger("x");
			this.y = initValues.getInteger("y");
			this.z = initValues.getInteger("z");
			this.tick = 0;
			initalized = true;
			return;
		}
		init();
	}
}
