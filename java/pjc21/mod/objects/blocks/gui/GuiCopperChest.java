package pjc21.mod.objects.blocks.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import pjc21.mod.objects.blocks.container.ContainerCopperChest;
import pjc21.mod.objects.blocks.tileentities.TileEntityCopperChest;
import pjc21.mod.util.Reference;

public class GuiCopperChest extends GuiContainer
{
	private static final ResourceLocation GUI_CHEST = new ResourceLocation(Reference.MODID + ":textures/gui/copper_chest.png");
	private final InventoryPlayer playerInventory;
	private final TileEntityCopperChest te;
	
	public GuiCopperChest(InventoryPlayer playerInventory, TileEntityCopperChest chestInventory, EntityPlayer player) 
	{
		super(new ContainerCopperChest(playerInventory, chestInventory, player));
		this.playerInventory = playerInventory;
		this.te = chestInventory;
		
		this.xSize = 179;
		this.ySize = 256;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		this.fontRenderer.drawString(this.te.getDisplayName().getFormattedText(), 8, 6, 16086784);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 92, 16086784);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(GUI_CHEST);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	
}
