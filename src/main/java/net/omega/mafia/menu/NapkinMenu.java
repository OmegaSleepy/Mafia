package net.omega.mafia.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class NapkinMenu extends AbstractContainerMenu {

    // Client-side constructor called by NeoForge
    public NapkinMenu(int containerId, Inventory playerInventory) {
        this(containerId);
    }

    // Main constructor
    public NapkinMenu(int containerId) {
        super(ModMenus.NAPKIN_MENU.get(), containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY; // No slots, so no quick-moving logic needed
    }

    @Override
    public boolean stillValid(Player player) {
        return true; // Keeps the UI open
    }
}