package dev._2lstudios.example.tasks;

import org.bukkit.Bukkit;

import dev._2lstudios.example.ExamplePlugin;

public class ExampleTask implements Runnable {
    @Override
    public void run() {
        final String message = ExamplePlugin.getInstance().getConfig().getString("messages.from-task");
        Bukkit.getServer().broadcastMessage(message);
    }
}