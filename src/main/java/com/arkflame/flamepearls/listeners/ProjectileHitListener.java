package com.arkflame.flamepearls.listeners;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.arkflame.flamepearls.config.GeneralConfigHolder;
import com.arkflame.flamepearls.managers.OriginManager;
import com.arkflame.flamepearls.utils.LocationUtil;

public class ProjectileHitListener implements Listener {
    private OriginManager originManager;
    private Sound sound;

    public ProjectileHitListener(OriginManager originManager, GeneralConfigHolder generalConfigHolder) {
        this.originManager = originManager;
        this.sound = generalConfigHolder.getPearlSound();
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        // Check if the projectile is an ender pearl
        if (projectile instanceof EnderPearl) {
            // Get the shooter of the pearl
            if (projectile.getShooter() instanceof Player) {
                Player player = (Player) projectile.getShooter();
                // Get the location where the pearl landed
                Location location = projectile.getLocation();
                // Get the location where the pearl was thrown from
                Location origin = originManager.getOriginAndRemove(projectile);
                // Get the world of the location
                World world = location.getWorld();
                // Try to find the nearest safest position
                Location safeLocation = LocationUtil.findSafeLocation(location, origin != null ? origin : location, world);
                // Teleport the player to that location
                player.teleport(safeLocation.setDirection(player.getLocation().getDirection()));
                // Damage the player
                player.damage(0, projectile);
                // Check if sound is defined
                if (sound != null) {
                    // Play sound
                    world.playSound(safeLocation, sound, 5, 1f);
                }
            }
        }
    }
}
