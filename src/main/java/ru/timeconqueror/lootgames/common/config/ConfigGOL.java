package ru.timeconqueror.lootgames.common.config;

import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.timeconqueror.timecore.api.common.config.Config;
import ru.timeconqueror.timecore.api.common.config.ConfigSection;
import ru.timeconqueror.timecore.api.common.config.IQuickConfigValue;
import ru.timeconqueror.timecore.api.common.config.ImprovedConfigBuilder;

public class ConfigGOL extends Config {
    public IQuickConfigValue<Integer> startDigitAmount;
    public IQuickConfigValue<Integer> attemptCount;
    public IQuickConfigValue<Integer> expandFieldAtStage;
    public IQuickConfigValue<Boolean> explodeOnFail;
    public IQuickConfigValue<Boolean> zombiesOnFail;
    public IQuickConfigValue<Boolean> lavaOnFail;
    public IQuickConfigValue<Integer> timeout;

    public StageConfig stage1 = new StageConfig("stage_1", new DefaultData(5, false, 24));
    public StageConfig stage2 = new StageConfig("stage_2", new DefaultData(5, false, 16));
    public StageConfig stage3 = new StageConfig("stage_3", new DefaultData(5, false, 12));
    public StageConfig stage4 = new StageConfig("stage_4", new DefaultData(5, true, 12));

    /**
     * @param type    <i>please, see description in ModConfig.Type</i>
     * @param key     used as a location for the config file (see {@link #getRelativePath()}). Also determines the section in config file and is used as a part of lang keys.
     * @param comment used to provide a comment that can be seen above this section in the config file.
     */
    public ConfigGOL(ModConfig.@NotNull Type type, @NotNull String key, @Nullable String comment) {
        super(type, key, comment);
    }

    @Override
    public void setup(ImprovedConfigBuilder builder) {
        startDigitAmount = builder.optimized(
                builder.comment("Regulates how many digits should be randomly chosen and shown at game-start.")
                        .defineInRange("start_digit_amount", 2, 1, Integer.MAX_VALUE)
        );
        attemptCount = builder.optimized(
                builder.comment("It represents the number of attempts the player has to beat the game successfully.")
                        .defineInRange("attempt_count", 3, 1, Integer.MAX_VALUE)
        );
        expandFieldAtStage = builder.optimized(
                builder.comment("At which stage should the playfield become a full 3x3 pattern?",
                        "Set 0 to disable and keep the 4-block size; set 1 to always start with 3x3.")
                        .defineInRange("expand_field_at_stage", 2, 0, 4)
        );
        explodeOnFail = builder.optimized(
                builder.comment("Enables or disables structure exploding on max failed attempts.")
                        .define("explode_on_fail", true)
        );
        zombiesOnFail = builder.optimized(
                builder.comment("Enables or disables structure filling with zombies on max failed attempts.")
                        .define("zombies_on_fail", true)
        );
        lavaOnFail = builder.optimized(
                builder.comment("Enables or disables structure filling with lava on max failed attempts.")
                        .define("lava_on_fail", true)
        );
        timeout = builder.optimized(
                builder.comment("How long does it take to timeout a game? Value is in seconds.",
                        "If player has been inactive for given time, the game will go to sleep. The next player can start the game from the beginning.")
                        .defineInRange("timeout", 60, 60, Integer.MAX_VALUE)
        );

        builder.addAndSetupSection(stage1, "stage", "Regulates characteristics of stage 1.");
        builder.addAndSetupSection(stage2, "stage", "Regulates characteristics of stage 2.");
        builder.addAndSetupSection(stage3, "stage", "Regulates characteristics of stage 3.");
        builder.addAndSetupSection(stage4, "stage", "Regulates characteristics of stage 4.");
    }

    @Override
    public @NotNull String getRelativePath() {
        return LGConfigs.resolve("games/" + getKey() + ".toml");
    }

    public static class StageConfig extends ConfigSection {
        public IQuickConfigValue<Integer> minRoundsRequiredToPass;
        public IQuickConfigValue<Boolean> randomizeSequence;
        public IQuickConfigValue<Integer> displayTime;

        private final DefaultData defData;

        public StageConfig(String key, DefaultData defData) {
            super(key, null);
            this.defData = defData;
        }

        @Override
        public void setup(ImprovedConfigBuilder builder) {
            minRoundsRequiredToPass = builder.optimized(
                    builder.comment("Minimum correct rounds required to complete this stage and unlock leveled reward.")
                            .defineInRange("min_rounds_required_to_pass", defData.minRoundsRequiredToPass, 1, 256)
            );
            randomizeSequence = builder.optimized(
                    builder.comment("If true, the pattern will randomize on each round in this stage.")
                            .define("randomize_sequence", defData.randomizeSequence)
            );
            displayTime = builder.optimized(
                    builder.comment("Amount of time (in ticks; 20 ticks = 1s) for which the symbol will be displayed.")
                            .defineInRange("display_time", defData.displayTime, 2, 40)
            );
        }
    }

    private static class DefaultData {
        private final int minRoundsRequiredToPass;
        private final boolean randomizeSequence;
        private final int displayTime;

        public DefaultData(int minRoundsRequiredToPass, boolean randomizeSequence, int displayTime) {
            this.minRoundsRequiredToPass = minRoundsRequiredToPass;
            this.randomizeSequence = randomizeSequence;
            this.displayTime = displayTime;
        }
    }
}
