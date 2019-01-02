package pjc21.mod.objects.blocks.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotElectricSinteringFurnaceOutput extends SlotItemHandler 
{
	public SlotElectricSinteringFurnaceOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}
