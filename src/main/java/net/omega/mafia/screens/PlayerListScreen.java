package net.omega.mafia.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.omega.mafia.network.PlayerClickedPayload;

import java.util.Collection;

public class PlayerListScreen extends Screen {

    private static final int ICON_SIZE = 32;
    private static final int PADDING = 10;
    private static final int COLS = 5; // Number of player faces per row

    public PlayerListScreen() {
        super(Component.literal("Online Players"));
    }

    @Override
    protected void init() {
        super.init();
        if (this.minecraft == null || this.minecraft.getConnection() == null) return;

        // Fetch all online players from the client connection
        Collection<PlayerInfo> players = this.minecraft.getConnection().getOnlinePlayers();

        int index = 0;
        // Center the grid dynamically based on columns
        int startX = (this.width - (COLS * (ICON_SIZE + PADDING) - PADDING)) / 2;
        int startY = 50;

        for (PlayerInfo player : players) {
            int col = index % COLS;
            int row = index / COLS;

            int x = startX + col * (ICON_SIZE + PADDING);
            int y = startY + row * (ICON_SIZE + PADDING);

            // Add a custom button for each player face
            this.addRenderableWidget(new PlayerFaceButton(x, y, player));
            index++;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Render default dirt background (or use renderTransparentBackground for in-game overlay)
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw Title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Set to true if you want the game to pause in singleplayer
    }

    // --- Inner Custom Button Class ---
    private static class PlayerFaceButton extends Button {
        private final PlayerInfo playerInfo;

        public PlayerFaceButton(int x, int y, PlayerInfo playerInfo) {
            super(x, y, ICON_SIZE, ICON_SIZE, Component.literal(playerInfo.getProfile().getName()), 
                  button -> handlePlayerClick(playerInfo), DEFAULT_NARRATION);
            this.playerInfo = playerInfo;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // Render the 2D Player Face skin texture
            PlayerFaceRenderer.draw(
                guiGraphics, 
                this.playerInfo.getSkin().texture(), 
                this.getX(), 
                this.getY(), 
                ICON_SIZE, 
                true, // Upside down fix (keep true)
                false // Rendering inside a menu (not a skull item block)
            );

            // Optional: Draw a hover overlay effect
            if (this.isHoveredOrFocused()) {
                guiGraphics.fill(this.getX(), this.getY(), this.getX() + ICON_SIZE, this.getY() + ICON_SIZE, 0x40FFFFFF);
                
                // Set the tooltip to show their username on hover
                setTooltip(net.minecraft.client.gui.components.Tooltip.create(Component.literal(playerInfo.getProfile().getName())));
            }
        }
    }

    private static void handlePlayerClick(PlayerInfo player) {
        if (Minecraft.getInstance().player != null) {
            java.util.UUID targetUuid = player.getProfile().getId();

            net.neoforged.neoforge.network.PacketDistributor.sendToServer(
                    new PlayerClickedPayload(targetUuid)
            );
            Minecraft.getInstance().setScreen(null);
        }
    }
}