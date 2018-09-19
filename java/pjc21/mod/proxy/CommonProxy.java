package pjc21.mod.proxy;

import net.minecraft.item.Item;
import pjc21.mod.util.handlers.TileEntityHandler;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void registerVariantRenderer(Item item, int meta, String filename, String id) {}
	
	/*public void registerTileEntities() 
	{
		TileEntityHandler.registerTileEntities();
	}
	
	public void Register(Object o) 
	{
		
	}*/
}
