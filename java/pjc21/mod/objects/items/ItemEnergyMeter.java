package pjc21.mod.objects.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import pjc21.mod.Main;
import pjc21.mod.objects.blocks.tileentities.TileEntityElectricSinteringFurnace;
import pjc21.mod.objects.blocks.tileentities.TileEntityFibreCable;
import pjc21.mod.objects.blocks.tileentities.TileEntityGlowstoneGenerator;
import pjc21.mod.util.interfaces.IHasModel;

public class ItemEnergyMeter extends ItemBase implements IHasModel
{
	public ItemEnergyMeter(String name) 
	{
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) 
	{
		if(!world.isRemote)
		{
			TileEntity tileentity = world.getTileEntity(pos);
			if(tileentity instanceof TileEntityGlowstoneGenerator)
			{
				player.sendMessage(new TextComponentString("Energy Level = " + ((TileEntityGlowstoneGenerator)tileentity).getEnergyStored()));
			}
			else if(tileentity instanceof TileEntityElectricSinteringFurnace)
			{
				player.sendMessage(new TextComponentString("Energy Level = " + ((TileEntityElectricSinteringFurnace)tileentity).getEnergyStored()));
			}
			else if(tileentity instanceof TileEntityFibreCable)
			{
				player.sendMessage(new TextComponentString("Energy Level = " + ((TileEntityFibreCable)tileentity).getEnergyStored()));
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
