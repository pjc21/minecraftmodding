package pjc21.mod.objects.blocks.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pjc21.mod.energy.CustomEnergyStorage;

public class TileEntityGlowstoneGenerator extends TileEntity implements ITickable
{
	public ItemStackHandler handler = new ItemStackHandler(1)
	{
		@Override
		protected void onContentsChanged(int slot) 
		{
			TileEntityGlowstoneGenerator.this.markDirty();
		}
	};
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(100000, 1000, 1000);
	public int energy = storage.getEnergyStored();
	public int cookTime;
	private String customName;

	public int getEnergyStored() 
	{
		return this.energy; 
	}
	
	public int getMaxEnergyStored() 
	{
		return this.storage.getMaxEnergyStored();
	}
	
	private int burnEnergy()
	{
		return 20;
	}
	
	public static boolean isItemFuel(ItemStack stack)
	{
		return getFuelValue(stack) > 0;
	}

	@Override
	public void update() 
	{
		boolean flag = false;
		
		if(this.storage.canReceive())
		{
			if(!handler.getStackInSlot(0).isEmpty() && isItemFuel(handler.getStackInSlot(0)))
			{
				++this.cookTime;
				
				if(this.cookTime == 25)
				{
					cookTime = 0;
					this.storage.receiveEnergy(getFuelValue(handler.getStackInSlot(0)), false);
					this.energy = this.storage.getEnergyStored();
					handler.getStackInSlot(0).shrink(1);
					flag = true;
				}
			}
			else if(handler.getStackInSlot(0).isEmpty() && this.cookTime > 0)
			{
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, 25);
			}
		}
		
		if(flag)
		{
			this.markDirty();
		}
	}
	
	private static int getFuelValue(ItemStack stack)
	{
		if(stack.getItem() == Items.GLOWSTONE_DUST) return 1000;
		else return 0;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return true;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("GuiEnergy", this.energy);
		this.storage.writeToNBT(compound);
		if(this.hasCustomName()) compound.setString("CustomName", this.customName);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.cookTime = compound.getInteger("CookTime");
		this.energy = compound.getInteger("GuiEnergy");
		this.storage.readFromNBT(compound);
		if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
	}

	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) 
	{
		super.handleUpdateTag(tag);
	}

	/*@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 0, tag);
	}*/
	
	/*@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) 
	{
		super.onDataPacket(net, packet);
		readFromNBT(packet.getNbtCompound());

		IBlockState state = this.world.getBlockState(this.pos);
		this.world.notifyBlockUpdate(this.pos, state, state, 3);
	}*/
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) 
	{
		return oldState != newState;
	}
	
	public boolean hasCustomName() 
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("container.glowstone_generator");
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return this.energy;
		case 1:
			return this.cookTime;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			this.energy = value;
			break;
		case 1:
			this.cookTime = value;
		}
	}
}
