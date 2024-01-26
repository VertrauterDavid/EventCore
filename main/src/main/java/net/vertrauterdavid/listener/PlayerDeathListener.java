package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import net.vertrauterdavid.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void handle(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (EventCore.getInstance().getConfig().getBoolean("Messages.PlayerDeath.Enabled")) {
            String message = event.getDeathMessage();
            for (Player player2 : Bukkit.getOnlinePlayers()) {
                message = message.replaceAll(player2.getName(), MessageUtil.get("Messages.PlayerDeath.HighlightColor") + player2.getName() + "§7");
            }
            event.setDeathMessage(MessageUtil.get("Messages.PlayerDeath.Prefix") + message);
        } else {
            event.setDeathMessage("");
        }

        event.setKeepLevel(true);
        event.setDroppedExp(0);

        PlayerUtil.cleanPlayer(player);
        player.setGameMode(GameMode.SPECTATOR);
    }

}
