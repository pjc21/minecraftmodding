package pjc21.mod.objects.blocks.slots;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import pjc21.mod.objects.blocks.recipes.SinteringFurnaceRecipes;

public class SlotElectricSinteringFurnaceInputs extends SlotItemHandler
{
	public SlotElectricSinteringFurnaceInputs(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
		return SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack);
    }
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
