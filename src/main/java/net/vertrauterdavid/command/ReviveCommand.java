package net.vertrauterdavid.command;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import net.vertrauterdavid.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ReviveCommand implements CommandExecutor, TabCompleter {

    public ReviveCommand(String name) {
        Objects.requireNonNull(EventCore.getInstance().getCommand(name)).setExecutor(this);
        Objects.requireNonNull(EventCore.getInstance().getCommand(name)).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;
        if (!(player.hasPermission("event.command"))) return false;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("*")) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    PlayerUtil.cleanPlayer(target);
                }

                player.sendMessage(MessageUtil.getPrefix() + "§aEveryone §7has been revived!");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(MessageUtil.getPrefix() + "§cThis player is not online!");
                return false;
            }

            PlayerUtil.cleanPlayer(target);
            player.sendMessage(MessageUtil.getPrefix() + "§a" + target.getName() + " §7has been revived!");
            return false;
        }

        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/revive <player>");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return new ArrayList<>();
        if (!(player.hasPermission("event.command"))) return new ArrayList<>();

        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            list.add("*");
        }

        try {
            Collections.sort(list);
        } catch (Exception ignored) { }
        return list.stream().filter(content -> content.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).sorted().toList();
    }

}
