package dev.lone64.LoneLib.common.config;

import dev.lone64.LoneLib.common.util.EnumUtil;
import dev.lone64.LoneLib.common.util.java.FileUtil;
import dev.lone64.LoneLib.common.util.location.LocationUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
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

    public void set(String key, Object value) {
        try {
            this.config.set(key, value != null ? serialize(value) : null);
            this.config.save(this.file);
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while saving config: %s".formatted(key), e);
        }
    }

    public void setIfAbuse(String key, Object value) {
        if (!contains(key)) set(key, value);
    }

    public Object get(String key) {
        Object value = config.get(key);
        if (value == null) return null;
        return deserialize(value);
    }

    public String getString(String key) {
        return get(key).toString();
    }

    public int getInt(String key) {
        return (int) get(key);
    }

    public double getDouble(String key) {
        return (double) get(key);
    }

    public long getLong(String key) {
        return (long) get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) get(key);
    }

    public Enum<?> getEnum(String key) {
        return (Enum<?>) get(key);
    }

    public UUID getUUID(String key) {
        return (UUID) get(key);
    }

    public Location getLocation(String key) {
        return (Location) get(key);
    }

    public boolean contains(String key) {
        return config.contains(key);
    }

    public List<?> getList(String key) {
        return config.getList(key);
    }

    public List<?> getList(String key, List<?> def) {
        return config.getList(key, def);
    }

    public List<String> getStringList(String key) {
        return config.getStringList(key);
    }

    public List<String> getKeys() {
        return config.getKeys(false).stream().toList();
    }

    public List<String> getKeys(String key) {
        var section = config.getConfigurationSection(key);
        if (section == null) return new ArrayList<>();
        return section.getKeys(false).stream().toList();
    }

    public List<String> getFiles() {
        var arrays = new ArrayList<String>();
        var files = this.file.list();
        if (files != null) {
            for (String fileName : files) {
                arrays.add(fileName.replace(".yml", ""));
            }
        }
        return arrays;
    }

    private Object serialize(Object value) {
        Class<?> type = value.getClass();
        if (type.isEnum()) return EnumUtil.to(value);
        else if (type == UUID.class) return value.toString();
        else if (type == Location.class) return LocationUtil.serialize((Location) value);
        else if (value instanceof ConfigurationSerializable serializable) return serializable.serialize();
        return value;
    }

    private Object deserialize(Object value) {
        Class<?> type = value.getClass();
        if (type.isEnum()) return EnumUtil.from(type, value.toString());
        else if (type == UUID.class) return UUID.fromString(value.toString());
        else if (type == Location.class) return LocationUtil.deserialize(value.toString());
        else if (value instanceof ConfigurationSection section) return ConfigurationSerialization.deserializeObject(section.getValues(false), type.asSubclass(ConfigurationSerializable.class));
        return value;
    }
}