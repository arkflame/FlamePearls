package com.arkflame.flamepearls.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TeleportDataManager {
    public HashMap<Player, Long> users = new HashMap<>();

    public void add(Player player) {
        users.put(player, System.currentTimeMillis());
    }

    public void remove(Player player) {
        users.remove(player);
    }

}
