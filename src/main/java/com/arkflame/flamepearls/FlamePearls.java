package com.arkflame.flamepearls;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.arkflame.flamepearls.listeners.CreatureSpawnListener;
import com.arkflame.flamepearls.listeners.EntityDamageByEntityListener;
import com.arkflame.flamepearls.listeners.PlayerInteractListener;
import com.arkflame.flamepearls.listeners.PlayerTeleportListener;
import com.arkflame.flamepearls.listeners.ProjectileHitListener;
import com.arkflame.flamepearls.listeners.ProjectileLaunchListener;
import com.arkflame.flamepearls.managers.OriginManager;

public class FlamePearls extends JavaPlugin implements Listener {
    private static OriginManager originManager;

    @Override
    public void onEnable() {
        // Save default config
        this.saveDefaultConfig();

        // Set static instance
        FlamePearls.instance = this;

        // Create the origin manager
        originManager = new OriginManager();

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
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(originManager), this);
        // Register ProjectileLaunchListener
        getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(originManager), this);
    }

    private static FlamePearls instance;

    public static FlamePearls getInstance() {
        return FlamePearls.instance;
    }

    /**
     * Use this to get and manage the origin of ender pearls thrown.
     */
    public static OriginManager getOriginManager() {
        return originManager;
    }
    
    // Last time pearl was thrown by a player (Cooldown checks)
    private Map<Player, Long> lastPearlThrows = new ConcurrentHashMap<>();

    public void updateLastPearl(Player player) {
        lastPearlThrows.put(player, System.currentTimeMillis());
    }

    public double getCooldown(Player player) {
        // Get the time passed since last pearl in milliseconds
        long timeSinceLastPearl = System.currentTimeMillis() - lastPearlThrows.getOrDefault(player, 0L);
        
        // Return the cooldown minus the time passed and convert to seconds
        return (500 - Math.min(500, timeSinceLastPearl)) / 1000D;
    }

    public void resetCooldown(Player player) {
        lastPearlThrows.remove(player);
    }
}