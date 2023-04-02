package com.arkflame.flamepearls;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.arkflame.flamepearls.listeners.CreatureSpawnListener;
import com.arkflame.flamepearls.listeners.EntityDamageByEntityListener;
import com.arkflame.flamepearls.listeners.PlayerInteractListener;
import com.arkflame.flamepearls.listeners.PlayerTeleportListener;
import com.arkflame.flamepearls.listeners.ProjectileHitListener;
import com.arkflame.flamepearls.listeners.ProjectileLaunchListener;

public class FlamePearls extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Save default config
        this.saveDefaultConfig();

        // Set static instance
        FlamePearls.instance = this;

        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
        // Register CreatureSpawnListener
        getServer().getPluginManager().registerEvents(new CreatureSpawnListener(), this);
        // Register EntityDamageByEntityListener
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        // Register CreatureSpawnListener
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        // Register PlayerTeleportListener
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        // Register ProjectileHitListener
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
        // Register ProjectileLaunchListener
        getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(), this);
    }

    private static FlamePearls instance;

    public static FlamePearls getInstance() {
        return FlamePearls.instance;
    }

    // Origin of launch of projectiles
    private Map<Projectile, Location> projectileOrigins = new ConcurrentHashMap<>();

    // Last time pearl was thrown by a player (Cooldown checks)
    private Map<Player, Long> lastPearlThrows = new ConcurrentHashMap<>();

    public void setOrigin(Projectile projectile, Location location) {
        // Insert the projectile-origin
        projectileOrigins.put(projectile, location);
    }

    public Location getOriginAndRemove(Projectile projectile) {
        // Return the value removed
        return projectileOrigins.remove(projectile);
    }

    public void updateLastPearl(Player player) {
        lastPearlThrows.put(player, System.currentTimeMillis());
    }

    public double getCooldown(Player player) {
        // Get the time passed since last pearl in milliseconds
        long timeSinceLastPearl = System.currentTimeMillis() - lastPearlThrows.getOrDefault(player, 0L);
        
        // Return the cooldown minus the time passed and convert to seconds
        return (500 - Math.min(500, timeSinceLastPearl)) / 1000D;
    }
}