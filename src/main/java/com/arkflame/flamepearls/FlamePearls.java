package com.arkflame.flamepearls;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.arkflame.flamepearls.listeners.CreatureSpawnListener;
import com.arkflame.flamepearls.listeners.EntityDamageByEntityListener;
import com.arkflame.flamepearls.listeners.PlayerInteractListener;
import com.arkflame.flamepearls.listeners.PlayerQuitListener;
import com.arkflame.flamepearls.listeners.PlayerTeleportListener;
import com.arkflame.flamepearls.listeners.ProjectileHitListener;
import com.arkflame.flamepearls.listeners.ProjectileLaunchListener;
import com.arkflame.flamepearls.managers.CooldownManager;
import com.arkflame.flamepearls.managers.OriginManager;

public class FlamePearls extends JavaPlugin implements Listener {
    private static OriginManager originManager;
    private static CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        // Save default config
        this.saveDefaultConfig();

        // Set static instance
        FlamePearls.instance = this;

        // Get the plugin manager
        PluginManager pluginManager = getServer().getPluginManager();

        // Create the origin manager
        originManager = new OriginManager();

        // Create the cooldown manager
        cooldownManager = new CooldownManager();

        // Register the event listener
        pluginManager.registerEvents(this, this);
        // Register CreatureSpawnListener
        pluginManager.registerEvents(new CreatureSpawnListener(), this);
        // Register EntityDamageByEntityListener
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        // Register Player Interact Listener
        pluginManager.registerEvents(new PlayerInteractListener(cooldownManager), this);
        // Register Player quit listener
        pluginManager.registerEvents(new PlayerQuitListener(cooldownManager), this);
        // Register PlayerTeleportListener
        pluginManager.registerEvents(new PlayerTeleportListener(), this);
        // Register ProjectileHitListener
        pluginManager.registerEvents(new ProjectileHitListener(originManager), this);
        // Register ProjectileLaunchListener
        pluginManager.registerEvents(new ProjectileLaunchListener(originManager), this);
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

    /**
     * Use this to get and manage the ender pearl cooldown of players
     */
    public static CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}