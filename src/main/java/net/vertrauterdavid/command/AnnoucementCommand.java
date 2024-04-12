package net.vertrauterdavid.command;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AnnoucementCommand implements CommandExecutor {

    public AnnoucementCommand(String name) {
        EventCore.getInstance().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender.hasPermission("event.command"))) return false;

        if (args.length >= 1) {
            String message = String.join(" ", args);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(MessageUtil.get("Messages.AnnoucementCommand.MessageFormat").replaceAll("%prefix%", MessageUtil.getPrefix()).replaceAll("%message%", message));
                if (EventCore.getInstance().getConfig().getBoolean("Messages.AnnoucementCommand.Title.Enabled")) {
                    player.sendTitle(MessageUtil.get("Messages.AnnoucementCommand.Title.Title").replaceAll("%prefix%", MessageUtil.getPrefix()).replaceAll("%message%", message), MessageUtil.get("Messages.AnnoucementCommand.Title.SubTitle").replaceAll("%prefix%", MessageUtil.getPrefix()).replaceAll("%message%", message));
                }
            }
            return false;
        }

        commandSender.sendMessage(MessageUtil.getPrefix() + "Usage: §c/annoucement <message>");
        return false;
    }

}
