package pjc21.mod.objects.fluids;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistryEntry;
import pjc21.mod.init.BlockInit;

public class FluidBase extends Fluid 
{

	protected static int mapColor = 0xFFFFFFFF;
    protected static float overlayAlpha = 0.2F;
    protected static SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
    protected static SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
    //protected static Material material = Material.WATER;
    protected boolean bucketEnabled = false;
    protected Block block = null;
	
	public FluidBase(String name, ResourceLocation still, ResourceLocation flowing) 
	{
		super(name, still, flowing);
	}

	public FluidBase(String name, ResourceLocation still, ResourceLocation flowing, int color) 
	{
		super(name, still, flowing);
		setColor(color);
	}
	
	public FluidBase(String name, ResourceLocation still, ResourceLocation flowing, int color, float overlayAlpha)
	{
		super(name, still, flowing, color);
		setAlpha(overlayAlpha);
	}

	@Override
	public int getColor() 
	{
		return color;
	}
	
	public FluidBase setColor(int parColor) 
	{
		color = parColor;
		return this;
	}
	
	public float getAlpha()
    {
        return overlayAlpha;
    }
	
	public FluidBase setAlpha(float parOverlayAlpha)
    {
        overlayAlpha = parOverlayAlpha;
        return this;
    }

	@Override
	public FluidBase setEmptySound(SoundEvent parSound) 
	{
		emptySound = parSound;
		return this;
	}
	
	@Override
	public SoundEvent getEmptySound() 
	{
		return emptySound;
	}
	
	@Override
	public Fluid setFillSound(SoundEvent parSound) 
	{
		fillSound = parSound;
		return this;
	}
	
	@Override
	public SoundEvent getFillSound() 
	{
		return fillSound;
	}
	
	/*public static void setMaterial(Material material) 
	{
		FluidBase.material = material;
	}
	
	public static Material getMaterial() 
	{
		return material;
	}*/
	
	public FluidBase setHasBucket(boolean parEnableBucket)
    {
        bucketEnabled = parEnableBucket;
        return this;
    }

    public boolean isBucketEnabled()
    {
        return bucketEnabled;
    }


	
	/*@Override
	public boolean doesVaporize(FluidStack fluidStack) 
	{
		if(block == null)
		{
			return false;
		}
		
		return block.getDefaultState().getMaterial() == getMaterial();
	}*/
	
	
	
}
