package net.omega.mafia.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.omega.mafia.data.Role;

public class NapkinMenu extends AbstractContainerMenu {

    private final Role role;

    public NapkinMenu(int containerId, Inventory playerInventory, Role role) {
        super(ModMenus.NAPKIN_MENU.get(), containerId);
        this.role = role;
    }

    // Server-side constructor
    public NapkinMenu(int containerId, Role role) {
        super(ModMenus.NAPKIN_MENU.get(), containerId);
        this.role = role;
    }

    public NapkinMenu(int containerId) {
        super(ModMenus.NAPKIN_MENU.get(), containerId);
        this.role = Role.NONE;
    }

    // Getter so the Screen class can access it
    public Role getRole() {
        return this.role;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}