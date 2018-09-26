package pjc21.mod.objects.blocks.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import pjc21.mod.objects.blocks.tileentities.TileEntitySinteringFurnace;

public class SlotSinteringFurnaceFuel extends SlotItemHandler
{
	public SlotSinteringFurnaceFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
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
