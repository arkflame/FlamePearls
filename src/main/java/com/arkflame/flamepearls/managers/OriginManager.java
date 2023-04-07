package com.arkflame.flamepearls.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;

// Stores and manages the origin of projectiles
public class OriginManager {
    // Origin of launch of projectiles
    private Map<Projectile, Location> projectileOrigins = new ConcurrentHashMap<>();

    // Counter for times a projectile had been added
    private int projectileCount = 0;

    public void setOrigin(Projectile projectile, Location location) {
        // Insert the projectile-origin
        projectileOrigins.put(projectile, location);
        // Add new projectile to count
        projectileCount++;
    }

    public Location getOriginAndRemove(Projectile projectile) {
        // Return the value removed
        return projectileOrigins.remove(projectile);
    }

    public int getProjectileCount() {
        return projectileCount;
    }
}
