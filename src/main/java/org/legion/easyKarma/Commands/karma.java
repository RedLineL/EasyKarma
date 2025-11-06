package org.legion.easyKarma.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.legion.easyKarma.EasyKarma;

import static org.legion.easyKarma.Karma.getKarma;
import static org.legion.easyKarma.Karma.setKarma;
import static org.legion.easyKarma.Utils.Util.color;

public class karma implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        String prefix = EasyKarma.getInstance().getConfig().getString("messages.prefix");

        // ---- /karma (own info) ----
        if (args.length == 0) {
            if (sender instanceof Player player) {
                int karma = getKarma(player.getUniqueId());
                String msg = EasyKarma.getInstance().getConfig().getString("messages.karma-view")
                        .replace("%karma%", String.valueOf(karma))
                        .replace("%prefix%", prefix);
                player.sendMessage(color(msg));
            } else {
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.only-player")
                        .replace("%prefix%", prefix)));
            }
            return true;
        }

        // ---- /karma info [player] ----
        if (args[0].equalsIgnoreCase("info")) {

            // /karma info (own)
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.only-player")
                            .replace("%prefix%", prefix)));
                    return true;
                }

                Player player = (Player) sender;
                int karma = getKarma(player.getUniqueId());
                String msg = EasyKarma.getInstance().getConfig().getString("messages.karma-view")
                        .replace("%karma%", String.valueOf(karma))
                        .replace("%prefix%", prefix);
                player.sendMessage(color(msg));
                return true;
            }

            // /karma info <player> (admin only)
            if (!sender.hasPermission("karma.admin")) {
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.not-enough-permission")
                        .replace("%prefix%", prefix)));
                return true;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            if (target == null || (!target.hasPlayedBefore() && !target.isOnline())) {
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.player-not-found")
                        .replace("%player%", args[1])
                        .replace("%prefix%", prefix)));
                return true;
            }

            int karma = getKarma(target.getUniqueId());
            String msg = EasyKarma.getInstance().getConfig().getString("messages.karma-view-other")
                    .replace("%player%", target.getName() != null ? target.getName() : args[1])
                    .replace("%karma%", String.valueOf(karma))
                    .replace("%prefix%", prefix);
            sender.sendMessage(color(msg));
            return true;
        }

        // ---- /karma reload ----
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("karma.admin")) {
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.not-enough-permission")
                        .replace("%prefix%", prefix)));
                return true;
            }
            EasyKarma.getInstance().reloadConfig();
            sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.reload")
                    .replace("%prefix%", prefix)));
            return true;
        }

        // ---- /karma give|take|set <player> <amount> ----
        if (args.length < 3) {
            sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.usage")
                    .replace("%prefix%", prefix)));
            return true;
        }

        String action = args[0];
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.player-not-found")
                    .replace("%player%", args[1])
                    .replace("%prefix%", prefix)));
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.invalid-number")
                    .replace("%prefix%", prefix)));
            return true;
        }

        int newKarma = getKarma(target.getUniqueId());

        switch (action.toLowerCase()) {
            case "give" -> {
                newKarma += amount;
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.admin-give")
                        .replace("%amount%", String.valueOf(amount))
                        .replace("%player%", target.getName())
                        .replace("%prefix%", prefix)));
                target.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.you-got-karma")
                        .replace("%amount%", String.valueOf(amount))
                        .replace("%prefix%", prefix)));
            }
            case "take" -> {
                newKarma -= amount;
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.admin-take")
                        .replace("%amount%", String.valueOf(amount))
                        .replace("%player%", target.getName())
                        .replace("%prefix%", prefix)));
                target.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.you-lost-karma")
                        .replace("%amount%", String.valueOf(amount))
                        .replace("%prefix%", prefix)));
            }
            case "set" -> {
                newKarma = amount;
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.admin-set")
                        .replace("%amount%", String.valueOf(amount))
                        .replace("%player%", target.getName())
                        .replace("%prefix%", prefix)));
                target.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.you-karma-set")
                        .replace("%amount%", String.valueOf(amount))
                        .replace("%prefix%", prefix)));
            }
            default -> {
                sender.sendMessage(color(EasyKarma.getInstance().getConfig().getString("messages.unknown-action")
                        .replace("%prefix%", prefix)));
                return true;
            }
        }

        setKarma(target.getUniqueId(), newKarma);
        return true;
    }
}
