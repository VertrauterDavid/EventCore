package net.vertrauterdavid.command;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    public SpawnCommand(String name) {
        Objects.requireNonNull(EventCore.getInstance().getCommand(name)).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;
        if (!(player.hasPermission("event.spawn"))) return false;

        if (EventCore.getInstance().getMapManager().getSpawnLocation() == null) {
            player.sendMessage(MessageUtil.getPrefix() + "§cThere isn’t a spawn location yet. Set one using the command /event setspawn");
            return false;
        }

        if (EventCore.getInstance().getGameManager().isRunning() && !player.hasPermission("event.bypass")) {
            player.sendMessage(MessageUtil.getPrefix() + "§cYou cannot teleport to the spawn while the event is running");
            return false;
        }

        player.teleportAsync(EventCore.getInstance().getMapManager().getSpawnLocation());
        return false;
    }

}
