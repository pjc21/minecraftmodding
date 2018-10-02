package pjc21.mod.objects.blocks.slots;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import pjc21.mod.objects.blocks.recipes.SinteringFurnaceRecipes;

public class SlotElectricSinteringFurnaceInputs extends SlotItemHandler
{
	private final IItemHandler itemHandler;
    private final int index;
	
	public SlotElectricSinteringFurnaceInputs(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
	    this.index = index;
	}
	
	@Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
		IItemHandler handler = this.getItemHandler();
		ItemStack remainder = ItemStack.EMPTY;
		ItemStack input_0 = ItemStack.EMPTY;
		ItemStack input_1 = ItemStack.EMPTY;
		
		if(SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack))			
		{
			input_0 = handler.getStackInSlot(0);
			input_1 = handler.getStackInSlot(1);
			
			if (handler instanceof IItemHandlerModifiable)
	        {
				IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;
	            ItemStack currentStack = handlerModifiable.getStackInSlot(index);
	            System.out.println("CurrentStack " + currentStack.getDisplayName());
				
				if(input_0.isEmpty() && input_1.isEmpty())
				{
	            	return true;
				}
				else if(input_0.isEmpty())
				{
					if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack, input_1).isEmpty())
					{
						remainder = handlerModifiable.insertItem(index, stack, true);
						handlerModifiable.setStackInSlot(index, currentStack);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
					else if(stack.isItemEqual(currentStack))
					{
						remainder = handlerModifiable.insertItem(index, stack, true);
						handlerModifiable.setStackInSlot(index, currentStack);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
				}
				else if(input_1.isEmpty())
				{
					if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack, input_0).isEmpty())
					{
						remainder = handlerModifiable.insertItem(index, stack, true);
						handlerModifiable.setStackInSlot(index, currentStack);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
					else if(stack.isItemEqual(currentStack))
					{
						remainder = handlerModifiable.insertItem(index, stack, true);
						handlerModifiable.setStackInSlot(index, currentStack);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
				}
				else
				{
					if(stack.isItemEqual(currentStack))
					{
						remainder = handlerModifiable.insertItem(index, stack, true);
						handlerModifiable.setStackInSlot(index, currentStack);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
				}
	        }
			else
			{
				ItemStack currentStack = handler.getStackInSlot(index);
				
				if(input_0.isEmpty() && input_1.isEmpty())
				{
	            	return true;
				}
				else if(input_0.isEmpty())
				{
					if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack, input_1).isEmpty())
					{
						remainder = handler.insertItem(index, stack, true);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
					else if(stack.isItemEqual(currentStack))
					{
						remainder = handler.insertItem(index, stack, true);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
				}
				else if(input_1.isEmpty())
				{
					if(!SinteringFurnaceRecipes.getInstance().getSinteringResult(stack, input_0).isEmpty())
					{
						remainder = handler.insertItem(index, stack, true);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
					else if(stack.isItemEqual(currentStack))
					{
						remainder = handler.insertItem(index, stack, true);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
				}
				else
				{
					if(stack.isItemEqual(currentStack))
					{
						remainder = handler.insertItem(index, stack, true);
						return remainder.isEmpty() || remainder.getCount() <= stack.getCount();
					}
				}
			}
		}
		return false;
		
		//return SinteringFurnaceRecipes.getInstance().isValidRecipeItem(stack);
    }
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
