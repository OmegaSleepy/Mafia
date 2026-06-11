package net.omega.mafia.data;

public enum Role {
    MAFIA,
    BOSS,
    DETECTIVE,
    MEDIC,
    ESCORT,
    NONE;

    public String translationKey () {
        return "napkin.message." + name().toLowerCase();
    }

    public String titleKey () {
        return "napkin.title." + name().toLowerCase();
    }

}
