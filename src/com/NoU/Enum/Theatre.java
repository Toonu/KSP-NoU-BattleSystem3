package com.NoU.Enum;

/**
 * @author Toonu
 * <p>
 * Enum representing area of operations for crafts.
 */
@SuppressWarnings("SpellCheckingInspection")
public enum Theatre {
    GROUND(" GRND"), AERIAL("  AIR"), NAVAL("NAVAL"), SPACE("SPACE");

    private final String nickname;

    Theatre(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return nickname;
    }
}
