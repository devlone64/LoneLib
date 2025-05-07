package dev.lone64.LoneLib.common.util.config;

import dev.lone64.LoneLib.common.util.config.interfaces.JsonConfiguration;
import dev.lone64.LoneLib.common.util.config.interfaces.YmlConfiguration;
import dev.lone64.LoneLib.common.util.config.interfaces.JsonConfiguration.JsonConfigurationImpl;
import dev.lone64.LoneLib.common.util.config.interfaces.YmlConfiguration.YmlConfigurationImpl;
import org.bukkit.plugin.Plugin;

public class ConfigUtil {
    public static YmlConfiguration loadYml(Plugin plugin, String path) {
        return new YmlConfigurationImpl(plugin, path);
    }
    public static YmlConfiguration loadYml(Plugin plugin, String dir, String path) {
        return new YmlConfigurationImpl(plugin, dir, path);
    }

    public static JsonConfiguration loadJson(Plugin plugin, String path) {
        return new JsonConfigurationImpl(plugin, path);
    }
    public static JsonConfiguration loadJson(Plugin plugin, String dir, String path) {
        return new JsonConfigurationImpl(plugin, dir, path);
    }
}