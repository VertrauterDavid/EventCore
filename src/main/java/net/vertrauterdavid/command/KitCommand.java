package net.vertrauterdavid.command;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KitCommand implements CommandExecutor, TabCompleter {

    public KitCommand(String name) {
        Objects.requireNonNull(EventCore.getInstance().getCommand(name)).setExecutor(this);
        Objects.requireNonNull(EventCore.getInstance().getCommand(name)).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;
        if (!(player.hasPermission("event.command"))) return false;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("*")) {
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> EventCore.getInstance().getKitManager().give(onlinePlayer));
                player.sendMessage(MessageUtil.getPrefix() + "§aEveryone §7has been equipped!");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(MessageUtil.getPrefix() + "§cThis player is not online!");
                return false;
            }

            EventCore.getInstance().getKitManager().give(target);
            player.sendMessage(MessageUtil.getPrefix() + "§a" + target.getName() + " §7has been equipped!");
            return false;
        }

        if (args.length == 2) {
            String kit = args[1].toLowerCase();

            if (args[0].equalsIgnoreCase("enable")) {
                if (!EventCore.getInstance().getKitManager().getKits().containsKey(kit)) {
                    player.sendMessage(MessageUtil.getPrefix() + "§cThis kit does not exist!");
                    return false;
                }

                EventCore.getInstance().getKitManager().enable(kit);
                player.sendMessage(MessageUtil.getPrefix() + "§a" + kit + " §7has been enabled!");
                return false;
            }

            if (args[0].equalsIgnoreCase("save")) {
                EventCore.getInstance().getKitManager().save(kit, player);
                player.sendMessage(MessageUtil.getPrefix() + "§a" + kit + " §7has been saved!");
                return false;
            }

            if (args[0].equalsIgnoreCase("delete")) {
                if (!EventCore.getInstance().getKitManager().getKits().containsKey(kit)) {
                    player.sendMessage(MessageUtil.getPrefix() + "§cThis kit does not exist!");
                    return false;
                }

                if (EventCore.getInstance().getKitManager().getEnabledKit().equalsIgnoreCase(kit)) {
                    player.sendMessage(MessageUtil.getPrefix() + "§cYou can't delete the enabled kit!");
                    return false;
                }

                EventCore.getInstance().getKitManager().delete(kit);
                player.sendMessage(MessageUtil.getPrefix() + "§a" + kit + " §7has been deleted!");
                return false;
            }
        }

        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/kit <player>");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/kit enable <kit>");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/kit save <kit>");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/kit delete <kit>");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return new ArrayList<>();
        if (!(player.hasPermission("event.command"))) return new ArrayList<>();

        List<String> list = new ArrayList<>();

        if (args.length == 2) {
            list.addAll(EventCore.getInstance().getKitManager().getKits().keySet());
        }

        if (args.length == 1) {
            list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            list.add("*");
            list.add("enable");
            list.add("save");
            list.add("delete");
        }

        return list.stream().filter(content -> content.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).sorted().toList();
    }

}
