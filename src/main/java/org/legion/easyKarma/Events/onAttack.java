package org.legion.easyKarma.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.legion.easyKarma.EasyKarma;

import static org.legion.easyKarma.Karma.getKarma;

public class onAttack implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player p) {
            int karma = getKarma(p.getUniqueId());

            double karmaBonus = EasyKarma.getInstance().getConfig().getDouble("config.positive-effects.damage-bonus-per-point");

            if (karma > 0) event.setDamage(event.getDamage() * (1 + karma * karmaBonus));
        }
    }
}
