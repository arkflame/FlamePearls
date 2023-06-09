package com.arkflame.flamepearls.listeners;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.arkflame.flamepearls.managers.OriginManager;

public class ProjectileLaunchListener implements Listener {
    private OriginManager originManager;
    
    public ProjectileLaunchListener(OriginManager originManager) {
        this.originManager = originManager;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        // Get the projectile
        Projectile projectile = event.getEntity();

        // Check if the projectile is an ender pearl
        if (projectile instanceof EnderPearl) {
            // Get the shooter
            ProjectileSource shooter = projectile.getShooter();

            // Check if shooter is entity
            if (shooter instanceof Player) {
                // Set the origin to the shooter location
                originManager.setOrigin(projectile, ((Player) shooter).getLocation());
            }
        }
    }
}
