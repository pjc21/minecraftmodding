package pjc21.mod.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pjc21.mod.objects.blocks.container.ContainerCopperChest;
import pjc21.mod.objects.blocks.container.ContainerGlowstoneGenerator;
import pjc21.mod.objects.blocks.gui.GuiCopperChest;
import pjc21.mod.objects.blocks.gui.GuiGlowstoneGenerator;
import pjc21.mod.objects.blocks.machines.sinterer.ContainerSinteringFurnace;
import pjc21.mod.objects.blocks.machines.sinterer.GuiSinteringFurnace;
import pjc21.mod.objects.blocks.machines.sinterer.TileEntitySinteringFurnace;
import pjc21.mod.objects.blocks.tileentities.TileEntityCopperChest;
import pjc21.mod.objects.blocks.tileentities.TileEntityGlowstoneGenerator;
import pjc21.mod.util.Reference;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_SINTERING_FURNACE) return new ContainerSinteringFurnace(player.inventory, (TileEntitySinteringFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Reference.GUI_COPPER_CHEST) return new ContainerCopperChest(player.inventory, (TileEntityCopperChest)world.getTileEntity(new BlockPos(x,y,z)), player);
		if(ID == Reference.GUI_GLOWSTONE_GENERATOR) return new ContainerGlowstoneGenerator(player.inventory, (TileEntityGlowstoneGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_SINTERING_FURNACE) return new GuiSinteringFurnace(player.inventory, (TileEntitySinteringFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Reference.GUI_COPPER_CHEST) return new GuiCopperChest(player.inventory, (TileEntityCopperChest)world.getTileEntity(new BlockPos(x,y,z)), player);
		if(ID == Reference.GUI_GLOWSTONE_GENERATOR) return new GuiGlowstoneGenerator(player.inventory, (TileEntityGlowstoneGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		
		return null;
	}
}
