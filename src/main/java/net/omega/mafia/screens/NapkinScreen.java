package net.omega.mafia.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.omega.mafia.menu.NapkinMenu;

import java.util.List;

public class NapkinScreen extends AbstractContainerScreen<NapkinMenu> {
    // 1. Define your texture path: assets/mafia/textures/gui/napkin_gui.png
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mafia", "textures/gui/napkin_gui.png");

    public NapkinScreen(NapkinMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        // 2. Setting your custom texture dimensions
        this.imageWidth = 176;
        this.imageHeight = 166;

        // Moves the "Inventory" label out of the way if you aren't rendering standard player inventory slots
        this.inventoryLabelY = 1000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Bind and draw your texture
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // Draws the texture
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Center the title horizontally at the top
        int titleWidth = this.font.width(this.title);
        guiGraphics.drawString(this.font, this.title, (this.imageWidth - titleWidth) / 2, 8, 0x404040, false);

        // Handle text wrapping for the dynamic message
        Component dynamicMessage = Component.translatable(this.menu.getRole().translationKey());

        int maxWidth = this.imageWidth - 24; // Give it 12px padding on both sides
        List<FormattedCharSequence> wrappedLines = this.font.split(dynamicMessage, maxWidth);

        // Render the wrapped lines centered vertically and horizontally
        int textX = 12;
        int startY = (this.imageHeight / 2) - ((wrappedLines.size() * this.font.lineHeight) / 2);

        for (int i = 0; i < wrappedLines.size(); i++) {
            FormattedCharSequence line = wrappedLines.get(i);
            int lineWidth = this.font.width(line);
            int centeredX = textX + (maxWidth - lineWidth) / 2;

            guiGraphics.drawString(this.font, line, centeredX, startY + (i * this.font.lineHeight), 0x303030, false);
        }
    }
}