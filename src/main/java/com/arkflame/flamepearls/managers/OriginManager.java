package com.arkflame.flamepearls.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;

// Stores and manages the origin of projectiles
public class OriginManager {
        // Origin of launch of projectiles
        private Map<Projectile, Location> projectileOrigins = new ConcurrentHashMap<>();
    
        public void setOrigin(Projectile projectile, Location location) {
            // Insert the projectile-origin
            projectileOrigins.put(projectile, location);
        }
    
        public Location getOriginAndRemove(Projectile projectile) {
            // Return the value removed
            return projectileOrigins.remove(projectile);
        }
}
