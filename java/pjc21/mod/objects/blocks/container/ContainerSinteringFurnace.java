package pjc21.mod.objects.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import pjc21.mod.objects.blocks.recipes.SinteringFurnaceRecipes;
import pjc21.mod.objects.blocks.slots.SlotSinteringFurnaceFuel;
import pjc21.mod.objects.blocks.slots.SlotSinteringFurnaceInputs;
import pjc21.mod.objects.blocks.slots.SlotSinteringFurnaceOutput;
import pjc21.mod.objects.blocks.tileentities.TileEntitySinteringFurnace;

public class ContainerSinteringFurnace extends Container
{
	private final TileEntitySinteringFurnace tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public ContainerSinteringFurnace(InventoryPlayer player, TileEntitySinteringFurnace tileentity) 
	{
		this.tileentity = tileentity;
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotSinteringFurnaceInputs(handler, 0, 26, 17));
		this.addSlotToContainer(new SlotSinteringFurnaceInputs(handler, 1, 56, 17));
		this.addSlotToContainer(new SlotSinteringFurnaceFuel(handler, 2, 56, 53));
		this.addSlotToContainer(new SlotSinteringFurnaceOutput(handler, 3, 116, 35));
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		this.tileentity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(this.cookTime != this.tileentity.getField(2)) listener.sendWindowProperty(this, 2, this.tileentity.getField(2));
			if(this.burnTime != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
			if(this.currentBurnTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
			if(this.totalCookTime != this.tileentity.getField(3)) listener.sendWindowProperty(this, 3, this.tileentity.getField(3));
		}
		
		this.cookTime = this.tileentity.getField(2);
		this.burnTime = this.tileentity.getField(0);
		this.currentBurnTime = this.tileentity.getField(1);
		this.totalCookTime = this.tileentity.getField(3);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack grabStack = slot.getStack();
			stack = grabStack.copy();
			
			if(index == 3) 
			{
				if(!this.mergeItemStack(grabStack, 4, 40, false)) return ItemStack.EMPTY;
				slot.onSlotChange(grabStack, stack);
			}
			else if(index != 2 && index != 1 && index != 0)
			{		
				Slot inputSlot_0 = (Slot)this.inventorySlots.get(0);
				Slot inputSlot_1 = (Slot)this.inventorySlots.get(1);
				Slot fuelSlot = (Slot)this.inventorySlots.get(2);
				ItemStack input_0 = inputSlot_0.getStack();
				ItemStack input_1 = inputSlot_1.getStack();
				ItemStack fuel = fuelSlot.getStack();
				
				if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(grabStack))
				{
					if(inputSlot_0.getStack().isEmpty() && inputSlot_1.getStack().isEmpty())
					{
						if(!this.mergeItemStack(grabStack, 0, 2, false)) 
						{
							return ItemStack.EMPTY;
						}
					}
					else if(inputSlot_0.getStack().isEmpty())
					{
						if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(grabStack, input_1).isEmpty())
						{
							if(!this.mergeItemStack(grabStack, 0, 1, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
						else if(grabStack.isItemEqual(input_1) && input_1.getCount() < input_1.getMaxStackSize())
						{
							if(!this.mergeItemStack(grabStack, 1, 2, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
						else if(grabStack.isItemEqual(input_1) && input_1.getCount() == input_1.getMaxStackSize())
						{
							if(index >= 4 && index <= 30)
							{
								if(!this.mergeItemStack(grabStack, 31, 40, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
							else if(index >= 30 && index <= 39)
							{
								if(!this.mergeItemStack(grabStack, 4, 31, false))
								{
									return ItemStack.EMPTY;
								}
							}
						}
					}
					else if(inputSlot_1.getStack().isEmpty())
					{
						if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(grabStack, input_0).isEmpty())
						{
							if(!this.mergeItemStack(grabStack, 1, 2, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
						else if(grabStack.isItemEqual(input_0) && input_0.getCount() < input_0.getMaxStackSize())
						{
							if(!this.mergeItemStack(grabStack, 0, 1, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
						else if(grabStack.isItemEqual(input_0) && input_0.getCount() == input_0.getMaxStackSize())
						{
							if(index >= 4 && index <= 30)
							{
								if(!this.mergeItemStack(grabStack, 31, 40, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
							else if(index >= 30 && index <= 39)
							{
								if(!this.mergeItemStack(grabStack, 4, 31, false))
								{
									return ItemStack.EMPTY;
								}
							}
						}
					}
					else
					{
						if(grabStack.isItemEqual(input_1) && input_1.getCount() < input_1.getMaxStackSize())
						{
							if(!this.mergeItemStack(grabStack, 1, 2, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
						else if(grabStack.isItemEqual(input_1) && input_1.getCount() == input_1.getMaxStackSize())
						{
							if(index >= 4 && index <= 30)
							{
								if(!this.mergeItemStack(grabStack, 31, 40, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
							else if(index >= 30 && index <= 39)
							{
								if(!this.mergeItemStack(grabStack, 4, 31, false))
								{
									return ItemStack.EMPTY;
								}
							}
						}
						else if(grabStack.isItemEqual(input_0) && input_0.getCount() < input_0.getMaxStackSize())
						{
							if(!this.mergeItemStack(grabStack, 0, 1, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
						else if(grabStack.isItemEqual(input_0) && input_0.getCount() == input_0.getMaxStackSize())
						{
							if(index >= 4 && index <= 30)
							{
								if(!this.mergeItemStack(grabStack, 31, 40, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
							else if(index >= 30 && index <= 39)
							{
								if(!this.mergeItemStack(grabStack, 4, 31, false))
								{
									return ItemStack.EMPTY;
								}
							}
						}
					}
				}
				else if(TileEntitySinteringFurnace.isItemFuel(grabStack))
				{
					if(fuelSlot.getStack().isEmpty())
					{
						if(!this.mergeItemStack(grabStack, 2, 3, false)) 
						{
							return ItemStack.EMPTY;
						}
					}
					else if(grabStack.isItemEqual(fuel) && fuel.getCount() < fuel.getMaxStackSize())
					{
						if(!this.mergeItemStack(grabStack, 2, 3, false)) 
						{
							return ItemStack.EMPTY;
						}
					}
					else if(grabStack.isItemEqual(fuel) && fuel.getCount() == fuel.getMaxStackSize())
					{
						if(index >= 4 && index <= 30)
						{
							if(!this.mergeItemStack(grabStack, 31, 40, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
						else if(index >= 30 && index <= 39)
						{
							if(!this.mergeItemStack(grabStack, 4, 31, false))
							{
								return ItemStack.EMPTY;
							}
						}
					}
				}
				else if(index >= 4 && index <= 30)
				{
					if(!this.mergeItemStack(grabStack, 31, 40, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 30 && index <= 39)
				{
					if(!this.mergeItemStack(grabStack, 4, 31, false))
					{
						return ItemStack.EMPTY;
					}
				}
			}
			else if(!this.mergeItemStack(grabStack, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}
			
			if(grabStack.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
			if(grabStack.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, grabStack);
		}
		return stack;
	}
}
