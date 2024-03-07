package com.arkflame.flamepearls.listeners;

import org.bukkit.entity.Endermite;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.arkflame.flamepearls.config.GeneralConfigHolder;

public class CreatureSpawnListener implements Listener {
    private GeneralConfigHolder generalConfigHolder;
    
    public CreatureSpawnListener(GeneralConfigHolder generalConfigHolder) {
        this.generalConfigHolder = generalConfigHolder;
    }

    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        boolean disableEndermites = generalConfigHolder.isDisableEndermites();
        if (!disableEndermites) { return; }
        // Check if the creature is an endermite and the spawn reason is ender pearl
        if (event.getEntity() instanceof Endermite) {
            // Cancel the spawn event if disable config is true
            event.setCancelled(true);
        }
    }
}
