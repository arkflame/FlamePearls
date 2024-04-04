package com.arkflame.flamepearls.listeners;

import com.arkflame.flamepearls.managers.TeleportDataManager;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.arkflame.flamepearls.config.GeneralConfigHolder;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class EntityDamageListener implements Listener {
    private GeneralConfigHolder generalConfigHolder;
    private TeleportDataManager teleportDataManager;
    
    public EntityDamageListener(TeleportDataManager teleportDataManager, GeneralConfigHolder generalConfigHolder) {
        this.teleportDataManager = teleportDataManager;
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

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause() != EntityDamageEvent.DamageCause.FALL
                || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if(!teleportDataManager.users.containsKey(player)) {
            return;
        }

        long time = teleportDataManager.users.get(player);

        if(System.currentTimeMillis() - time < generalConfigHolder.getNoDamageTicksAfterTeleport() * 50L) {
            event.setCancelled(true);
        }
    }
}
