package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class BlockExplodeListener implements Listener {

    @EventHandler
    public void handle(BlockExplodeEvent event) {
        event.blockList().forEach(block -> block.getDrops().clear());
        event.setCancelled(!(EventCore.getInstance().getGameManager().isRunning()));
    }

}
