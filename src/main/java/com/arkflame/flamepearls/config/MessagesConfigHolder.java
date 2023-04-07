package com.arkflame.flamepearls.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class MessagesConfigHolder {
    private Map<String, String> messages = new ConcurrentHashMap<>();

    public void load(Configuration config) {
        // Get messages section
        ConfigurationSection section = config.getConfigurationSection("messages");

        // Check if section exists
        if (section != null) {
            // Iterate over section keys
            for (String key : section.getKeys(false)) {
                // Insert the key and value
                messages.put(key, section.getString(key));
            }
        }
    }

    public String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', messages.getOrDefault(key, ""));
    }
}
