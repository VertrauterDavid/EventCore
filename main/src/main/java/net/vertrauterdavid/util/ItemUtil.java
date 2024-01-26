package net.vertrauterdavid.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemUtil {

    private final ItemStack item;

    public ItemUtil(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemUtil(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        
    }

    public ItemUtil(Material material, int amount, int id) {
        this.item = new ItemStack(material, amount, (short) id);
    }

    public ItemUtil(PotionType potionType) {
        this.item = new Potion(potionType).toItemStack(1);
    }

    public ItemUtil(PotionType potionType, int level) {
        this.item = new Potion(potionType, level).toItemStack(1);
    }

    public ItemUtil(PotionType potionType, int level, int amount) {
        this.item = new Potion(potionType, level).toItemStack(amount);
    }

    public ItemUtil(PotionType potionType, int level, int amount, boolean splash) {
        this.item = new Potion(potionType, level, splash).toItemStack(amount);
    }

    public ItemUtil(PotionType potionType, int level, int amount, boolean splash, boolean extended) {
        this.item = new Potion(potionType, level, splash, extended).toItemStack(amount);
    }

    public ItemUtil(ItemStack item) {
        this.item = item;
    }

    public ItemUtil setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemUtil setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemUtil setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return this;
    }

    public ItemUtil addLore(String... lore) {
        addLore(Arrays.asList(lore));
        return this;
    }

    public ItemUtil setLore(List<String> lore) {
        if (lore.isEmpty()) return this;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemUtil addLore(List<String> lore) {
        if (lore.isEmpty()) return this;
        ItemMeta meta = item.getItemMeta();
        List<String> fullLore = new ArrayList<>();
        if (meta.getLore() != null) {
            fullLore.addAll(meta.getLore());
        }
        fullLore.addAll(lore);
        meta.setLore(fullLore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setUnbreakable() {
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setColor(DyeColor color) {
        if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET) {
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color.getColor());
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemUtil setSkullOwner(String owner) {
        if (item.getType() == Material.PLAYER_HEAD && !owner.equalsIgnoreCase("")) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(owner);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemUtil addEnchantment(Enchantment enchantment, int level) {
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            meta.addStoredEnchant(enchantment, level, true);
            item.setItemMeta(meta);
        } else {
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(enchantment, level, true);
            item.setItemMeta(meta);
        }
        return this;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        Map<Enchantment, Integer> enchantmentsList = item.getItemMeta().getEnchants();
        if (item.getType() == Material.ENCHANTED_BOOK) {
            enchantmentsList = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
        }
        return enchantmentsList;
    }

    public ItemUtil addItemFlag(ItemFlag flag) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemMeta getItemMeta() {
        return item.getItemMeta();
    }

    public ItemStack toItemStack() {
        return item;
    }

}
