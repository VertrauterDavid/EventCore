package net.vertrauterdavid.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "eventcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "VertrauterDavid";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        return Arrays.asList(
                "total",
                "alive",
                "kills",
                "deaths",
                "kd",
                "totems",
                "border",
                "ping",
                "tps"
        );
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        if (params.equals("total")) {
            return String.valueOf(PlayerUtil.getTotal());
        }

        if (params.equals("alive")) {
            return String.valueOf(PlayerUtil.getAlive());
        }

        if (params.equals("kills")) {
            return String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS));
        }

        if (params.equals("deaths")) {
            return String.valueOf(player.getStatistic(Statistic.DEATHS));
        }

        if (params.equals("kd")) {
            double kills = player.getStatistic(Statistic.PLAYER_KILLS);
            double deaths = player.getStatistic(Statistic.DEATHS);
            return new DecimalFormat("#0.00").format((deaths == 0 ? kills : (kills / deaths)));
        }

        if (params.equals("totems")) {
            int count = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null) {
                    if (itemStack.getType() == Material.TOTEM_OF_UNDYING) {
                        count++;
                    }
                }
            }
            return String.valueOf(count);
        }

        if (params.equals("border")) {
            return String.valueOf((int) (player.getWorld().getWorldBorder().getSize() / 2));
        }

        if (params.equals("ping")) {
            if (player.isOnline()) {
                return String.valueOf((int) (player.getPing() * 0.8));
            } else {
                return "0";
            }
        }

        if (params.equals("tps")) {
            return ChatColor.stripColor(PlaceholderAPI.setPlaceholders(null, "%spark_tps_5m%").replaceAll("\\*", "").split("\\.")[0]);
        }

        return "";
    }

}
