package net.omega.mafia.util;

import net.omega.mafia.data.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoleDistributor {

    /**
     * Algorithmically generates a randomized list of roles based on player count.
     * @param playerCount The total number of players (minimum 6).
     * @return A shuffled List of Role enums.
     */
    public static List<Role> generateRoleList(int playerCount) {
        if (playerCount < 6) {
            throw new IllegalArgumentException("Mafia requires a minimum of 6 players.");
        }

        List<Role> roles = new ArrayList<>();

        int totalMafia = (playerCount < 8) ? 1 : (playerCount / 4);

        if (totalMafia > 1) {
            roles.add(Role.BOSS);
            for (int i = 0; i < totalMafia - 1; i++) {
                roles.add(Role.MAFIA);
            }
        } else {
            roles.add(Role.MAFIA);
        }

        if (playerCount >= 6) {
            roles.add(Role.DETECTIVE);
            roles.add(Role.MEDIC);
        }
        if (playerCount >= 7) {
            roles.add(Role.ESCORT);
        }

        int currentSize = roles.size();
        for (int i = 0; i < (playerCount - currentSize); i++) {
            roles.add(Role.NONE);
        }

        Collections.shuffle(roles);
        return roles;
    }
}