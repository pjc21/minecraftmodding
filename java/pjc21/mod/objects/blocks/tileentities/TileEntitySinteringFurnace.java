package pjc21.mod.objects.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pjc21.mod.objects.blocks.BlockElectricSinteringFurnace;
import pjc21.mod.objects.blocks.BlockSinteringFurnace;
import pjc21.mod.objects.blocks.recipes.SinteringFurnaceRecipes;

public class TileEntitySinteringFurnace extends TileEntity implements ITickable
{
	public ItemStackHandler handler = new ItemStackHandler(4)
	{
		@Override
		protected void onContentsChanged(int slot) 
		{
			TileEntitySinteringFurnace.this.markDirty();
		}
	};
	
	private String customName;
	private ItemStack smelting = ItemStack.EMPTY;
	
	private final int FLAGS = 3;
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime = 200;
	
	public static boolean isItemFuel(ItemStack fuel)
	{
		return getItemBurnTime(fuel) > 0;
	}
	
	public int getInventoryStackLimit()
    {
        return 64;
    }
	
	public boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(TileEntitySinteringFurnace te) 
	{
		return te.getField(0) > 0;
	}
	
	public void update() 
	{	
		boolean flag = false;
		
		if(this.isBurning())
		{
			--this.burnTime;
		}

		if(!world.isRemote)
		{
			ItemStack[] inputs = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1)};
			ItemStack fuel = this.handler.getStackInSlot(2);
			
			if(this.isBurning() || !fuel.isEmpty() && !inputs[0].isEmpty() || !inputs[1].isEmpty())
			{
				if(!this.isBurning() && this.canSmelt())
				{
					this.burnTime = getItemBurnTime(fuel);
					this.currentBurnTime = burnTime;
					
					if(this.isBurning() && !fuel.isEmpty())
					{
						Item item = fuel.getItem();
						fuel.shrink(1);
						
						if(fuel.isEmpty())
						{
							ItemStack item1 = item.getContainerItem(fuel);
							this.handler.setStackInSlot(2, item1);
						}
					}
				}
			}
			if(this.isBurning() && !inputs[0].isEmpty() && !inputs[1].isEmpty())
			{
				if(this.isBurning() && this.canSmelt())
				{
					++this.cookTime;
					
					if(this.cookTime == this.totalCookTime)
					{
						this.cookTime = 0;
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
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}
			else if (!this.isBurning() && fuel.isEmpty() && this.cookTime > 0)
	        {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
	        }
			
			if (this.isBurning()) 	
	        {
	            flag = true;
	            BlockSinteringFurnace.setState(this.isBurning(), world, pos);
	        }
			else if(!this.isBurning())
			{
				flag = true;
	            BlockSinteringFurnace.setState(this.isBurning(), world, pos);
			}
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
                ItemStack itemstack1 = this.handler.getStackInSlot(3);

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
            ItemStack itemstack2 = this.handler.getStackInSlot(3);

            if (itemstack2.isEmpty())
            {
                this.handler.setStackInSlot(3, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }
        }
    }
	
	public static int getItemBurnTime(ItemStack fuel) 
	{
		if(fuel.isEmpty()) return 0;
		else 
		{
			Item item = fuel.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) 
			{
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.WOODEN_SLAB) return 150;
				if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
				if (block == Blocks.COAL_BLOCK) return 16000;
			}

			if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
			if (item == Items.STICK) return 100;
			if (item == Items.COAL) return 1600;
			if (item == Items.LAVA_BUCKET) return 20000;
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
			if (item == Items.BLAZE_ROD) return 2400;

			return GameRegistry.getFuelValue(fuel);
		}
	}
		
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("BurnTime", (short)this.burnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);

		if(this.hasCustomName()) compound.setString("CustomName", this.customName);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBurnTime((ItemStack)this.handler.getStackInSlot(2));
		
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
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.sintering_furnace");
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
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) 
	{
		switch(id) 
		{
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}
}
