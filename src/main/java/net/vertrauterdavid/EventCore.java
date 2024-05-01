package net.vertrauterdavid;

import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.vertrauterdavid.command.AnnoucementCommand;
import net.vertrauterdavid.command.EventCommand;
import net.vertrauterdavid.command.KitCommand;
import net.vertrauterdavid.command.ReviveCommand;
import net.vertrauterdavid.listener.*;
import net.vertrauterdavid.manager.GameManager;
import net.vertrauterdavid.manager.KitManager;
import net.vertrauterdavid.manager.MapManager;
import net.vertrauterdavid.util.*;
import net.vertrauterdavid.util.plugin.StatisticUtil;
import net.vertrauterdavid.util.plugin.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class EventCore extends JavaPlugin {

    @Getter
    private static EventCore instance;
    private VersionUtil versionUtil;
    private MapManager mapManager;
    private GameManager gameManager;
    private KitManager kitManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        new StatisticUtil(this);
        versionUtil = new VersionUtil(this, "EventCore");
        mapManager = new MapManager();
        gameManager = new GameManager();
        kitManager = new KitManager();

        new AnnoucementCommand("announcement");
        new EventCommand("event");
        new KitCommand("kit");
        new ReviveCommand("revive");

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), instance);
        Bukkit.getPluginManager().registerEvents(new BlockExplodeListener(), instance);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), instance);
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), instance);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), instance);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), instance);
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), instance);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerPickupItemListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), instance);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
        }

        Scheduler.timerAsync(new BorderUtil(), 20, 10);
        Scheduler.timerAsync(new AutoBroadcast(), 20, 20 * getConfig().getLong("AutoBroadcast.Interval", 60));
        Scheduler.wait(() -> {
            World world = mapManager.getSpawnLocation().getWorld();
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setDifficulty(Difficulty.PEACEFUL);
            world.getWorldBorder().setSize(BorderUtil.borderDefault);
            world.getWorldBorder().setDamageBuffer(BorderUtil.borderDamageBuffer);
            world.getWorldBorder().setDamageAmount(BorderUtil.borderDamageAmount);
        }, 2);

        if (getConfig().getBoolean("Messages.Actionbar.Enabled")) {
            Scheduler.timerAsync(() -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(MessageUtil.get("Messages.Actionbar.Message")));
                }
            }, 0, 20);
        }
    }

}
