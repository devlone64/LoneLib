package dev.lone64.LoneLib.common.util.config.configuration;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.io.File;

@Getter
public class Configuration {
    private final File file;
    private final String path;
    private final Plugin plugin;

    public Configuration(Plugin plugin, String path) {
        this.plugin = plugin;
        this.path = path;
        this.file = new File(plugin.getDataFolder(), this.path);
    }

    public Configuration(Plugin plugin, String dir, String path) {
        this(plugin, "%s/%s".formatted(dir, path));
    }
}