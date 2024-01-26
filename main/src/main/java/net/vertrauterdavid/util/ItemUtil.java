package net.vertrauterdavid.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemUtil {

    private final ItemStack item;

    public ItemUtil(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemUtil setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return this;
    }

    public ItemUtil setLore(List<String> lore) {
        if (lore.isEmpty()) return this;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack toItemStack() {
        return item;
    }

}
