package pjc21.mod.objects.blocks.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import pjc21.mod.objects.blocks.tileentities.TileEntityGlowstoneGenerator;

public class SlotGlowstoneGenerator extends SlotItemHandler
{
	public SlotGlowstoneGenerator(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		return TileEntityGlowstoneGenerator.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
