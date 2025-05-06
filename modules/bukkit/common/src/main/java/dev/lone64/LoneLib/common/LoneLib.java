package dev.lone64.LoneLib.common;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

public class LoneLib {
    public static Economy getEconomy() {
        var provider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (provider == null) return null;
        return provider.getProvider();
    }
}