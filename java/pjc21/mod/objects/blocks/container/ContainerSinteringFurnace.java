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
	private final TileEntitySinteringFurnace titeentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public ContainerSinteringFurnace(InventoryPlayer player, TileEntitySinteringFurnace tileentity) 
	{
		this.titeentity = tileentity;
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
		return this.titeentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		this.titeentity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(this.cookTime != this.titeentity.getField(2)) listener.sendWindowProperty(this, 2, this.titeentity.getField(2));
			if(this.burnTime != this.titeentity.getField(0)) listener.sendWindowProperty(this, 0, this.titeentity.getField(0));
			if(this.currentBurnTime != this.titeentity.getField(1)) listener.sendWindowProperty(this, 1, this.titeentity.getField(1));
			if(this.totalCookTime != this.titeentity.getField(3)) listener.sendWindowProperty(this, 3, this.titeentity.getField(3));
		}
		
		this.cookTime = this.titeentity.getField(2);
		this.burnTime = this.titeentity.getField(0);
		this.currentBurnTime = this.titeentity.getField(1);
		this.totalCookTime = this.titeentity.getField(3);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 3) 
			{
				if(!this.mergeItemStack(stack1, 4, 40, false)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 2 && index != 1 && index != 0) 
			{		
				Slot slot0 = (Slot)this.inventorySlots.get(0);
				Slot slot1 = (Slot)this.inventorySlots.get(1);
				ItemStack stack2 = slot0.getStack();
				ItemStack stack3 = slot1.getStack();
				
				if(slot0.getStack().isEmpty() && slot1.getStack().isEmpty())
				{
					if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack1))
					{
						if(!this.mergeItemStack(stack1, 0, 2, false)) 
						{
							return ItemStack.EMPTY;
						}
					}
					else if(TileEntitySinteringFurnace.isItemFuel(stack1))
					{
						if(!this.mergeItemStack(stack1, 2, 3, false)) 
						{
							return ItemStack.EMPTY;
						}
					}
				}
				else if(slot0.getStack().isEmpty() || slot1.getStack().isEmpty())
				{
					if(slot0.getStack().isEmpty())
					{
						if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack1))
						{
							if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack1, stack3).isEmpty())
							{
								if(!this.mergeItemStack(stack1, 0, 1, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
							else if(stack1.isItemEqual(stack3))
							{
								if(!this.mergeItemStack(stack1, 1, 2, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
						}
						else if(TileEntitySinteringFurnace.isItemFuel(stack1))
						{
							if(!this.mergeItemStack(stack1, 2, 3, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
					}
					else
					{
						if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack1))
						{
							if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack1, stack2).isEmpty())
							{
								if(!this.mergeItemStack(stack1, 1, 2, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
							else if(stack1.isItemEqual(stack2))
							{
								if(!this.mergeItemStack(stack1, 0, 1, false)) 
								{
									return ItemStack.EMPTY;
								}
							}
						}
						else if(TileEntitySinteringFurnace.isItemFuel(stack1))
						{
							if(!this.mergeItemStack(stack1, 2, 3, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
					}
				}
				else if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack1))
				{
					if(!this.mergeItemStack(stack1, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if(TileEntitySinteringFurnace.isItemFuel(stack1))
				{
					if(!this.mergeItemStack(stack1, 2, 3, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 4 && index < 31)
				{
					if(!this.mergeItemStack(stack1, 31, 40, true)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 31 && index < 40)
				{
					if(!this.mergeItemStack(stack1, 4, 31, false))
					{
						return ItemStack.EMPTY;
					}
				}
			} 
			else if(!this.mergeItemStack(stack1, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}
			
			if(stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}
