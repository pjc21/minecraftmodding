package pjc21.mod.objects.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import pjc21.mod.Main;
import pjc21.mod.init.ItemInit;
import pjc21.mod.util.interfaces.IHasModel;

public class ToolAxe extends ItemAxe implements IHasModel 
{
	public ToolAxe(String name, ToolMaterial material, float damage, float speed) 
	{
		super(material, 7f, -3.1f);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHarvestLevel("axe", 0);
		setCreativeTab(Main.PAULSTAB);
		
		ItemInit.ITEMS.add(this);
	}

	public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();
        return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE ? super.getDestroySpeed(stack, state) : this.efficiency;
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
