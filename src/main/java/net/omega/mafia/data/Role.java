package net.omega.mafia.data;

public enum Role {
    MAFIA("napkin.message.mafia"),
    BOSS("napkin.message.boss"),
    DETECTIVE("napkin.message.detective"),
    MEDIC("napkin.message.medic"),
    STRIPPER("napkin.message.stripper"),
    NONE("napkin.message.none");

    public final String translationKey;

    Role (String s) {
        translationKey = s;
    }
}
