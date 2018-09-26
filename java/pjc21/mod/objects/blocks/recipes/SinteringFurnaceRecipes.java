package pjc21.mod.objects.blocks.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import pjc21.mod.init.BlockInit;

public class SinteringFurnaceRecipes 
{
	private static final SinteringFurnaceRecipes INSTANCE = new SinteringFurnaceRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static SinteringFurnaceRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private SinteringFurnaceRecipes() 
	{
		addSinteringRecipe(new ItemStack(BlockInit.BLOCK_COPPER), new ItemStack(BlockInit.ORE_OVERWORLD), new ItemStack(Blocks.ANVIL), 5.0F);
		addSinteringRecipe(new ItemStack(Blocks.ACACIA_FENCE), new ItemStack(Blocks.ACACIA_FENCE_GATE), new ItemStack(BlockInit.SINTERING_FURNACE), 5.0F);
	}

	public void addSinteringRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) 
	{
		if(getSinteringResult(input1, input2) != ItemStack.EMPTY) return;
		this.smeltingList.put(input1, input2, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}

	public ItemStack getSinteringResult(ItemStack input1, ItemStack input2) 
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet()) 
		{
			if(this.compareItemStacks(input1, (ItemStack)entry.getKey())) 
			{
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) 
				{
					if(this.compareItemStacks(input2, (ItemStack)ent.getKey())) 
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
			else if(this.compareItemStacks(input2, (ItemStack)entry.getKey())) 
			{
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) 
				{
					if(this.compareItemStacks(input1, (ItemStack)ent.getKey())) 
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList() 
	{
		return this.smeltingList;
	}
	
	public float getSinteringExperience(ItemStack stack)
	{
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) 
		{
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) 
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
	
	public boolean isValidRecipeItem(ItemStack stack)
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : SinteringFurnaceRecipes.getInstance().smeltingList.columnMap().entrySet()) 
		{
			ItemStack stack1 = (ItemStack)entry.getKey();
			
			if(stack1.getItem() == stack.getItem())
			{
				return true;
			}

			for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet())
			{
				ItemStack stack2 = (ItemStack)ent.getKey();

				if(stack2.getItem() == stack.getItem())
				{
					return true;
				}
			}
		}
		return false;
	}
}
