package net.vertrauterdavid.util;

import net.vertrauterdavid.EventCore;

public class BorderUtil implements Runnable {

    public static int borderDefault = 200;
    public static double borderDamageBuffer = 0.0;
    public static double borderDamageAmount = 0.2;
    public static int lastOptimal = borderDefault;
    public static boolean autoBorder = true;

    public BorderUtil() {
        borderDefault = EventCore.getInstance().getConfig().getInt("Settings.WorldBorder.DefaultSize", borderDefault);
        borderDamageBuffer = EventCore.getInstance().getConfig().getDouble("Settings.WorldBorder.Damage.Buffer", borderDamageBuffer);
        borderDamageAmount = EventCore.getInstance().getConfig().getDouble("Settings.WorldBorder.Damage.Amount", borderDamageAmount);
        lastOptimal = borderDefault;
    }

    @Override
    public void run() {
        if (EventCore.getInstance().getGameManager().isRunning() && autoBorder) {
            double current = EventCore.getInstance().getMapManager().getSpawnLocation().getWorld().getWorldBorder().getSize();
            int optimal = getOptimalSize();
            if (lastOptimal > optimal) {
                lastOptimal = optimal;
                Scheduler.runSync(() -> EventCore.getInstance().getMapManager().getSpawnLocation().getWorld().getWorldBorder().setSize(optimal, (long) (current - optimal)));
            }
        }
    }

    private int getOptimalSize() {
        int optimal = (int) (((Math.pow(PlayerUtil.getAlive(), 2)) / 60 + 4 + 0.6 * PlayerUtil.getAlive()) * 2);
        return Math.min(200, optimal);
    }

}
