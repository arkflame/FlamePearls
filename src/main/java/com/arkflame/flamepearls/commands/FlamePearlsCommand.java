package com.arkflame.flamepearls.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.arkflame.flamepearls.config.GeneralConfigHolder;
import com.arkflame.flamepearls.config.MessagesConfigHolder;
import com.arkflame.flamepearls.managers.OriginManager;

public class FlamePearlsCommand implements CommandExecutor {
    private GeneralConfigHolder generalConfigHolder;
    private OriginManager originManager;
    private String statsMessage;

    public FlamePearlsCommand(GeneralConfigHolder generalConfigHolder, OriginManager originManager, MessagesConfigHolder messagesConfigHolder) {
        this.generalConfigHolder = generalConfigHolder;
        this.originManager = originManager;
        statsMessage = messagesConfigHolder.getMessage("stats");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Set the damage
        statsMessage = statsMessage.replace("{damage}", String.valueOf(generalConfigHolder.getPearlDamageOther()));
        // Set the damage
        statsMessage = statsMessage.replace("{damage-self}", String.valueOf(generalConfigHolder.getPearlDamageSelf()));
        // Set the damage
        statsMessage = statsMessage.replace("{cooldown}", String.valueOf(generalConfigHolder.getPearlCooldown()));
        // Set the damage
        statsMessage = statsMessage.replace("{thrown}", String.valueOf(originManager.getProjectileCount()));
        // Send the message
        sender.sendMessage(statsMessage);

        // Return
        return true;
    }
}
