package org.legion.easyKarma.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.legion.easyKarma.EasyKarma;

import static org.legion.easyKarma.Karma.getKarma;

public class onMobTarget implements Listener {
    @EventHandler
    public void onMobTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player player) {
            int karma = getKarma(player.getUniqueId());

            int mobAggroChance = EasyKarma.getInstance().getConfig().getInt("config.negative-effects.mob-aggro-chance");

            if (karma < 0 && Math.random() < mobAggroChance) {
                event.setCancelled(false);
            }
        }
    }
}
