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
import pjc21.mod.objects.blocks.slots.SlotElectricSinteringFurnaceInputs;
import pjc21.mod.objects.blocks.slots.SlotElectricSinteringFurnaceOutput;
import pjc21.mod.objects.blocks.tileentities.TileEntityElectricSinteringFurnace;

public class ContainerElectricSinteringFurnace extends Container
{
	private final TileEntityElectricSinteringFurnace tileentity;
	private int cookTime, energy;
	
	public ContainerElectricSinteringFurnace(InventoryPlayer player, TileEntityElectricSinteringFurnace tileentity) 
	{
		this.tileentity = tileentity;
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotElectricSinteringFurnaceInputs(handler, 0, 44, 21));
		this.addSlotToContainer(new SlotElectricSinteringFurnaceInputs(handler, 1, 44, 50));
		this.addSlotToContainer(new SlotElectricSinteringFurnaceOutput(handler, 2, 97, 36));
		
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) 
	{
		this.tileentity.setField(id, data);
		/*if (id == 0)
        {
            this.tileentity.cookTime = data;
        }
		if (id == 1)
        {
			this.tileentity.energy = data;
        }*/
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();

		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(this.cookTime != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
			if(this.energy != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
			
			/*if(this.energy != this.tileentity.energy)
			{
				listener.sendWindowProperty(this, 1, this.tileentity.energy);
			}
			
			if(this.cookTime != this.tileentity.cookTime)
			{
				listener.sendWindowProperty(this, 0, this.tileentity.cookTime);
			}*/
		}

		this.cookTime = this.tileentity.getField(0);
		this.energy = this.tileentity.getField(1);
		//this.cookTime = this.tileentity.cookTime;
		//this.energy = this.tileentity.energy;
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
			
			if(index == 2) 
			{
				if(!this.mergeItemStack(stack1, 3, 39, false)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 1 && index != 0) 
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
					}
				}
				else if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack1))
				{
					if(!this.mergeItemStack(stack1, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 3 && index < 30)
				{
					if(!this.mergeItemStack(stack1, 30, 39, true)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 30 && index < 39)
				{
					if(!this.mergeItemStack(stack1, 3, 30, true))
					{
						return ItemStack.EMPTY;
					}
				}
			} 
			else if(!this.mergeItemStack(stack1, 3, 39, false)) 
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