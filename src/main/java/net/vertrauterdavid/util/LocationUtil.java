package net.vertrauterdavid.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static Location fromString(String in) {
        return new Location(Bukkit.getWorld(in.split("/")[0]), Double.parseDouble(in.split("/")[1]), Double.parseDouble(in.split("/")[2]), Double.parseDouble(in.split("/")[3]));
    }

    public static String toString(Location location) {
        return location.getWorld().getName() + "/" + (location.getBlockX() + 0.5) + "/" + location.getBlockY() + "/" + (location.getBlockZ() + 0.5) + "/" + (Math.round(location.getYaw() / 45) * 45);
    }

}
