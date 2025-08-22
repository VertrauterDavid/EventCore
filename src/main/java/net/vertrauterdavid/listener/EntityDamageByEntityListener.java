package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (EventCore.getInstance().getConfig().getBoolean("Settings.DisableItemExplosions", true)) {
            if (entity.getType() == EntityType.ITEM && damager.getType() == EntityType.END_CRYSTAL) {
                if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                    event.setCancelled(true);
                    event.setDamage(0);
                    return;
                }
            }
        }

        if (EventCore.getInstance().getConfig().getBoolean("Settings.DisableFallDamage", true)) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
                return;
            }
        }

        if (damager.hasPermission("event.bypass")) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(!(EventCore.getInstance().getGameManager().isRunning()));
    }

}
