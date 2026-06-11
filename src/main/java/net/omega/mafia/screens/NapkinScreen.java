package net.omega.mafia.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.omega.mafia.menu.NapkinMenu;

public class NapkinScreen extends AbstractContainerScreen<NapkinMenu> {

    public NapkinScreen(NapkinMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Draw a dark translucent background box for the text
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        guiGraphics.fill(x, y, x + this.imageWidth, y + this.imageHeight, 0xD0000000); 
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xFFFFFF);

        String translationKey = this.menu.getRole().translationKey;

        Component dynamicMessage = Component.translatable(translationKey);

        int textWidth = this.font.width(dynamicMessage);

        guiGraphics.drawString(
                this.font,
                dynamicMessage,
                (this.imageWidth - textWidth) / 2,
                this.imageHeight / 2,
                0xAAAAAA
        );
    }
}