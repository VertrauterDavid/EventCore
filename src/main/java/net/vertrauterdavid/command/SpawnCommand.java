package net.vertrauterdavid.command;

import net.vertrauterdavid.EventCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    public SpawnCommand(String name) {
        EventCore.getInstance().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;
        if (!(player.hasPermission("event.spawn"))) return false;

        player.teleport(EventCore.getInstance().getMapManager().getSpawnLocation());
        return false;
    }

}
