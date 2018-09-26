package pjc21.mod.objects.fluids;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import pjc21.mod.Main;
import pjc21.mod.init.ItemInit;
import pjc21.mod.util.interfaces.IHasModel;

public class BlockFluidOil extends BlockFluidClassic implements IHasModel
{

	public BlockFluidOil(String name, Fluid fluid, Material material) 
	{
		super(fluid, material);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(Main.PAULSTAB);
		System.out.println("BlockFluid");

		//BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName().getResourcePath()));
	}
	
	@Override
    public int place(World world, BlockPos pos, @Nonnull FluidStack fluidStack, boolean doPlace)
    {
		if (doPlace)
        {
			FluidUtil.destroyBlockOnFluidPlacement(world, pos);
            world.setBlockState(pos, this.getDefaultState(), 11);
        }
        return fluidStack.amount;
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
    {
    	return new Vec3d(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue());
    }*/

    @Override
    public void registerModels() 
    {
    	Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

} 
	

