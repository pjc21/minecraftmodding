package pjc21.mod.objects.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import pjc21.mod.objects.blocks.slots.SlotGlowstoneGenerator;
import pjc21.mod.objects.blocks.tileentities.TileEntityGlowstoneGenerator;
import pjc21.mod.objects.blocks.tileentities.TileEntitySinteringFurnace;


public class ContainerGlowstoneGenerator extends Container
{
	private final TileEntityGlowstoneGenerator tileentity;
	private int energy, cookTime, totalcooktime;
	
	public ContainerGlowstoneGenerator(InventoryPlayer player, TileEntityGlowstoneGenerator tileentity) 
	{
		this.tileentity = tileentity;
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotGlowstoneGenerator(handler, 0, 80, 33));
		
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
			if(this.energy != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
			if(this.cookTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
		}
		
		this.energy = this.tileentity.getField(0);
		this.cookTime = this.tileentity.getField(1);
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
			
			if(index == 0)
			{
				if(!this.mergeItemStack(grabStack, 1, 36, false)) return ItemStack.EMPTY;
				slot.onSlotChange(grabStack, stack);
			}
			else if(index != 0)
			{
				Slot fuelSlot = (Slot)this.inventorySlots.get(0);
				ItemStack fuel = fuelSlot.getStack();
				
				if(TileEntityGlowstoneGenerator.isItemFuel(grabStack))
				{
					if(fuelSlot.getStack().isEmpty())
					{
						if(!this.mergeItemStack(grabStack, 0, 1, false)) 
						{
							return ItemStack.EMPTY;
						}
					}
					else if(grabStack.isItemEqual(fuel) && fuel.getCount() < fuel.getMaxStackSize())
					{
						if(!this.mergeItemStack(grabStack, 0, 1, false)) 
						{
							return ItemStack.EMPTY;
						}
					}
					else if(grabStack.isItemEqual(fuel) && fuel.getCount() == fuel.getMaxStackSize())
					{
						if(index >= 1 && index <= 27)
						{
							if(!this.mergeItemStack(grabStack, 28, 37, false))
							{
								return ItemStack.EMPTY;
							}
						}
						else if(index >= 27 && index <= 36)
						{
							if(!this.mergeItemStack(grabStack, 1, 28, false)) 
							{
								return ItemStack.EMPTY;
							}
						}
					}
				}
				else if(index >= 1 && index <= 27)
				{
					if(!this.mergeItemStack(grabStack, 28, 37, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 27 && index <= 36)
				{
					if(!this.mergeItemStack(grabStack, 1, 28, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
			}
			else if(!this.mergeItemStack(grabStack, 1, 37, false))
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
