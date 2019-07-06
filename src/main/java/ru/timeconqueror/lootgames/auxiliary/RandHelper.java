package ru.timeconqueror.lootgames.auxiliary;

import ru.timeconqueror.lootgames.LootGames;

public class RandHelper {
    /**
     * Returns {@code a} with 50% chance otherwise return {@code b}
     */
    public static <T> T flipCoin(T a, T b) {
        return chance(50, a, b);
    }

    /**
     * Returns {@code a} with {@code chance}% (from 0 to 100) otherwise return {@code b}
     */
    public static <T> T chance(int chance, T a, T b) {
        return LootGames.rand.nextInt(100) < chance ? a : b;
    }
}
