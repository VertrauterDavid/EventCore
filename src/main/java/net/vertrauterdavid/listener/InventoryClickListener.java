package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void handle(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getInventory().getType() == InventoryType.PLAYER || event.getInventory().getType() == InventoryType.CREATIVE) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;

        ItemStack itemStack = event.getCurrentItem();
        InventoryView view = event.getView();

        if (view.getTitle().equalsIgnoreCase("§cEvent Settings")) {
            event.setCancelled(true);

            if (itemStack.getType() == Material.COMPASS) {
                EventCore.getInstance().getMapManager().saveSpawnLocation(player);

                player.sendMessage(MessageUtil.getPrefix() + "§aLocation saved!");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
                player.closeInventory();
            }

            if (itemStack.getType() == Material.DIAMOND_CHESTPLATE) {
                EventCore.getInstance().getKitManager().save(player);
            }
        }
    }

}
