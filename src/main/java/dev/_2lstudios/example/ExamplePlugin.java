package dev._2lstudios.example;

import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.example.commands.ExampleCommand;
import dev._2lstudios.example.listeners.PlayerJoinListener;
import dev._2lstudios.example.tasks.ExampleTask;

public class ExamplePlugin extends JavaPlugin {
    
    @Override
    public void onEnable () {
        // Save default config
        this.saveDefaultConfig();

        // Set static instance
        ExamplePlugin.instance = this;

        // Register the example command
        this.getCommand("example").setExecutor(new ExampleCommand());
        
        // Register the example listener
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // Register the example task
        final long taskRepeatEvery = this.getConfig().getInt("task-repeat-every") * 20L;
        this.getServer().getScheduler().runTaskTimer(this, new ExampleTask(), taskRepeatEvery, taskRepeatEvery);
    }

    private static ExamplePlugin instance;

    public static ExamplePlugin getInstance () {
        return ExamplePlugin.instance;
    }
}