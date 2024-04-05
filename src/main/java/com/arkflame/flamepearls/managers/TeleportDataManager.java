package com.arkflame.flamepearls.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportDataManager {
    private Map<UUID, Long> users = new HashMap<>();

    public void add(UUID uuid) {
        users.put(uuid, System.currentTimeMillis());
    }

    public void remove(UUID uuid) {
        users.remove(uuid);
    }

    public Long get(UUID uuid) {
        return users.getOrDefault(uuid, 0L);
    }

    public boolean contains(UUID uuid) {
        return users.containsKey(uuid);
    }

    public void add(Player player) {
        add(player.getUniqueId());
    }

    public void remove(Player player) {
        remove(player.getUniqueId());
    }

    public Long get(Player player) {
        return get(player.getUniqueId());
    }

    public boolean contains(Player player) {
        return contains(player.getUniqueId());
    }
}
