package pjc21.mod.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pjc21.mod.Main;
import pjc21.mod.commands.CommandDimensionTeleport;
import pjc21.mod.init.BiomeInit;
import pjc21.mod.init.BlockInit;
import pjc21.mod.init.DimensionInit;
import pjc21.mod.init.EntityInit;
import pjc21.mod.init.ItemInit;
import pjc21.mod.init.PotionInit;
import pjc21.mod.init.RecipesInit;
import pjc21.mod.objects.blocks.animation.RenderCopperChest;
import pjc21.mod.objects.blocks.tileentities.TileEntityCopperChest;
import pjc21.mod.util.interfaces.IHasModel;
import pjc21.mod.world.gen.WorldGenCustomOres;
import pjc21.mod.world.gen.WorldGenCustomStructures;
import pjc21.mod.world.types.WorldTypeCopper;
import pjc21.mod.world.types.WorldTypeCustom;


@EventBusSubscriber
public class RegistryHandler 
{

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCopperChest.class, new RenderCopperChest());
	}
			
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ItemInit.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : BlockInit.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}

		}
		EntityInit.registerEntityRenders();
	}
	
	@SubscribeEvent
	public static void onFly(TickEvent.PlayerTickEvent event) 
	{
		boolean fly = false;
		
		if(event.player.isPotionActive(PotionInit.FLY_POTION))
			fly = true;
		if(fly || event.player.isCreative() || event.player.isSpectator()) 
		{
			event.player.capabilities.allowFlying = true;
			event.player.fallDistance = 0.0f;
		} else {
			fly = false;
			event.player.capabilities.isFlying = false;
			event.player.capabilities.allowFlying = false;
		}
	}
	
	public static void preInitRegistries()
	{
		EntityInit.registerEntities();
		PotionInit.registerPotions();
		
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		//GameRegistry.registerWorldGenerator(new WorldGenCustomTrees(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);

		BiomeInit.registerBiomes();
		DimensionInit.registerDimension();
		FluidHandler.registerFluids();
	}
	
	public static void initRegistries()
	{
		TileEntityHandler.registerTileEntities();
		RecipesInit.registerRecipes();
		SoundsHandler.registerSounds();
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
		
	}
	
	public static void postInitRegistries()
	{
		WorldType COPPER = new WorldTypeCopper();
		WorldType CUSTOM = new WorldTypeCustom();
		
	}
	
	public static void serverRegistries(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandDimensionTeleport());
	}
}
