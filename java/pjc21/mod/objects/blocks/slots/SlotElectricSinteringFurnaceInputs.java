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
		
		
        /*if (stack.isEmpty())
        {
        	return false;
        }

        IItemHandler handler = this.getItemHandler();
        
        if(handler.getStackInSlot(0).isEmpty() && handler.getStackInSlot(1).isEmpty())
        {
        	if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack))
            {
            	return true;
            }
        }
        else if(handler.getStackInSlot(0).isEmpty() || handler.getStackInSlot(1).isEmpty())
        {
    		if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack))
			{
				if(handler.getStackInSlot(0).isEmpty())
	        	{
					ItemStack slot1 = handler.getStackInSlot(1);
					if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack, slot1).isEmpty())
					{
						return true;
					}
	        	}
				else if(handler.getStackInSlot(1).isEmpty())
				{
					ItemStack slot0 = handler.getStackInSlot(0);
					if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack, slot0).isEmpty())
					{
						return true;
					}
				}
			}
        }
        return false;*/
    }
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
