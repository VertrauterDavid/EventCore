package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import net.vertrauterdavid.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission(Objects.requireNonNull(EventCore.getInstance().getConfig().getString("Settings.HostRank.Permission"),"event.host"))) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Objects.requireNonNull(EventCore.getInstance().getConfig().getString("Settings.HostRank.JoinCommand").replaceAll("%player%", player.getName()), "event.host"));
        }

        if (EventCore.getInstance().getConfig().getBoolean("Messages.PlayerJoin.Enabled")) {
            event.setJoinMessage(MessageUtil.getPrefix() + MessageUtil.get("Messages.PlayerJoin.Message").replaceAll("%player%", player.getName()));
        } else {
            event.setJoinMessage("");
        }

        player.teleport(EventCore.getInstance().getMapManager().getSpawnLocation());
        PlayerUtil.cleanPlayer(player);
        if (EventCore.getInstance().getGameManager().isRunning()) {
            player.getInventory().setArmorContents(null);
            player.getInventory().clear();
            player.setGameMode(GameMode.SPECTATOR);
        }
        Bukkit.getScheduler().runTaskLater(EventCore.getInstance(), () -> {
            player.teleport(EventCore.getInstance().getMapManager().getSpawnLocation());
            if (EventCore.getInstance().getGameManager().isRunning()) {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }, 2);
    }

}
