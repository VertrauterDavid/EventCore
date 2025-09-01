package net.vertrauterdavid.manager;

import lombok.Getter;
import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.LocationUtil;
import net.vertrauterdavid.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
public class MapManager {

    private Location spawnLocation;

    public MapManager() {
        Scheduler.wait(() -> spawnLocation = LocationUtil.fromString(EventCore.getInstance().getConfig().getString("Settings.SpawnLocation", "world/0/200/0")), 2);
    }

    public void saveSpawnLocation(Player player) {
        String location = LocationUtil.toString(player.getLocation());
        spawnLocation = player.getLocation();

        EventCore.getInstance().getConfig().set("Settings.SpawnLocation", location);
        EventCore.getInstance().saveConfig();
    }

    public void drop() {
        long borderExtra = EventCore.getInstance().getConfig().getLong("Settings.Drop.BorderExtra", 3);
        Location edgeMin = spawnLocation.clone().subtract(spawnLocation.getWorld().getWorldBorder().getSize() / 2D + borderExtra, 0, spawnLocation.getWorld().getWorldBorder().getSize() / 2D + borderExtra);
        Location edgeMax = spawnLocation.clone().add(spawnLocation.getWorld().getWorldBorder().getSize() / 2D + borderExtra, 0, spawnLocation.getWorld().getWorldBorder().getSize() / 2D + borderExtra);

        Scheduler.dispatchCommand(() -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/world " + spawnLocation.getWorld().getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/pos1 " + edgeMin.getBlockX() + ",-63," + edgeMin.getBlockZ());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/pos2 " + edgeMax.getBlockX() + ",350," + edgeMax.getBlockZ());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/set 0");
            EventCore.getInstance().getConfig().getStringList("Settings.Drop.CustomCommands").forEach(command ->
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(1)));
        });
    }

    public void reset() {
        Scheduler.dispatchCommand(() -> EventCore.getInstance().getConfig().getStringList("Settings.MapReset.Commands").forEach(command ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(1))));
    }

}
