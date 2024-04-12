package net.vertrauterdavid.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void handle(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.RAID || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.ENDER_PEARL) {
            event.setCancelled(true);
        }
    }

}
