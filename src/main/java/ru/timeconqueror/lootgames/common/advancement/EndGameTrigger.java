package ru.timeconqueror.lootgames.common.advancement;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.lootgames.LootGames;
import ru.timeconqueror.timecore.api.common.advancement.criteria.TimeSimpleTrigger;

import static ru.timeconqueror.lootgames.common.advancement.EndGameTrigger.Instance;

public class EndGameTrigger extends TimeSimpleTrigger<String, Instance> {
    public static final String TYPE_ANY = "any";
    public static final String TYPE_WIN = "win";
    public static final String TYPE_LOSE = "lose";
    private static final ResourceLocation ID = LootGames.INSTANCE.createRl("end_minigame");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     */
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new Instance(json.has("type") ? JSONUtils.getString(json, "type") : TYPE_ANY);
    }

    @Override
    public PerPlayerListenerSet<String, Instance> createListenerSet(PlayerAdvancements advancements) {
        return null;
    }

    public static class Instance extends TimeSimpleTrigger.TimeCriterionInstance<String> {
        private String type;

        public Instance(@NotNull String type) {
            super(ID);
            this.type = type;
        }

        @Override
        public boolean test(ServerPlayerEntity player, String type) {
            return this.type.equals(TYPE_ANY) || this.type.equals(type);
        }

        @Override
        public JsonElement serialize() {
            JsonObject root = new JsonObject();
            root.addProperty("type", type);

            return root;
        }
    }
}