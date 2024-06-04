package net.vertrauterdavid.listener;

import net.vertrauterdavid.EventCore;
import net.vertrauterdavid.util.MessageUtil;
import net.vertrauterdavid.util.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void handle(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getGameMode() == GameMode.SPECTATOR) {
            event.setDeathMessage("");
            return;
        }

        if (EventCore.getInstance().getConfig().getBoolean("Messages.PlayerDeath.Enabled")) {
            if (player.getKiller() != null) {
                event.setDeathMessage(MessageUtil.get("Messages.PlayerDeath.Message1").replaceAll("%player%", player.getName()).replaceAll("%killer%", player.getKiller().getName()));
            } else {
                event.setDeathMessage(MessageUtil.get("Messages.PlayerDeath.Message2").replaceAll("%player%", player.getName()));
            }
        } else {
            event.setDeathMessage("");
        }

        event.setKeepLevel(true);
        event.setDroppedExp(0);

        PlayerUtil.cleanPlayer(player);
        player.setGameMode(GameMode.SPECTATOR);
    }

}
