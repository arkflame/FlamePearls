package com.arkflame.flamepearls.listeners;

import java.util.Collection;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.arkflame.flamepearls.config.GeneralConfigHolder;
import com.arkflame.flamepearls.managers.OriginManager;

public class PlayerTeleportListener implements Listener {
    private OriginManager originManager;
    private GeneralConfigHolder generalConfigHolder;

    public PlayerTeleportListener(OriginManager originManager, GeneralConfigHolder generalConfigHolder) {
        this.originManager = originManager;
        this.generalConfigHolder = generalConfigHolder;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Check if the teleport cause is ENDER_PEARL
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            // Get the player
            Player player = event.getPlayer();
            // Get the world
            World world = player.getLocation().getWorld();
            // Get disabled worlds
            Collection<String> disabledWorlds = generalConfigHolder.getDisabledWorlds();
            // This world is disabled
            if (disabledWorlds.contains(world.getName())) {
                return;
            }
            if (originManager.canTeleport(player)) {
                // Teleported
                originManager.setAsTeleported(player);
                return; 
            }
            // Cancel the event
            event.setTo(event.getFrom());
            // Really cancel
            event.setCancelled(true);
        }
    }
}
