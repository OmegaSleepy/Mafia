package net.omega.mafia.components;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.omega.mafia.Mafia;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Mafia.MODID);

//    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ROLL = register("roll",
//            builder -> builder.persistent(Codec.INT));
//
//    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TICK_TIMER = register("tick_timer",
//            builder -> builder.persistent(Codec.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ROLE = register("role",
            builder -> builder.persistent(Codec.STRING));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            String name,
            UnaryOperator<DataComponentType.Builder<T>> builderOperator
    ) {
        return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }


    public static void register (IEventBus eventBus) {
        DATA_COMPONENT_TYPE.register(eventBus);
    }
}
