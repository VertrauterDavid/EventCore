package net.vertrauterdavid.util;

import lombok.Getter;
import net.vertrauterdavid.EventCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

@Getter
public class Scheduler {

    @Getter
    private static final boolean FOLIA;

    static {
        boolean folia;
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler");
            folia = true;
        } catch (ClassNotFoundException e) {
            folia = false;
        }
        FOLIA = folia;
    }

    public interface TaskWrapper {
        void cancel();
        boolean isCancelled();
    }

    private static class BukkitTaskWrapper implements TaskWrapper {
        private final BukkitTask task;

        public BukkitTaskWrapper(BukkitTask task) {
            this.task = task;
        }

        @Override
        public void cancel() {
            if (task != null) {
                task.cancel();
            }
        }

        @Override
        public boolean isCancelled() {
            return task == null || task.isCancelled();
        }
    }

    private static class FoliaTaskWrapper implements TaskWrapper {
        private Object task;

        public FoliaTaskWrapper(Object task) {
            this.task = task;
        }

        @Override
        public void cancel() {
            if (task != null) {
                try {
                    // Use reflection to call cancel() on Folia ScheduledTask
                    task.getClass().getMethod("cancel").invoke(task);
                } catch (Exception e) {
                    // Fallback - set to null if reflection fails
                    task = null;
                }
            }
        }

        @Override
        public boolean isCancelled() {
            if (task == null) return true;
            try {
                // Use reflection to call isCancelled() on Folia ScheduledTask
                return (Boolean) task.getClass().getMethod("isCancelled").invoke(task);
            } catch (Exception e) {
                return task == null;
            }
        }
    }

    public static void runSync(Runnable runnable) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().execute(EventCore.getInstance(), runnable);
        } else {
            Bukkit.getScheduler().runTask(EventCore.getInstance(), runnable);
        }
    }

    public static TaskWrapper wait(Runnable runnable, long delay) {
        long safeDelay = Math.max(delay, 1);

        if (FOLIA) {
            Object foliaTask = Bukkit.getGlobalRegionScheduler().runDelayed(EventCore.getInstance(), (task) -> runnable.run(), safeDelay);
            return new FoliaTaskWrapper(foliaTask);
        } else {
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(EventCore.getInstance(), runnable, delay);
            return new BukkitTaskWrapper(bukkitTask);
        }
    }

    public static TaskWrapper timer(Runnable runnable, long delay, long period) {
        long safeDelay = Math.max(delay, 1);
        long safePeriod = Math.max(period, 1);

        if (FOLIA) {
            Object foliaTask = Bukkit.getGlobalRegionScheduler().runAtFixedRate(EventCore.getInstance(), (task) -> runnable.run(), safeDelay, safePeriod);
            return new FoliaTaskWrapper(foliaTask);
        } else {
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(EventCore.getInstance(), runnable, delay, period);
            return new BukkitTaskWrapper(bukkitTask);
        }
    }

    public static TaskWrapper timerAsync(Runnable runnable, long delay, long period) {
        long safeDelay = Math.max(delay, 1);
        long safePeriod = Math.max(period, 1);

        if (FOLIA) {
            Object foliaTask = Bukkit.getAsyncScheduler().runAtFixedRate(EventCore.getInstance(), (task) -> runnable.run(),
                    safeDelay * 50, safePeriod * 50, TimeUnit.MILLISECONDS);
            return new FoliaTaskWrapper(foliaTask);
        } else {
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(EventCore.getInstance(), runnable, delay, period);
            return new BukkitTaskWrapper(bukkitTask);
        }
    }

    public static void cancelTask(Object task) {
        if (task == null) return;

        if (task instanceof TaskWrapper) {
            ((TaskWrapper) task).cancel();
        } else if (task instanceof BukkitTask) {
            ((BukkitTask) task).cancel();
        } else if (FOLIA) {
            // Handle raw Folia task objects
            try {
                task.getClass().getMethod("cancel").invoke(task);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static void dispatchCommand(Runnable commandRunnable) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().execute(EventCore.getInstance(), commandRunnable);
        } else {
            commandRunnable.run();
        }
    }
}
