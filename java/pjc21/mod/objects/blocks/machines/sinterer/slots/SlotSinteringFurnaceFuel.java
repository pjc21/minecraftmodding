package pjc21.mod.objects.blocks.machines.sinterer.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pjc21.mod.objects.blocks.machines.sinterer.TileEntitySinteringFurnace;

public class SlotSinteringFurnaceFuel extends Slot
{
	public SlotSinteringFurnaceFuel(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		return TileEntitySinteringFurnace.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
