package com.arkflame.flamepearls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.arkflame.flamepearls.managers.CooldownManager;

public class PlayerQuitListener implements Listener {
    private CooldownManager cooldownManager;

    public PlayerQuitListener(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Remove the player from cooldown list
        cooldownManager.resetCooldown(event.getPlayer());
    }
}
