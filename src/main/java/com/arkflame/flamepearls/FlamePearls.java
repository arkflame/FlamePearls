package com.arkflame.flamepearls;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

public class FlamePearls extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Save default config
        this.saveDefaultConfig();

        // Set static instance
        FlamePearls.instance = this;

        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    private static FlamePearls instance;

    public static FlamePearls getInstance() {
        return FlamePearls.instance;
    }

    private Map<Projectile, Location> projectileOrigins = new ConcurrentHashMap<>();
    private Map<Player, Long> lastPearlThrows = new ConcurrentHashMap<>();

    private void setOrigin(Projectile projectile, Location location) {
        projectileOrigins.put(projectile, location);
    }

    private Location getOriginAndRemove(Projectile projectile) {
        return projectileOrigins.remove(projectile);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the action is right click
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Get the player who interacted
            Player player = event.getPlayer();

            // Get the held item
            ItemStack heldItem = player.getInventory().getItem(player.getInventory().getHeldItemSlot());

            // Check if the player is holding an ender pearl in their main hand
            if (heldItem != null && heldItem.getType() == Material.ENDER_PEARL) {
                // Get the current time
                long currentTime = System.currentTimeMillis();

                // Get time since last pearl
                long timeSinceLastPearl = currentTime - lastPearlThrows.getOrDefault(player, 0L);

                // Check if enough time passed
                if (timeSinceLastPearl < 500L) {
                    // Create a decimal format object with 0.0 pattern
                    DecimalFormat df = new DecimalFormat("0.0");
                    // Apply the format to the time
                    String cooldownSeconds = df.format(0.5 - timeSinceLastPearl / 1000D);
                    // Cancel the interaction event
                    event.setCancelled(true);
                    // Send a message to the player
                    player.sendMessage("You cannot throw ender pearls! Wait " + cooldownSeconds + "s");
                } else {
                    // Set the current time as last pearl thrown
                    lastPearlThrows.put(player, currentTime);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Check if the teleport cause is ENDER_PEARL
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            // Cancel the event
            event.setTo(event.getFrom());
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // Check if the entity is a player and the damager is an ender pearl
        if (event.getDamager() instanceof EnderPearl) {
            // Get the player and the ender pearl
            EnderPearl pearl = (EnderPearl) event.getDamager();
            // Check if the pearl was thrown by a different entity than the shooter
            if (pearl.getShooter() != event.getEntity()) {
                // Set the damage to 2
                event.setDamage(2);
            } else {
                // Set the damage to 5
                event.setDamage(5);
            }
        }
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
                setOrigin(projectile, ((Player) shooter).getLocation());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // Check if the creature is an endermite and the spawn reason is ender pearl
        if (event.getEntity() instanceof Endermite) {
            // Cancel the spawn event
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        // Check if the projectile is an ender pearl
        if (projectile instanceof EnderPearl) {
            // Get the shooter of the pearl
            Player player = (Player) projectile.getShooter();
            // Get the location where the pearl landed
            Location location = projectile.getLocation();
            // Get the location where the pearl was thrown from
            Location origin = getOriginAndRemove(projectile);
            // Get the world of the location
            World world = location.getWorld();
            // Try to find the nearest safest position
            Location safeLocation = findSafeLocation(location, origin != null ? origin : location, world);
            // Teleport the player to that location
            player.teleport(safeLocation.setDirection(player.getLocation().getDirection()));
        }
    }

    // A helper method that finds the nearest safest location from a given location,
    // origin and world
    private Location findSafeLocation(Location location, Location origin, World world) {
        // Get the coordinates of the location
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        // Initialize a variable to store the minimum distance to a safe block
        double minDistance = Double.MAX_VALUE;
        // Initialize a variable to store the best safe location
        Location bestLocation = null;
        // Loop through a 3x3 square around the location on the same y level
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                // Get the block at the offset coordinates
                Material block = world.getBlockAt(x + dx, y, z + dz).getType();
                // Check if the block is air or water
                if (!block.isSolid()) {
                    // Create a new location with the offset coordinates and the same pitch and yaw
                    // as the original location
                    Location newLocation = new Location(world, x + dx + 0.5, y, z + dz + 0.5, location.getYaw(),
                            location.getPitch());
                    // Calculate the distance between the new location and the original location
                    double distance = newLocation.distance(location);
                    // Check if the distance is smaller than or equal to the minimum distance
                    if (distance <= minDistance) {
                        // If the distance is equal to the minimum distance, compare the distance
                        // between the new location and the origin with the distance between the best
                        // location and the origin
                        if (distance == minDistance && bestLocation != null) {
                            // Calculate the distance between the new location and the origin
                            double newOriginDistance = newLocation.distance(origin);
                            // Calculate the distance between the best location and the origin
                            double bestOriginDistance = bestLocation.distance(origin);
                            // Check if the new origin distance is smaller than the best origin distance
                            if (newOriginDistance < bestOriginDistance) {
                                // Update the best location
                                bestLocation = newLocation;
                            }
                        } else {
                            // Update the minimum distance and the best location
                            minDistance = distance;
                            bestLocation = newLocation;
                        }
                    }
                }
            }
        }
        // If a safe location is found, return it
        if (bestLocation != null) {
            // Floor the Y value of the best location
            bestLocation.setY(Math.floor(bestLocation.getY()));

            return bestLocation;
        }
        // If no safe location is found, return the original location
        return location;
    }
}