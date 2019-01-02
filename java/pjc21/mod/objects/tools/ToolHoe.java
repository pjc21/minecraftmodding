package pjc21.mod.objects.tools;

import net.minecraft.item.ItemHoe;
import pjc21.mod.Main;
import pjc21.mod.init.ItemInit;
import pjc21.mod.util.interfaces.IHasModel;

public class ToolHoe extends ItemHoe implements IHasModel
{

	public ToolHoe(String name, ToolMaterial material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.PAULSTAB);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
