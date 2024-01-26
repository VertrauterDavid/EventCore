package net.vertrauterdavid.util;

import net.vertrauterdavid.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Scheduler {

    public static void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(EventCore.getInstance(), runnable);
    }

    public static BukkitTask wait(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(EventCore.getInstance(), runnable, delay);
    }

    public static BukkitTask timer(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(EventCore.getInstance(), runnable, delay, period);
    }

    public static BukkitTask timerAsync(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(EventCore.getInstance(), runnable, delay, period);
    }

}
