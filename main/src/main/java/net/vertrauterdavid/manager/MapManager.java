package net.vertrauterdavid.manager;

import lombok.Getter;
import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.LocationUtil;
import net.vertrauterdavid.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MapManager {

    private Location spawnLocation;
    private String dropPos1;
    private String dropPos2;

    public MapManager() {
        Scheduler.wait(() -> {
            spawnLocation = LocationUtil.fromString(EventCore.getInstance().getConfig().getString("Settings.SpawnLocation", "world/0/200/0"));
            dropPos1 = EventCore.getInstance().getConfig().getString("Settings.Drop.Pos1", "0/0/0");
            dropPos2 = EventCore.getInstance().getConfig().getString("Settings.Drop.Pos2", "0/0/0");
        }, 2);
    }

    public void saveSpawnLocation(Player player) {
        String location = LocationUtil.toString(player.getLocation());
        spawnLocation = player.getLocation();

        EventCore.getInstance().getConfig().set("Settings.SpawnLocation", location);
        EventCore.getInstance().saveConfig();
    }

    public void saveDropPos1(Player player) {
        String location = player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ();
        dropPos1 = location;

        EventCore.getInstance().getConfig().set("Settings.Drop.Pos1", location);
        EventCore.getInstance().saveConfig();
    }

    public void saveDropPos2(Player player) {
        String location = player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ();
        dropPos2 = location;

        EventCore.getInstance().getConfig().set("Settings.Drop.Pos2", location);
        EventCore.getInstance().saveConfig();
    }

    public void drop() {
        for (String command : (List<String>) EventCore.getInstance().getConfig().getList("Settings.Drop.Commands", new ArrayList<>())) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(1).replaceAll("%pos1%", dropPos1).replaceAll("%pos2%", dropPos2));
        }
    }

}
