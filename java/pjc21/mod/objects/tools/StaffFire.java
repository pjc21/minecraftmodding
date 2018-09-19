package pjc21.mod.objects.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import pjc21.mod.objects.items.ItemBase;

public class StaffFire extends ItemBase
{
	public StaffFire(String name) 
	{
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		ItemStack item = playerIn.getHeldItem(handIn);
		Vec3d aim = playerIn.getLookVec();
		EntityLargeFireball fireball = new EntityLargeFireball(worldIn, playerIn, 1, 1, 1);
		
		worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, 0.9F);
				
		fireball.setPosition(playerIn.posX + aim.x * 1.5D, playerIn.posY + aim.y * 1.5D, playerIn.posZ + aim.z * 1.5D);
		fireball.accelerationX = aim.x * 0.1;
		fireball.accelerationY = aim.y * 0.1;
		fireball.accelerationZ = aim.z * 0.1;
		
		worldIn.spawnEntity(fireball);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	
	
	/*@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		// TODO Auto-generated method stub
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}*/
}
