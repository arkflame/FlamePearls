package com.arkflame.flamepearls.listeners;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.arkflame.flamepearls.config.GeneralConfigHolder;

public class EntityDamageByEntityListener implements Listener {
    private GeneralConfigHolder generalConfigHolder;
    
    public EntityDamageByEntityListener(GeneralConfigHolder generalConfigHolder) {
        this.generalConfigHolder = generalConfigHolder;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // Get the damager
        Entity damager = event.getDamager();
        
        // Check if the entity is a player and the damager is an ender pearl
        if (damager instanceof EnderPearl) {
            // Get the player and the ender pearl
            EnderPearl pearl = (EnderPearl) damager;
            // Check if the pearl was thrown by a different entity than the shooter
            if (pearl.getShooter() != event.getEntity()) {
                // Set the other damage
                event.setDamage(generalConfigHolder.getPearlDamageOther());
            } else {
                // Set the self damage
                event.setDamage(generalConfigHolder.getPearlDamageSelf());
            }
        }
    }
}
