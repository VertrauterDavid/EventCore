package net.vertrauterdavid.manager;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class KitManager {

    private HashMap<Integer, ItemStack> loaded;

    public KitManager() {
        load();
    }

    public void give(Player player) {
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        for (int i = 0; i < 41; i++) {
            player.getInventory().setItem(i, loaded.get(i));
        }
    }

    private void load() {
        HashMap<Integer, ItemStack> map = new HashMap<>();
        String base64 = EventCore.getInstance().getConfig().getString("Kit.Kit", "-");

        if (!(base64.equalsIgnoreCase("-"))) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
                BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);

                for (int i = 0; i < 41; i++) {
                    map.put(i, (ItemStack) bukkitObjectInputStream.readObject());
                }
            } catch (Exception ignored) { }
        }

        loaded = map;
    }

    public void save(Player player) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);

            for (int i = 0; i < 41; i++) {
                bukkitObjectOutputStream.writeObject(player.getInventory().getItem(i));
            }

            bukkitObjectOutputStream.close();
            String base64 = Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
            EventCore.getInstance().getConfig().set("Kit.Kit", base64);
            EventCore.getInstance().saveConfig();

            load();

            player.sendMessage(MessageUtil.getPrefix() + "§aKit has been saved successfully!");
        } catch (Exception exception) {
            player.sendMessage(MessageUtil.getPrefix() + "§cFailed to save!");
        }
    }

}
