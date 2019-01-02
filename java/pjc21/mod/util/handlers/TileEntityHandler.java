package pjc21.mod.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pjc21.mod.objects.blocks.tileentities.TileEntityCopperChest;
import pjc21.mod.objects.blocks.tileentities.TileEntityElectricSinteringFurnace;
import pjc21.mod.objects.blocks.tileentities.TileEntityGlowstoneGenerator;
import pjc21.mod.objects.blocks.tileentities.TileEntityMiner;
import pjc21.mod.objects.blocks.tileentities.TileEntitySinteringFurnace;
import pjc21.mod.util.Reference;

public class TileEntityHandler 
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySinteringFurnace.class, new ResourceLocation(Reference.MODID + ":sintering_furnace"));
		GameRegistry.registerTileEntity(TileEntityMiner.class, new ResourceLocation(Reference.MODID + ":miner"));
		GameRegistry.registerTileEntity(TileEntityCopperChest.class, new ResourceLocation(Reference.MODID + ":copper_chest"));
		GameRegistry.registerTileEntity(TileEntityGlowstoneGenerator.class, new ResourceLocation(Reference.MODID + ":glowstone_generator"));
		GameRegistry.registerTileEntity(TileEntityElectricSinteringFurnace.class, new ResourceLocation(Reference.MODID + ":electric_sintering_furnace"));
	}
}
