package com.arkflame.flamepearls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.arkflame.flamepearls.FlamePearls;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Remove the player from cooldown list
        FlamePearls.getInstance().resetCooldown(event.getPlayer());
    }
}
