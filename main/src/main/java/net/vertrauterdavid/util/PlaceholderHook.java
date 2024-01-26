package net.vertrauterdavid.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
