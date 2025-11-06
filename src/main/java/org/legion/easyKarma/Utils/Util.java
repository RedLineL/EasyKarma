package org.legion.easyKarma.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.legion.easyKarma.EasyKarma;

import java.util.UUID;

public class Util {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&' , message);
    }

    public static void sendPathMessage(UUID uuid, String path) {
        Player player = Bukkit.getPlayer(uuid);
        player.sendMessage(color((String) EasyKarma.getInstance().getConfig().get(path))
                .replace("%prefix%", EasyKarma.getInstance().getConfig().getString("messages.prefix")));
    }
}
