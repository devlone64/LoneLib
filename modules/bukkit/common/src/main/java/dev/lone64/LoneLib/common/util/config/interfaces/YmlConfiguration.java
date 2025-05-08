package dev.lone64.LoneLib.common.util.config.interfaces;

import dev.lone64.LoneLib.common.util.EnumUtil;
import dev.lone64.LoneLib.common.util.config.annotation.Config;
import dev.lone64.LoneLib.common.util.config.configuration.Configuration;
import dev.lone64.LoneLib.common.util.java.FileUtil;
import dev.lone64.LoneLib.common.util.location.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public interface YmlConfiguration {
    boolean createNewFile();
    boolean deleteFile();
    boolean exists();

    void save(Object object);

    void set(String key, Object value);
    void setIfAbuse(String key, Object value);

    Object serialized(Object object);
    Object deserialized(Object object);

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

    List<?> getList(String key);
    List<?> getList(String key, List<?> def);

    List<String> getStringList(String key);

    List<String> getKeys();
    List<String> getKeys(String key);

    List<String> list();

    boolean has(String key);

    YamlConfiguration getConfig();

    class YmlConfigurationImpl extends Configuration implements YmlConfiguration {
        private final YamlConfiguration config;

        public YmlConfigurationImpl(Plugin plugin, String path) {
            super(plugin, path);
            this.config = YamlConfiguration.loadConfiguration(this.getFile());
        }

        public YmlConfigurationImpl(Plugin plugin, String dir, String path) {
            super(plugin, dir, path);
            this.config = YamlConfiguration.loadConfiguration(this.getFile());
        }

        @Override
        public boolean createNewFile() {
            return this.getPlugin().getResource(this.getPath()) != null ?
                    FileUtil.saveResource(this.getPlugin(), this.getPath(), false) :
                    FileUtil.createNewFile(this.getFile());
        }

        @Override
        public boolean deleteFile() {
            return this.exists() && this.getFile().delete();
        }

        @Override
        public boolean exists() {
            return this.getFile().exists();
        }

        @Override
        public void save(Object object) {
            try {
                for (var entry : this.configurables(object.getClass()).entrySet()) {
                    var field = entry.getKey();
                    var config = entry.getValue();
                    var key = config.path().isEmpty() ? field.getName().replace("_", "-") : config.path();
                    var value = this.config.get(key);
                    if ((value != null && field.get(this) == null) || (value != null && field.get(this) != null && value.equals(field.get(this))))
                        continue;
                    value = this.serialized(field.get(object));
                    if (value == null) continue;
                    this.config.set(key, serialize(value));
                    this.config.save(this.getFile());
                }
            } catch (final IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid argument passed during code execution.", e);
            } catch (final IllegalAccessException e) {
                throw new IllegalArgumentException("The specified class, field, method, constructor cannot be accessed during code execution.");
            } catch (final IOException e) {
                throw new IllegalArgumentException("An error occurred while saving the yaml file.", e);
            }
        }

        @Override
        public void set(String key, Object value) {
            try {
                this.config.set(key, value != null ? this.serialize(value) : null);
                this.config.save(this.getFile());
            } catch (IOException e) {
                throw new IllegalArgumentException("An error occurred while saving config: %s".formatted(key), e);
            }
        }

        @Override
        public void setIfAbuse(String key, Object value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public Object serialized(Object object) {
            if (object instanceof ConfigurationSerializable src)
                return src.serialize();
            return object;
        }

        @Override
        public Object deserialized(Object object) {
            var type = object.getClass();
            if (object instanceof ConfigurationSection src)
                return ConfigurationSerialization.deserializeObject(src.getValues(false), type.asSubclass(ConfigurationSerializable.class));
            return object;
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
            var value = this.config.get(key);
            if (value == null) return def;
            return value.toString();
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
        public List<?> getList(String key) {
            return this.getList(key, new ArrayList<>());
        }

        @Override
        public List<?> getList(String key, List<?> def) {
            var value = this.config.getList(key);
            if (value == null) return def;
            return value;
        }

        @Override
        public List<String> getStringList(String key) {
            return this.config.getStringList(key);
        }

        @Override
        public List<String> getKeys() {
            return this.config.getKeys(false).stream().toList();
        }

        @Override
        public List<String> getKeys(String key) {
            var section = config.getConfigurationSection(key);
            if (section == null) return new ArrayList<>();
            return section.getKeys(false).stream().toList();
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
            return this.config.contains(key);
        }

        @Override
        public YamlConfiguration getConfig() {
            return this.config;
        }

        private Object serialize(Object value) {
            Class<?> type = value.getClass();
            if (type.isEnum()) return EnumUtil.to(value);
            else if (type == UUID.class) return value.toString();
            else if (type == Location.class) return LocationUtil.serialize((Location) value);
            else if (value instanceof ConfigurationSerializable serializable) return serializable.serialize();
            return value;
        }

        private Map<Field, Config> configurables(Class<?> clazz) {
            var superclasses = superclasses(clazz, Object.class);
            var configurables = new HashMap<Field, Config>();
            for (var superclass : superclasses) {
                for (Field field : superclass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Config.class)) {
                        field.setAccessible(true);
                        configurables.put(field, field.getAnnotation(Config.class));
                    }
                }
            }
            return configurables;
        }

        private List<Class<?>> superclasses(Class<?> clazz, Class<?> limit) {
            Class<?> current = clazz;
            List<Class<?>> superclasses = new ArrayList<>();
            while (current != limit) {
                superclasses.add(current);
                current = current.getSuperclass();
            }
            return superclasses;
        }
    }

    static YmlConfiguration createConfig(Plugin plugin, String path) {
        return new YmlConfigurationImpl(plugin, path);
    }

    static YmlConfiguration createConfig(Plugin plugin, String dir, String path) {
        return new YmlConfigurationImpl(plugin, dir, path);
    }
}