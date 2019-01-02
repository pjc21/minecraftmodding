package pjc21.mod.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import pjc21.mod.Main;
import pjc21.mod.init.BlockInit;
import pjc21.mod.init.ItemInit;
import pjc21.mod.objects.blocks.item.ItemBlockVariants;
import pjc21.mod.util.handlers.EnumDirtHandler;
import pjc21.mod.util.handlers.EnumHandler;
import pjc21.mod.util.interfaces.IHasModel;
import pjc21.mod.util.interfaces.IMetaName;

public class BlockDirts extends Block implements IMetaName, IHasModel
{
	public static final PropertyEnum<EnumDirtHandler.EnumType> VARIANT = PropertyEnum.<EnumDirtHandler.EnumType>create("variant", EnumDirtHandler.EnumType.class);
	
	private String name;
	
	public BlockDirts(String name)
	{
		super(Material.GROUND);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.GROUND);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumDirtHandler.EnumType.RED));
		setCreativeTab(Main.PAULSTAB);
		
		this.name = name;
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public int damageDropped(IBlockState state) 
	{
		return ((EnumDirtHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) 
	{
		for(EnumDirtHandler.EnumType dirthandler$enumtype : EnumDirtHandler.EnumType.values())
		{
			items.add(new ItemStack(this, 1, dirthandler$enumtype.getMeta()));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState().withProperty(VARIANT, EnumDirtHandler.EnumType.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return ((EnumDirtHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
		
	@Override
	public String getSpecialName(ItemStack stack) 
	{
		return EnumDirtHandler.EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) 
	{
		return true;
}
	
	@Override
	public void registerModels() 
	{
		for(int i = 0; i < EnumDirtHandler.EnumType.values().length; i++)
		{
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "dirts_" + EnumDirtHandler.EnumType.values()[i].getName(), "inventory");
		}
	}
}
