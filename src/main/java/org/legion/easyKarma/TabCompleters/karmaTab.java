package org.legion.easyKarma.TabCompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class karmaTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String alias,
                                                @NotNull String[] args) {

        List<String> completions = new ArrayList<>();

        // /karma <subcommand>
        if (args.length == 1) {
            completions.add("info"); // добавлена новая команда

            if (sender.hasPermission("karma.admin")) {
                completions.add("give");
                completions.add("take");
                completions.add("set");
                completions.add("reload");
            }

            return completions;
        }

        // /karma <subcommand> <player>
        if (args.length == 2) {
            // Для info тоже нужны имена игроков
            if (args[0].equalsIgnoreCase("info") ||
                    args[0].equalsIgnoreCase("give") ||
                    args[0].equalsIgnoreCase("take") ||
                    args[0].equalsIgnoreCase("set")) {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
            return completions;
        }

        // /karma <give|take|set> <player> <amount>
        if (args.length == 3 &&
                (args[0].equalsIgnoreCase("give") ||
                        args[0].equalsIgnoreCase("take") ||
                        args[0].equalsIgnoreCase("set"))) {

            completions.add("10");
            completions.add("50");
            completions.add("100");
            completions.add("500");
            return completions;
        }

        return completions;
    }
}
