package net.omega.mafia;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MafiaConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // Config values
//    public static final BooleanValue SPECIFIC_FEATURE_ENABLED;
    public static final ModConfigSpec.IntValue MINIMUM_PLAYER_COUNT;
//    public static final ConfigValue<String> GREETING_MESSAGE;
//    public static final ConfigValue<List<? extends String>> ALLOWED_ITEMS;

    static {
        BUILDER.comment("General settings for Example Mod").push("general");

//        SPECIFIC_FEATURE_ENABLED = BUILDER
//                .comment("Should the awesome feature be enabled?")
//                .define("enableAwesomeFeature", true);
//
        MINIMUM_PLAYER_COUNT = BUILDER
                .comment("The minimum number of players to use for the game of Mafia.")
                .defineInRange("maxLuckyNumbers", 6, 6, 20);
//
//        GREETING_MESSAGE = BUILDER
//                .comment("The message shown to players when they log in.")
//                .define("greetingMessage", "Hello from Example Mod!");
//
//        ALLOWED_ITEMS = BUILDER
//                .comment("A list of item IDs allowed in the custom machine.")
//                .defineListAllowEmpty("allowedItems",
//                        List.of("minecraft:diamond", "minecraft:iron_ingot"),
//                        obj -> obj instanceof String);

        BUILDER.pop(); // Closes the "general" section
        SPEC = BUILDER.build(); // Compiles everything into the final SPEC
    }
}