package com.arkflame.flamepearls.listeners;

import com.arkflame.flamepearls.config.GeneralConfigHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.arkflame.flamepearls.managers.OriginManager;

public class PlayerTeleportListener implements Listener {
    private OriginManager originManager;
    private GeneralConfigHolder configHolder;

    public PlayerTeleportListener(GeneralConfigHolder configHolder, OriginManager originManager) {
        this.configHolder = configHolder;
        this.originManager = originManager;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Check if the teleport cause is ENDER_PEARL
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            if (originManager.canTeleport(event.getPlayer())) {
                // Teleported
                originManager.setAsTeleported(event.getPlayer());
                // Set no damage ticks for spoof player damage if player is falling.
                event.getPlayer().setNoDamageTicks(configHolder.getNoDamageTicksAfterTeleport());

                return; 
            }
            // Cancel the event
            event.setTo(event.getFrom());
            // Really cancel
            event.setCancelled(true);
        }
    }
}
