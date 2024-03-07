package com.arkflame.flamepearls.config;

import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;

import com.arkflame.flamepearls.FlamePearls;

public class GeneralConfigHolder {
    // Disable random endermite spawning
    private boolean disableEndermites = true;
    private double endermiteChance = 0.0;

    // Damage modifiers
    private double pearlDamageSelf = 5;
    private double pearlDamageOther = 2;

    // The pearl cooldown in seconds
    private double pearlCooldown = 0.5;

    // Sound played when teleporting
    private Sound pearlSound = null;

    public void load(Configuration config) {
        // Load disable endermites
        disableEndermites = config.getBoolean("disable-endermites", disableEndermites);
        endermiteChance = config.getDouble("endermite-chance", endermiteChance);

        // Load pearl damage self
        pearlDamageSelf = config.getDouble("pearl-damage-self", pearlDamageSelf);

        // Load pearl damage other
        pearlDamageOther = config.getDouble("pearl-damage-other", pearlDamageOther);

        // Load pearl cooldown
        pearlCooldown = config.getDouble("pearl-cooldown", pearlCooldown);

        // Load pearl sound name
        String pearlSoundName = config.getString("pearl-sound");

        // Try processing pearl sound
        try {
            // Process pearl sound
            pearlSound = Sound.valueOf(pearlSoundName);
        } catch (IllegalArgumentException ex) {
            FlamePearls.getInstance().getLogger().warning("Invalid pearl sound: " + pearlSoundName);
        }
    }

    public boolean isDisableEndermites() {
        return disableEndermites;
    }

    public double getPearlDamageSelf() {
        return pearlDamageSelf;
    }

    public double getPearlDamageOther() {
        return pearlDamageOther;
    }

    public double getPearlCooldown() {
        return pearlCooldown;
    }

    public Sound getPearlSound() {
        return pearlSound;
    }

    public double getEndermiteChance() {
        return endermiteChance;
    }
}
