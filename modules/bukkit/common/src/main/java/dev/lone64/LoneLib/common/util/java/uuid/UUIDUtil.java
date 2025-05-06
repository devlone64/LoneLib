package dev.lone64.LoneLib.common.util.java.uuid;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class UUIDUtil {
    public static UUID from(final String name) {
        for (final OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName() != null && p.getName().equals(name)) {
                return p.getUniqueId();
            }
        }
        return null;
    }
}