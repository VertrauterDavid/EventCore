package net.vertrauterdavid.command;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.BorderUtil;
import net.vertrauterdavid.util.ItemUtil;
import net.vertrauterdavid.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EventCommand implements CommandExecutor, TabCompleter {

    public EventCommand(String name) {
        EventCore.getInstance().getCommand(name).setExecutor(this);
        EventCore.getInstance().getCommand(name).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;

        if (args.length == 0) {
            player.sendMessage(" ");
            player.sendMessage(MessageUtil.getPrefix() + "§7Running §aEventCore §7v" + EventCore.getInstance().getVersionUtil().getCurrentVersion());
            player.sendMessage(MessageUtil.getPrefix() + "§7Download at §ahttps://github.com/VertrauterDavid");
            player.sendMessage(" ");
        }

        if (!(player.hasPermission("event.command"))) return false;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("start")) {
                EventCore.getInstance().getGameManager().start();
                return false;
            }

            if (args[0].equalsIgnoreCase("stop")) {
                player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event stop <winner>");
                return false;
            }

            if (args[0].equalsIgnoreCase("drop")) {
                EventCore.getInstance().getMapManager().drop();
                return false;
            }

            if (args[0].equalsIgnoreCase("settings")) {
                Inventory inventory = Bukkit.createInventory(null, 9 * 3, MessageUtil.translateColorCodes("&cEvent Settings"));
                inventory.setItem(2 + 9, new ItemUtil(Material.WOODEN_AXE).setName("§aSet drop location").setLore("§8 - §7Left click: §aLocation 1", "§8 - §7Right click: §aLocation 2").toItemStack());
                inventory.setItem(4 + 9, new ItemUtil(Material.COMPASS).setName("§aSet spawn location").toItemStack());
                inventory.setItem(6 + 9, new ItemUtil(Material.DIAMOND_CHESTPLATE).setName("§aSave Kit").toItemStack());
                player.openInventory(inventory);
                return false;
            }

            if (args[0].equalsIgnoreCase("kickspec")) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!(target.hasPermission("event.spec")) && target.getGameMode() == GameMode.SPECTATOR) {
                        target.kickPlayer(" ");
                    }
                }
                player.sendMessage(MessageUtil.getPrefix() + "All spectators have been kicked!");
                return false;
            }

            if (args[0].equalsIgnoreCase("kickall")) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!(target.hasPermission("event.spec"))) {
                        target.kickPlayer(" ");
                    }
                }
                player.sendMessage(MessageUtil.getPrefix() + "All players have been kicked!");
                return false;
            }

            if (args[0].equalsIgnoreCase("clearall")) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.getInventory().setArmorContents(null);
                    target.getInventory().clear();
                }
                player.sendMessage(MessageUtil.getPrefix() + "All players have been cleared!");
                return false;
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("autoBorder")) {
                if (args[1].equalsIgnoreCase("on")) {
                    BorderUtil.autoBorder = true;

                    player.sendMessage(MessageUtil.getPrefix() + "AutoBorder has been §cdeactivated!");
                    return false;
                }

                if (args[1].equalsIgnoreCase("off")) {
                    BorderUtil.autoBorder = false;
                    BorderUtil.lastOptimal = BorderUtil.borderDefault;

                    player.sendMessage(MessageUtil.getPrefix() + "AutoBorder has been §aactivated!");
                    return false;
                }
            }
        }

        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("stop")) {
                StringBuilder winner = null;
                for (int i = 1; i < args.length; i++) {
                    if (winner == null) {
                        winner = new StringBuilder(args[i]);
                    } else {
                        winner.append(" ").append(args[i]);
                    }
                }

                EventCore.getInstance().getGameManager().stop(winner.toString());
                return false;
            }
        }

        player.sendMessage(" ");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event start");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event stop <winner>");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event drop");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event autoBorder <on / off>");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event settings");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event kickspec");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event kickall");
        player.sendMessage(MessageUtil.getPrefix() + "Usage: §c/event clearall");
        player.sendMessage(" ");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return new ArrayList<>();
        if (!(player.hasPermission("event.command"))) return new ArrayList<>();

        List<String> list = new ArrayList<>();

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("stop")) {
                list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            }

            if (args[0].equalsIgnoreCase("autoBorder")) {
                list.addAll(Arrays.asList("on", "off"));
            }
        }

        if (args.length == 1) {
            list.addAll(Arrays.asList("start", "stop", "drop", "autoBorder", "settings", "kickspec", "kickall", "clearall"));
        }

        try {
            Collections.sort(list);
        } catch (Exception ignored) { }
        return list.stream().filter(content -> content.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).toList();
    }

}
