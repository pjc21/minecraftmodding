package pjc21.mod.objects.blocks.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import pjc21.mod.objects.blocks.container.ContainerGlowstoneGenerator;
import pjc21.mod.objects.blocks.tileentities.TileEntityGlowstoneGenerator;
import pjc21.mod.util.Reference;

public class GuiGlowstoneGenerator extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/glowstone_generator.png");
	private final InventoryPlayer player;
	private final TileEntityGlowstoneGenerator tileentity;
	
	public GuiGlowstoneGenerator(InventoryPlayer player, TileEntityGlowstoneGenerator tileentity) 
	{
		super(new ContainerGlowstoneGenerator(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = this.tileentity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) -5, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileentity.getEnergyStored()), 115, 72, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 113, this.guiTop + 32, 176, 14, l + 1, 16);

		int k = this.getEnergyStoredScaled(76);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 7, 176, 31, 16, 76 - k);
	}

	private int getEnergyStoredScaled(int pixels)
	{
		
		int j = this.tileentity.getMaxEnergyStored();
		int i = this.tileentity.getEnergyStored();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int m = this.tileentity.getCookTime();
		return m != 1 ? m * pixels / 25 : 0;
	}
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        
        if (mouseX > this.guiLeft + 151 && mouseX < this.guiLeft + 152 + 16 && mouseY > this.guiTop + 6 && mouseY < this.guiTop + 4 + 76)
        {
            this.drawHoveringText(Integer.toString(this.tileentity.energy) + " FluxEnergy", mouseX, mouseY);
        }
    }
}
