package org.legion.easyKarma;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Karma {

    public static int getKarma(UUID uuid) {
        return EasyKarma.getInstance().karma.getOrDefault(uuid, 0);
    }

    public static void addKarma(UUID uuid,  Integer amount) {
        EasyKarma.getInstance().karma.put(uuid, getKarma(uuid) + amount);
    }

    public static void takeKarma(UUID uuid,  Integer amount) {
        EasyKarma.getInstance().karma.put(uuid, getKarma(uuid) - amount);
    }

    public static void setKarma(UUID uuid,  Integer amount) {
        EasyKarma.getInstance().karma.put(uuid, amount);
    }
}
