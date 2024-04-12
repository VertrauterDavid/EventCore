package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void handle(EntityExplodeEvent event) {
        event.blockList().forEach(block -> block.getDrops().clear());
        event.setCancelled(!(EventCore.getInstance().getGameManager().isRunning()));
    }

}
