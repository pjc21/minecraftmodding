package pjc21.mod.objects.blocks.slots;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import pjc21.mod.objects.blocks.recipes.SinteringFurnaceRecipes;

public class SlotSinteringFurnaceOutput extends SlotItemHandler
{
	
	public SlotSinteringFurnaceOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		return false;
	}

	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) 
	{
		EntityPlayer player = thePlayer;
		
		if (!player.world.isRemote)
        {
			int i = stack.getCount();
	        float f = SinteringFurnaceRecipes.getInstance().getSinteringExperience(stack);

	        if (f == 0.0F)
	        {
	            i = 0;
	        }
	        else if (f < 1.0F)
	        {
	            int j = MathHelper.floor((float)i * f);

	            if (j < MathHelper.ceil((float)i * f) && Math.random() < (double)((float)i * f - (float)j))
	            {
	                ++j;
	            }

	            i = j;
	        }

	        while (i > 0)
	        {
	            int k = EntityXPOrb.getXPSplit(i);
	            i -= k;
	            player.world.spawnEntity(new EntityXPOrb(player.world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, k));
	        }
        }

		onSlotChanged();
        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerSmeltedEvent(player, stack);
		return super.onTake(thePlayer, stack);
	}
	
	@Override
	public void onSlotChanged() 
	{
		super.onSlotChanged();
	}
	
	@Override
	public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) 
	{
		super.onSlotChange(p_75220_1_, p_75220_2_);
	}
	
}
