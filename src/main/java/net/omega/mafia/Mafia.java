package net.omega.mafia;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.omega.mafia.commands.ModCommands;
import net.omega.mafia.components.ModDataComponents;
import net.omega.mafia.items.ModItems;
import net.omega.mafia.logic.MafiaGame;
import net.omega.mafia.menu.ModMenus;
import org.slf4j.Logger;
import org.slf4j.MDC;

@Mod(Mafia.MODID)
public class Mafia {
    public static final String MODID = "mafia";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Mafia (IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModDataComponents.register(modEventBus);
        ModMenus.register(modEventBus);
        ModItems.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, MafiaConfig.SPEC);

    }

    private void commonSetup (final FMLCommonSetupEvent event) {

    }
}
