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
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.projectiles.ProjectileSource;

import com.arkflame.flamepearls.config.GeneralConfigHolder;
import com.arkflame.flamepearls.managers.OriginManager;
import com.arkflame.flamepearls.utils.LocationUtil;

public class ProjectileHitListener implements Listener {
    private OriginManager originManager;
    private Sound sound;
    private double endermiteChance;

    public ProjectileHitListener(OriginManager originManager, GeneralConfigHolder generalConfigHolder) {
        this.originManager = originManager;
        this.sound = generalConfigHolder.getPearlSound();
        this.endermiteChance = generalConfigHolder.getEndermiteChance();
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        // Check if the projectile is an ender pearl
        if (projectile instanceof EnderPearl) {
            // Get shooter
            ProjectileSource shooter = projectile.getShooter();

            if (shooter instanceof Player) {
                // Cast the shooter of the pearl
                Player player = (Player) shooter;
                // Get the location where the pearl landed
                Location location = projectile.getLocation();
                // Get the location where the pearl was thrown from
                Location origin = originManager.getOriginAndRemove(projectile);

                if (origin != null) {
                    // Get the world of the location
                    World world = location.getWorld();
                    // Try to find the nearest safest position
                    Location safeLocation = LocationUtil.findSafeLocation(location, origin, world);
                    // Will teleport
                    originManager.setAsWillTeleport(player);
                    // Teleport the player to that location
                    player.teleport(safeLocation.setDirection(player.getLocation().getDirection()), TeleportCause.ENDER_PEARL);
                    // Spawn endermite if chance is higher
                    if (endermiteChance > Math.random()) {
                        world.spawnEntity(safeLocation, org.bukkit.entity.EntityType.ENDERMITE);
                    }
                    // Check if sound is defined
                    if (sound != null) {
                        // Play sound
                        world.playSound(safeLocation, sound, 5, 1f);
                    }
                }
            }
        }
    }
}
