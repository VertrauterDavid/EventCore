package net.vertrauterdavid.util;

import lombok.experimental.UtilityClass;
import net.vertrauterdavid.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@UtilityClass
public class PlayerUtil {

    public static int getAlive() {
        return Bukkit.getOnlinePlayers().stream().filter(player2 -> player2.getGameMode() == GameMode.SURVIVAL).toList().size();
    }

    public static int getTotal() {
        return Bukkit.getOnlinePlayers().size();
    }

    public static void cleanPlayer(@NotNull Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        new ArrayList<>(player.getActivePotionEffects()).forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        EventCore.getInstance().getKitManager().give(player);
    }

}
