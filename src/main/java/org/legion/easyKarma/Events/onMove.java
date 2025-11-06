package org.legion.easyKarma.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.legion.easyKarma.EasyKarma;

import static org.legion.easyKarma.Karma.getKarma;

public class onMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        int karma = getKarma(player.getUniqueId());

        double lightningStart = EasyKarma.getInstance().getConfig().getDouble("config.start-effects.lightning");
        double lightningChance = EasyKarma.getInstance().getConfig().getDouble("config.negative-effects.lightning-chance");

        //Bukkit.getLogger().info(String.valueOf(lightningChance) + Math.random());

        if (karma < lightningStart && Math.random() < lightningChance) {
            player.getWorld().strikeLightningEffect(player.getLocation());
        }
    }
}
