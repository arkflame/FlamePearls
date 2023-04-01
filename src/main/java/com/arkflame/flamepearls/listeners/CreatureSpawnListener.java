package com.arkflame.flamepearls.listeners;

import org.bukkit.entity.Endermite;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {
    
    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // Check if the creature is an endermite and the spawn reason is ender pearl
        if (event.getEntity() instanceof Endermite) {
            // Cancel the spawn event
            event.setCancelled(true);
        }
    }
}
