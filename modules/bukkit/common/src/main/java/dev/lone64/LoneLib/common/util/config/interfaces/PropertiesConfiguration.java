package dev.lone64.LoneLib.common.util.config.interfaces;

import dev.lone64.LoneLib.common.util.EnumUtil;
import dev.lone64.LoneLib.common.util.config.configuration.Configuration;
import dev.lone64.LoneLib.common.util.file.PropertiesUtil;
import dev.lone64.LoneLib.common.util.java.FileUtil;
import dev.lone64.LoneLib.common.util.location.LocationUtil;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public interface PropertiesConfiguration {
    boolean createNewFile();
    boolean deleteFile();
    boolean exists();

    void set(String key, Object value);
    void setIfAbuse(String key, Object value);

    void remove(String key);

    Object get(String key);
    Object get(String key, Object def);

    String getString(String key);
    String getString(String key, String def);

    Number getNumber(String key);
    Number getNumber(String key, Number def);

    boolean getBoolean(String key);
    boolean getBoolean(String key, boolean def);

    UUID getUUID(String key);
    UUID getUUID(String key, UUID def);

    Location getLocation(String key);
    Location getLocation(String key, Location def);

    Enum<?> getEnum(String key);
    Enum<?> getEnum(String key, Enum<?> def);

    List<String> list();

    boolean has(String key);

    Properties getConfig();

    class PropertiesConfigurationImpl extends Configuration implements PropertiesConfiguration {
        private final Properties config;

        public PropertiesConfigurationImpl(Plugin plugin, String path) {
            super(plugin, path);
            var resource = PropertiesUtil.loadProperties(this.getPlugin(), this.getPath());
            this.config = resource != null ? resource : new Properties();
        }

        public PropertiesConfigurationImpl(Plugin plugin, String dir, String path) {
            super(plugin, dir, path);
            var resource = PropertiesUtil.loadProperties(this.getPlugin(), this.getPath());
            this.config = resource != null ? resource : new Properties();
        }

        @Override
        public boolean createNewFile() {
            return this.getPlugin().getResource(this.getPath()) != null ?
                    FileUtil.saveResource(this.getPlugin(), this.getPath(), false) :
                    FileUtil.createNewFile(this.getFile());
        }

        @Override
        public boolean deleteFile() {
            return this.getFile().exists() && this.getFile().delete();
        }

        @Override
        public boolean exists() {
            return this.getFile().exists();
        }

        @Override
        public void set(String key, Object value) {
            this.config.put(key, value);
            PropertiesUtil.saveProperties(this.getFile(), this.config);
        }

        @Override
        public void setIfAbuse(String key, Object value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public void remove(String key) {
            if (this.has(key)) this.config.remove(key);
        }

        @Override
        public Object get(String key) {
            return this.get(key, null);
        }

        @Override
        public Object get(String key, Object def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return value;
        }

        @Override
        public String getString(String key) {
            return this.getString(key, null);
        }

        @Override
        public String getString(String key, String def) {
            var value = this.config.getProperty(key);
            if (value == null) return def;
            return value;
        }

        @Override
        public Number getNumber(String key) {
            return this.getNumber(key, null);
        }

        @Override
        public Number getNumber(String key, Number def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return (Number) value;
        }

        @Override
        public boolean getBoolean(String key) {
            return this.getBoolean(key, false);
        }

        @Override
        public boolean getBoolean(String key, boolean def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return (boolean) value;
        }

        @Override
        public UUID getUUID(String key) {
            return this.getUUID(key, null);
        }

        @Override
        public UUID getUUID(String key, UUID def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return UUID.fromString(value.toString());
        }

        @Override
        public Location getLocation(String key) {
            return this.getLocation(key, null);
        }

        @Override
        public Location getLocation(String key, Location def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return LocationUtil.deserialize(value.toString());
        }

        @Override
        public Enum<?> getEnum(String key) {
            return this.getEnum(key, null);
        }

        @Override
        public Enum<?> getEnum(String key, Enum<?> def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return EnumUtil.from(value.getClass(), value.toString());
        }

        @Override
        public List<String> list() {
            var arrays = new ArrayList<String>();
            var files = this.getFile().list();
            if (files != null) {
                for (String fileName : files) {
                    arrays.add(fileName.replace(".yml", ""));
                }
            }
            return arrays;
        }

        @Override
        public boolean has(String key) {
            return this.config.containsKey(key);
        }

        @Override
        public Properties getConfig() {
            return this.config;
        }
    }

    static PropertiesConfiguration createConfig(Plugin plugin, String path) {
        return new PropertiesConfigurationImpl(plugin, path);
    }

    static PropertiesConfiguration createConfig(Plugin plugin, String dir, String path) {
        return new PropertiesConfigurationImpl(plugin, dir, path);
    }
}