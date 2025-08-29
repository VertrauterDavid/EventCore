package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import net.vertrauterdavid.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (EventCore.getInstance().getConfig().getBoolean("Settings.HostRank.Enabled")) {
            if (player.hasPermission(Objects.requireNonNull(EventCore.getInstance().getConfig().getString("Settings.HostRank.Permission"),"event.host"))) {
                Scheduler.dispatchCommand(() -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Objects.requireNonNull(EventCore.getInstance().getConfig().getString("Settings.HostRank.QuitCommand").replaceAll("%player%", player.getName()), "event.host"));
                });
            }
        }

        if (EventCore.getInstance().getConfig().getBoolean("Messages.PlayerQuit.Enabled")) {
            event.setQuitMessage(MessageUtil.getPrefix() + MessageUtil.get("Messages.PlayerQuit.Message").replaceAll("%player%", player.getName()));
        } else {
            event.setQuitMessage("");
        }
    }

}
