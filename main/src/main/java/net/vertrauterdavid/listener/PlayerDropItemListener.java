package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (EventCore.getInstance().getConfig().getBoolean("Settings.AllowItemDropBeforeStart")) return;

        if (player.hasPermission("event.bypass")) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(!(EventCore.getInstance().getGameManager().isRunning()));
    }

}
