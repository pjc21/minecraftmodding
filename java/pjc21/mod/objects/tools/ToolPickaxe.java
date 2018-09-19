package pjc21.mod.objects.tools;

import net.minecraft.item.ItemPickaxe;
import pjc21.mod.Main;
import pjc21.mod.init.ItemInit;
import pjc21.mod.util.interfaces.IHasModel;

public class ToolPickaxe extends ItemPickaxe implements IHasModel 
{
	public ToolPickaxe(String name, ToolMaterial material) 
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
