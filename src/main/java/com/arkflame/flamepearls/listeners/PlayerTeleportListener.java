package com.arkflame.flamepearls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.arkflame.flamepearls.managers.OriginManager;

public class PlayerTeleportListener implements Listener {
    private OriginManager originManager;

    public PlayerTeleportListener(OriginManager originManager) {
        this.originManager = originManager;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Check if the teleport cause is ENDER_PEARL
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            if (originManager.canTeleport(event.getPlayer())) {
                // Teleported
                originManager.setAsTeleported(event.getPlayer());
                return; 
            }
            // Cancel the event
            event.setTo(event.getFrom());
            // Really cancel
            event.setCancelled(true);
        }
    }
}
