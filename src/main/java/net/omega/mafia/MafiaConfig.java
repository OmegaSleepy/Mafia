package net.omega.mafia;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MafiaConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // Config values
//    public static final BooleanValue SPECIFIC_FEATURE_ENABLED;
    public static final ModConfigSpec.IntValue MINIMUM_PLAYER_COUNT;

    public static final ModConfigSpec.IntValue VOTING_TIME;
    public static final ModConfigSpec.IntValue NIGHT_ACTION_TIME;
    public static final ModConfigSpec.IntValue PRE_DEATH_ANNOUNCE;
    public static final ModConfigSpec.IntValue FREE_ROAM_TIME;
    public static final ModConfigSpec.IntValue DISCUSSION_TIME;


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
                .defineInRange("minMafiaPlayers", 6, 6, 20);

        VOTING_TIME = BUILDER
                .comment("The time players have to vote for a person to go out. (in ticks)")
                .defineInRange("timeForVoting", 6000, 3000, 12000);

        NIGHT_ACTION_TIME = BUILDER
                .comment("The time players have to do their actions during the night. (in ticks)")
                .defineInRange("timeForVoting", 8000, 4000, 12000);

        PRE_DEATH_ANNOUNCE = BUILDER
                .comment("The time players have before the dead guests are announced. (in ticks)")
                .defineInRange("timeForVoting", 1000, 4000, 12000);

        FREE_ROAM_TIME = BUILDER
                .comment("The time players have to go around the hotel and discuss in private. (in ticks)")
                .defineInRange("timeForVoting", 8000, 4000, 24000);

        DISCUSSION_TIME = BUILDER
                .comment("The time players have to discuss who to vote out during dinner. (in ticks)")
                .defineInRange("timeForVoting", 6000, 4000, 12000);
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

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}