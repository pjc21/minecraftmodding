package pjc21.mod;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import pjc21.mod.init.FluidInit;
import pjc21.mod.proxy.CommonProxy;
import pjc21.mod.util.Reference;
import pjc21.mod.util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)

public class Main 
{
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	public static final CreativeTabs PAULSTAB = new Pjc21Tab("paulstab");
	
	static
    {
        FluidRegistry.enableUniversalBucket();
    } 
	
	public static Item setItemName(Item parItem, String parItemName)
    {
        parItem.setRegistryName(parItemName);
        parItem.setUnlocalizedName(parItemName);
        return parItem;
    }
	
	public static Block setBlockName(Block parBlock, String parBlockName)
    {
        parBlock.setRegistryName(parBlockName);
        parBlock.setUnlocalizedName(parBlockName);
        return parBlock;
    }
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		RegistryHandler.preInitRegistries();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) 
	{
		RegistryHandler.initRegistries();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) 
	{
		RegistryHandler.postInitRegistries();
	}
	
	@EventHandler
	public static void serverInit(FMLServerStartingEvent event) 
	{
		RegistryHandler.serverRegistries(event);
	}
}
