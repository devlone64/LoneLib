package dev.lone64.LoneLib.common.config;

import dev.lone64.LoneLib.common.util.EnumUtil;
import dev.lone64.LoneLib.common.util.java.FileUtil;
import dev.lone64.LoneLib.common.util.location.LocationUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class YamlConfig {
    private final File file;
    private final String path;
    private final Plugin plugin;
    private final YamlConfiguration config;

    public YamlConfig(Plugin plugin, String dir, String path) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "%s/%s".formatted(dir, path));
        this.config = YamlConfiguration.loadConfiguration(file);
        this.path = "%s/%s".formatted(dir, path);
    }

    public YamlConfig(Plugin plugin, String path) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), path);
        this.config = YamlConfiguration.loadConfiguration(file);
        this.path = path;
    }

    public boolean createNewFile() {
        return plugin.getResource(this.path) != null ?
                FileUtil.saveResource(this.plugin, this.path, false) :
                FileUtil.createNewFile(this.file);
    }

    public boolean deleteFile() {
        return file.exists() && file.delete();
    }

    public boolean exists() {
        return file.exists();
    }

    public boolean load() {
        try {
            this.config.load(this.file);
            return true;
        } catch (IOException | InvalidConfigurationException e) {
            return false;
        }
    }

    public void set(String path, Object value) {
        try {
            this.config.set(path, value);
            this.config.save(this.file);
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while saving config: %s".formatted(path), e);
        }
    }

    public void add(String path, Object value) {
        if (!this.contains(path)) this.set(path, value);
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public Object get(String path, Object def) {
        if (!this.contains(path)) return def;
        return this.get(path);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public String getString(String path, String def) {
        if (!this.contains(path)) return def;
        return this.getString(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public int getInt(String path, int def) {
        if (!this.contains(path)) return def;
        return this.getInt(path);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public double getDouble(String path, double def) {
        if (!this.contains(path)) return def;
        return this.getDouble(path);
    }

    public long getLong(String path) {
        return this.config.getLong(path);
    }

    public long getLong(String path, long def) {
        if (!this.contains(path)) return def;
        return this.getLong(path);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        if (!this.contains(path)) return def;
        return this.getBoolean(path);
    }

    public Enum<?> getEnum(String path) {
        return EnumUtil.from(Enum.class, this.getString(path).toUpperCase());
    }

    public Enum<?> getEnum(String path, Enum<?> def) {
        if (!this.contains(path)) return def;
        return this.getEnum(path);
    }

    public UUID getUUID(String path) {
        return UUID.fromString(this.getString(path));
    }

    public UUID getUUID(String path, UUID def) {
        if (!this.contains(path)) return def;
        return this.getUUID(path);
    }

    public Location getLocation(String path) {
        return LocationUtil.deserialize(this.getString(path));
    }

    public Location getLocation(String path, Location def) {
        if (!this.contains(path)) return def;
        return this.getLocation(path);
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        if (!this.contains(path)) return def;
        return this.getList(path);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public List<String> getStringList(String path, List<String> def) {
        if (!this.contains(path)) return def;
        return config.getStringList(path);
    }

    public List<String> getArrays() {
        return config.getKeys(false).stream().toList();
    }

    public List<String> getArrays(String path) {
        var section = config.getConfigurationSection(path);
        if (section == null) return new ArrayList<>();
        return section.getKeys(false).stream().toList();
    }

    public List<String> list() {
        var arrays = new ArrayList<String>();
        var files = this.file.list();
        if (files != null) {
            for (String fileName : files) {
                arrays.add(fileName.replace(".yml", ""));
            }
        }
        return arrays;
    }
}