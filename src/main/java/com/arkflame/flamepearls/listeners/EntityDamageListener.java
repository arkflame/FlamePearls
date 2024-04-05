package com.arkflame.flamepearls.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.arkflame.flamepearls.config.GeneralConfigHolder;
import com.arkflame.flamepearls.managers.TeleportDataManager;

public class EntityDamageListener implements Listener {
    private GeneralConfigHolder generalConfigHolder;
    private TeleportDataManager teleportDataManager;

    public EntityDamageListener(TeleportDataManager teleportDataManager, GeneralConfigHolder generalConfigHolder) {
        this.teleportDataManager = teleportDataManager;
        this.generalConfigHolder = generalConfigHolder;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() != DamageCause.FALL) {
            return;
        }
        
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        int noDamageTicks = generalConfigHolder.getNoDamageTicksAfterTeleport();

        if (noDamageTicks <= 0) {
            return;
        }

        Player player = (Player) entity;

        if (!teleportDataManager.contains(player)) {
            return;
        }

        long time = teleportDataManager.get(player);

        if (System.currentTimeMillis() - time < noDamageTicks * 50L) {
            event.setCancelled(true);
        }
    }
}
