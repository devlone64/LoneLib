package dev.lone64.LoneLib.common.util.config;

import dev.lone64.LoneLib.common.util.config.interfaces.JsonConfiguration;
import dev.lone64.LoneLib.common.util.config.interfaces.PropertiesConfiguration;
import dev.lone64.LoneLib.common.util.config.interfaces.YmlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigUtil {
    @SuppressWarnings("unchecked")
    public static <C> C fetch(Class<C> type, Plugin plugin, String path) {
        if (type == JsonConfiguration.class) return (C) JsonConfiguration.createConfig(plugin, path);
        else if (type == PropertiesConfiguration.class) return (C) PropertiesConfiguration.createConfig(plugin, path);
        return (C) YmlConfiguration.createConfig(plugin, path);
    }
    @SuppressWarnings("unchecked")
    public static <C> C fetch(Class<C> type, Plugin plugin, String dir, String path) {
        if (type == JsonConfiguration.class) return (C) JsonConfiguration.createConfig(plugin, dir, path);
        else if (type == PropertiesConfiguration.class) return (C) PropertiesConfiguration.createConfig(plugin, dir, path);
        return (C) YmlConfiguration.createConfig(plugin, dir, path);
    }
}