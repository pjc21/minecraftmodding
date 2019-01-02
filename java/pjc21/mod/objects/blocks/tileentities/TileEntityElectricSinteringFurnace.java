package pjc21.mod.objects.blocks.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pjc21.mod.energy.CustomEnergyStorage;
import pjc21.mod.objects.blocks.BlockElectricSinteringFurnace;
import pjc21.mod.objects.blocks.recipes.SinteringFurnaceRecipes;

public class TileEntityElectricSinteringFurnace extends TileEntity implements ITickable
{
	public ItemStackHandler handler = new ItemStackHandler(3)
	{
		@Override
		protected void onContentsChanged(int slot) 
		{
			TileEntityElectricSinteringFurnace.this.markDirty();
		}
	};
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(100000, 1000, 1000);
	private String customName;
	private int energy = storage.getEnergyStored();
	private int cookTime;
	private boolean lit;
	
	public int getEnergyStored() 
	{
		return this.energy; 
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	private boolean hasEnergy()
    {
        return this.energy > 0;
    }
	
	private int getEnergyValue()
	{
		return 100;
	}
	
	private int burnEnergy()
	{
		return 20;
	}
	
	public int getInventoryStackLimit()
    {
        return 64;
    }
	
	private boolean isCooking()
	{
		return this.cookTime > 0;
	}
	
	private int extractEnergy(int energy, boolean a)
	{
		return this.storage.extractEnergy(energy, a);
	}

	public void update()
	{

		boolean flag = false;
		boolean flag1 = this.isCooking();

		if(lit)
		{
			 BlockElectricSinteringFurnace.setState(this.isCooking(), world, pos);
		}
		
		if(world.isBlockPowered(pos))
		{
			if(this.storage.canReceive())
			{
				this.storage.receiveEnergy(getEnergyValue(), false);
				this.energy = this.storage.getEnergyStored();
				flag = true;
			}
		}
		
		ItemStack[] inputs = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1)};
		
		if(this.hasEnergy() && !inputs[0].isEmpty() && !inputs[1].isEmpty())
		{
			if (this.hasEnergy() && this.canSmelt())
            {
                ++this.cookTime;

                if(this.storage.canExtract())
                {
    				this.storage.extractEnergy(burnEnergy(), false);
    				this.energy = this.storage.getEnergyStored();
    				flag = true;
                }

                if (this.cookTime == 100)
                {
                    this.cookTime = 1;
                    this.smeltItem();
                    inputs[0].shrink(1);
					inputs[1].shrink(1);
					handler.setStackInSlot(0, inputs[0]);
					handler.setStackInSlot(1, inputs[1]);
                    flag = true;
                }
            }
		}
		else if (inputs[0].isEmpty() || inputs[1].isEmpty() && this.cookTime > 0)
		{
			this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, 100);
		}
		else if (!this.hasEnergy() && this.cookTime > 0)
        {
			this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, 100);
        }

		if (flag1 != this.isCooking())	
        {
			lit = this.isCooking();
            BlockElectricSinteringFurnace.setState(this.isCooking(), world, pos);
        }

		if (flag)
        {
            this.markDirty();
        }
	}
	
	private boolean canSmelt()
    {
        if (this.handler.getStackInSlot(0).isEmpty() && this.handler.getStackInSlot(1).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = SinteringFurnaceRecipes.getInstance().getSinteringResult(this.handler.getStackInSlot(0), this.handler.getStackInSlot(1));
            		
            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.handler.getStackInSlot(2);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }
	
	public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack1 = SinteringFurnaceRecipes.getInstance().getSinteringResult(this.handler.getStackInSlot(0), this.handler.getStackInSlot(1));
            ItemStack itemstack2 = this.handler.getStackInSlot(2);

            if (itemstack2.isEmpty())
            {
                this.handler.setStackInSlot(2, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }
        }
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
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		if(capability == CapabilityEnergy.ENERGY) return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("GuiEnergy", this.energy);
		compound.setString("Name", getDisplayName().toString());
		compound.setBoolean("Lit", this.lit);
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
		this.lit = compound.getBoolean("Lit");
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
		return new SPacketUpdateTileEntity(this.pos, 1, tag);
	}
	
	@Override
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
		return new TextComponentTranslation("container.electric_sintering_furnace");
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
			return this.cookTime;
		case 1:
			return this.energy;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			this.cookTime = value;
			break;
		case 1:
			this.energy = value;
		}
	}
}
