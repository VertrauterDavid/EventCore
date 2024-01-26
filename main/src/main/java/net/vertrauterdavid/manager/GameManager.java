package net.vertrauterdavid.manager;

import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.BorderUtil;
import net.vertrauterdavid.util.MessageUtil;
import net.vertrauterdavid.util.PlayerUtil;
import net.vertrauterdavid.util.Scheduler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class GameManager {

    private boolean running = false;

    private BukkitTask task;
    private int timer;

    private boolean autoDropped = false;

    public void start() {
        timer = EventCore.getInstance().getConfig().getInt("Messages.StartTimer.Timer", 5);
        task = Scheduler.timer(() -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (timer > 0) {
                    String color = MessageUtil.get("Messages.StartTimer.Colors." + timer + "sec");

                    player.sendMessage(MessageUtil.getPrefix() + MessageUtil.get("Messages.StartTimer.Message").replaceAll("%timer%", color + timer + "§7"));
                    player.sendTitle(MessageUtil.get("Messages.StartTimer.Title").replaceAll("%timer%", color + timer + "§7"), " ");
                    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5, 5);
                } else if (timer == 0) {
                    player.sendMessage(MessageUtil.getPrefix() + MessageUtil.get("Messages.Start.Message"));
                    player.sendTitle(MessageUtil.get("Messages.Start.Title"), " ");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);

                    for (World world : Bukkit.getWorlds()) {
                        world.setDifficulty(Difficulty.HARD);
                    }

                    if (EventCore.getInstance().getConfig().getBoolean("Settings.IngameTimer.Enabled")) {
                        startInGameTimer();
                    }

                    running = true;
                    task.cancel();
                }
            }
            timer--;
        }, 0, 20);

        if (EventCore.getInstance().getConfig().getBoolean("Settings.AutoStop1Player")) {
            Scheduler.timerAsync(() -> {
                if (running && PlayerUtil.getAlive() == 1) {
                    running = false;
                    Scheduler.runSync(() -> stop(Bukkit.getOnlinePlayers().stream().filter(player2 -> player2.getGameMode() == GameMode.SURVIVAL).toList().get(0).getName()));
                }
            }, 0, 20);
        }

        if (EventCore.getInstance().getConfig().getBoolean("Settings.DropOnPlayerCount.Enabled")) {
            Scheduler.timerAsync(() -> {
                if (running && PlayerUtil.getAlive() <= EventCore.getInstance().getConfig().getLong("Settings.DropOnPlayerCount.Count") && !(autoDropped)) {
                    Scheduler.runSync(() -> {
                        autoDropped = true;
                        EventCore.getInstance().getMapManager().drop();
                    });
                }
            }, 0, 1);
        }
    }

    public void stop(String winner) {
        running = false;
        BorderUtil.lastOptimal = 200;

        stopInGameTimer();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(MessageUtil.getPrefix() + MessageUtil.get("Messages.Stop.Message").replaceAll("%winner%", winner));
            player.sendTitle(MessageUtil.get("Messages.Stop.Title").replaceAll("%winner%", winner), MessageUtil.get("Messages.Stop.SubTitle").replaceAll("%winner%", winner));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
            PlayerUtil.cleanPlayer(player);
        }

        for (World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.PEACEFUL);
            world.getWorldBorder().setSize(200);
        }
    }

    private BukkitTask timerTask;
    private long inGameTimer;

    public void startInGameTimer() {
        inGameTimer = 0;

        if (timerTask != null) {
            timerTask.cancel();
        }

        timerTask = Scheduler.timerAsync(() -> {
            inGameTimer++;

            String message = MessageUtil.get("Settings.IngameTimer.Format");
            message = message.replaceAll("hh", String.format("%02d", (inGameTimer / 3600)));
            message = message.replaceAll("mm", String.format("%02d", ((inGameTimer % 3600) / 60)));
            message = message.replaceAll("ss", String.format("%02d", (inGameTimer % 60)));

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            }
        }, 0, 20);
    }

    public void stopInGameTimer() {
        inGameTimer = 0;
        timerTask.cancel();
    }

}
