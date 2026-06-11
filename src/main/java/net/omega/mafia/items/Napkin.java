package net.omega.mafia.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.omega.mafia.components.ModDataComponents;
import net.omega.mafia.data.Role;
import net.omega.mafia.menu.NapkinMenu;

import java.util.Arrays;
import java.util.List;

public class Napkin extends Item {

    public Napkin () {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        String role = itemStack.get(ModDataComponents.ROLE);
        role = role == null ? "none" : role;
        role = Arrays.stream(Role.values()).map(Role::name).toList().contains(role.toUpperCase()) ? role : "none";
        Role roleEnum = Role.valueOf(role.toUpperCase());

        if (!level.isClientSide) {
            player.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, serverPlayer) -> new NapkinMenu(containerId, roleEnum),
                    Component.translatable("napkin.title.menu")
            ), buffer -> buffer.writeUtf(roleEnum.name()));
        }

        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public boolean onDroppedByPlayer (ItemStack item, Player player) {
        player.drop(item, false);
        player.addItem(item.copy());
        return false;
    }

    public void setString (ItemStack itemStack, String string) {
        itemStack.set(ModDataComponents.ROLE, string);
    }

    @Override
    public void appendHoverText (ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        if (stack.get(ModDataComponents.ROLE) != null) {
            tooltipComponents.add(Component.literal("YOU ARE A " + stack.get(ModDataComponents.ROLE)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
