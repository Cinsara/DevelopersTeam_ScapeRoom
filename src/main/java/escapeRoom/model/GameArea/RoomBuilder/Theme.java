package escapeRoom.model.GameArea.RoomBuilder;

public enum Theme {
    LOVEAFFAIR("Love Affair"),
    FANTASTIC("Fantastic"),
    MYSTERY("Mystery"),
    SCIFI("Sci-Fi");

    private final String displayName;

    Theme(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Theme fromString(String text) {
        for (Theme theme : Theme.values()) {
            if (theme.displayName.equalsIgnoreCase(text)) {
                return theme;
            }
        }
        throw new IllegalArgumentException("No theme found for: " + text);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
