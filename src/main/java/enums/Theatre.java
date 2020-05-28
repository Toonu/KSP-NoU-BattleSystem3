package enums;

/**
 * @author Toonu
 * <p>
 * enums representing area of operations for crafts.
 */
@SuppressWarnings("SpellCheckingInspection")
public enum Theatre {
    GROUND("GRND"), AERIAL("AIR"), NAVAL("NAVAL"), SPACE("SPACE");

    private final String nickname;

    Theatre(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return String.format("%-5s |", nickname);
    }
}
