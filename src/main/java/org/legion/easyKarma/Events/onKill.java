package org.legion.easyKarma.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.legion.easyKarma.EasyKarma;

import static org.legion.easyKarma.Karma.addKarma;

public class onKill implements Listener {

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer == null) return;

        FileConfiguration config = EasyKarma.getInstance().getConfig();

        if (e.getEntity() instanceof Player) {
            int value = config.getInt("config.values.kill-player", -5);
            addKarma(killer.getUniqueId(), value);
            return;
        }

        EntityType type = e.getEntityType();

        if (config.contains("config.mob." + type.name())) {
            int value = config.getInt("config.mob." + type.name());
            addKarma(killer.getUniqueId(), value);
        } else {
            int defaultValue = config.getInt("config.values.kill-mob", -1);
            addKarma(killer.getUniqueId(), defaultValue);
        }
    }
}