package dev.lone64.LoneLib.common.util.config.interfaces;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.lone64.LoneLib.common.util.EnumUtil;
import dev.lone64.LoneLib.common.util.config.annotation.Config;
import dev.lone64.LoneLib.common.util.config.configuration.Configuration;
import dev.lone64.LoneLib.common.util.file.JsonUtil;
import dev.lone64.LoneLib.common.util.java.FileUtil;
import dev.lone64.LoneLib.common.util.location.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public interface JsonConfiguration {
    boolean createNewFile();
    boolean deleteFile();
    boolean exists();

    void save(Object object);

    void set(String key, String value);
    void set(String key, Number value);
    void set(String key, Boolean value);
    void set(String key, UUID value);
    void set(String key, Location value);
    void set(String key, Enum<?> value);
    void set(String key, JsonElement value);
    void setIfAbuse(String key, String value);
    void setIfAbuse(String key, Number value);
    void setIfAbuse(String key, Boolean value);
    void setIfAbuse(String key, UUID value);
    void setIfAbuse(String key, Location value);
    void setIfAbuse(String key, Enum<?> value);
    void setIfAbuse(String key, JsonElement value);

    Object serialized(Object object);
    Object deserialized(Object object);

    JsonElement get(String key);
    JsonElement get(String key, JsonElement def);
    JsonElement get(JsonElement src, String key);
    JsonElement get(JsonElement src, String key, JsonElement def);

    String getString(String key);
    String getString(String key, String def);
    String getString(JsonElement src, String key);
    String getString(JsonElement src, String key, String def);

    Number getNumber(String key);
    Number getNumber(String key, Number def);
    Number getNumber(JsonElement src, String key);
    Number getNumber(JsonElement src, String key, Number def);

    boolean getBoolean(String key);
    boolean getBoolean(String key, boolean def);
    boolean getBoolean(JsonElement src, String key);
    boolean getBoolean(JsonElement src, String key, boolean def);

    UUID getUUID(String key);
    UUID getUUID(String key, UUID def);
    UUID getUUID(JsonElement src, String key);
    UUID getUUID(JsonElement src, String key, UUID def);

    Location getLocation(String key);
    Location getLocation(String key, Location def);
    Location getLocation(JsonElement src, String key);
    Location getLocation(JsonElement src, String key, Location def);

    Enum<?> getEnum(String key);
    Enum<?> getEnum(String key, Enum<?> def);
    Enum<?> getEnum(JsonElement src, String key);
    Enum<?> getEnum(JsonElement src, String key, Enum<?> def);

    JsonArray getArray(String key);
    JsonArray getArray(String key, JsonArray def);
    JsonArray getArray(JsonElement src, String key);
    JsonArray getArray(JsonElement src, String key, JsonArray def);

    List<String> list();

    boolean has(String key);
    boolean has(JsonElement src, String key);

    class JsonConfigurationImpl extends Configuration implements JsonConfiguration {
        private JsonObject config;

        public JsonConfigurationImpl(Plugin plugin, String path) {
            super(plugin, path);
            var resource = JsonUtil.loadJson(this.getPlugin(), this.getPath());
            this.config = resource != null ? resource : new JsonObject();
        }

        public JsonConfigurationImpl(Plugin plugin, String dir, String path) {
            super(plugin, dir, path);
            var resource = JsonUtil.loadJson(this.getPlugin(), this.getPath());
            this.config = resource != null ? resource : new JsonObject();
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
        public void save(Object object) {
            try {
                if (this.config == null) this.config = new JsonObject();
                for (var entry : this.configurables(object.getClass()).entrySet()) {
                    var field = entry.getKey();
                    var config = entry.getValue();
                    var path = config.path().isEmpty() ? field.getName().replace("_", "-") : config.path();
                    var value = field.get(object);
                    if (value == null) continue;

                    var jsonValue = new Gson().toJsonTree(value);
                    if (jsonValue == null || jsonValue.isJsonNull()) continue;

                    var parts = path.split("\\.");
                    var current = this.config != null ? this.config : new JsonObject();
                    for (int i = 0; i < parts.length - 1; i++) {
                        var part = parts[i];
                        if (!current.has(part) || !current.get(part).isJsonObject()) {
                            current.add(part, new JsonObject());
                        }
                        current = current.getAsJsonObject(part);
                    }

                    var key = parts[parts.length - 1];
                    var existing = current.get(key);
                    if (existing != null && existing.equals(jsonValue)) continue;

                    current.add(key, jsonValue);
                    JsonUtil.saveJson(this.getFile(), this.config);
                }
            } catch (final IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid argument passed during code execution.", e);
            } catch (final IllegalAccessException e) {
                throw new IllegalArgumentException("The specified class, field, method, constructor cannot be accessed during code execution.");
            }
        }

        @Override
        public void set(String key, String value) {
            this.config.addProperty(key, value);
            JsonUtil.saveJson(this.getFile(), this.config);
        }

        @Override
        public void set(String key, Number value) {
            this.config.addProperty(key, value);
            JsonUtil.saveJson(this.getFile(), this.config);
        }

        @Override
        public void set(String key, Boolean value) {
            this.config.addProperty(key, value);
            JsonUtil.saveJson(this.getFile(), this.config);
        }

        @Override
        public void set(String key, UUID value) {
            this.config.addProperty(key, value.toString());
            JsonUtil.saveJson(this.getFile(), this.config);
        }

        @Override
        public void set(String key, Location value) {
            this.config.addProperty(key, LocationUtil.serialize(value));
            JsonUtil.saveJson(this.getFile(), this.config);
        }

        @Override
        public void set(String key, Enum<?> value) {
            this.config.addProperty(key, value.name());
            JsonUtil.saveJson(this.getFile(), this.config);
        }

        @Override
        public void set(String key, JsonElement value) {
            this.config.add(key, value);
            JsonUtil.saveJson(this.getFile(), this.config);
        }

        @Override
        public void setIfAbuse(String key, String value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public void setIfAbuse(String key, Number value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public void setIfAbuse(String key, Boolean value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public void setIfAbuse(String key, UUID value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public void setIfAbuse(String key, Location value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public void setIfAbuse(String key, Enum<?> value) {
            if (!this.has(key)) this.set(key, value);
        }

        @Override
        public void setIfAbuse(String key, JsonElement value) {
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
        public JsonElement get(String key) {
            return this.get(key, null);
        }

        @Override
        public JsonElement get(String key, JsonElement def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return value;
        }

        @Override
        public JsonElement get(JsonElement src, String key) {
            return this.get(src, key, null);
        }

        @Override
        public JsonElement get(JsonElement src, String key, JsonElement def) {
            var value = src.getAsJsonObject().get(key);
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
            return value.getAsString();
        }

        @Override
        public String getString(JsonElement src, String key) {
            return this.getString(src, key, null);
        }

        @Override
        public String getString(JsonElement src, String key, String def) {
            var value = src.getAsJsonObject().get(key);
            if (value == null) return def;
            return value.getAsString();
        }

        @Override
        public Number getNumber(String key) {
            return this.getNumber(key, null);
        }

        @Override
        public Number getNumber(String key, Number def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return value.getAsNumber();
        }

        @Override
        public Number getNumber(JsonElement src, String key) {
            return this.getNumber(src, key, null);
        }

        @Override
        public Number getNumber(JsonElement src, String key, Number def) {
            var value = src.getAsJsonObject().get(key);
            if (value == null) return def;
            return value.getAsNumber();
        }

        @Override
        public boolean getBoolean(String key) {
            return this.getBoolean(key, false);
        }

        @Override
        public boolean getBoolean(String key, boolean def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return value.getAsBoolean();
        }

        @Override
        public boolean getBoolean(JsonElement src, String key) {
            return this.getBoolean(src, key, false);
        }

        @Override
        public boolean getBoolean(JsonElement src, String key, boolean def) {
            var value = src.getAsJsonObject().get(key);
            if (value == null) return def;
            return value.getAsBoolean();
        }

        @Override
        public UUID getUUID(String key) {
            return this.getUUID(key, null);
        }

        @Override
        public UUID getUUID(String key, UUID def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return UUID.fromString(value.getAsString());
        }

        @Override
        public UUID getUUID(JsonElement src, String key) {
            return this.getUUID(src, key, null);
        }

        @Override
        public UUID getUUID(JsonElement src, String key, UUID def) {
            var value = src.getAsJsonObject().get(key);
            if (value == null) return def;
            return UUID.fromString(value.getAsString());
        }

        @Override
        public Location getLocation(String key) {
            return this.getLocation(key, null);
        }

        @Override
        public Location getLocation(String key, Location def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return LocationUtil.deserialize(value.getAsString());
        }

        @Override
        public Location getLocation(JsonElement src, String key) {
            return this.getLocation(src, key, null);
        }

        @Override
        public Location getLocation(JsonElement src, String key, Location def) {
            var value = src.getAsJsonObject().get(key);
            if (value == null) return def;
            return LocationUtil.deserialize(value.getAsString());
        }

        @Override
        public Enum<?> getEnum(String key) {
            return this.getEnum(key, null);
        }

        @Override
        public Enum<?> getEnum(String key, Enum<?> def) {
            var value = this.config.get(key);
            if (value == null) return def;
            var classType = value.getAsString().getClass();
            return EnumUtil.from(classType, value.getAsString());
        }

        @Override
        public Enum<?> getEnum(JsonElement src, String key) {
            return this.getEnum(src, key, null);
        }

        @Override
        public Enum<?> getEnum(JsonElement src, String key, Enum<?> def) {
            var value = src.getAsJsonObject().get(key);
            if (value == null) return def;
            var classType = value.getAsString().getClass();
            return EnumUtil.from(classType, value.getAsString());
        }

        @Override
        public JsonArray getArray(String key) {
            return this.getArray(key, new JsonArray());
        }

        @Override
        public JsonArray getArray(String key, JsonArray def) {
            var value = this.config.get(key);
            if (value == null) return def;
            return value.getAsJsonArray();
        }

        @Override
        public JsonArray getArray(JsonElement src, String key) {
            return this.getArray(src, key, new JsonArray());
        }

        @Override
        public JsonArray getArray(JsonElement src, String key, JsonArray def) {
            var value = src.getAsJsonObject().get(key);
            if (value == null) return def;
            return value.getAsJsonArray();
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
            return this.config.has(key);
        }

        @Override
        public boolean has(JsonElement src, String key) {
            return src.getAsJsonObject().has(key);
        }

        private JsonElement serialize(Object value) {
            var gson = new Gson();
            if (value instanceof Location src) return gson.toJsonTree(LocationUtil.serialize(src));
            else if (value instanceof UUID src) return gson.toJsonTree(src.toString());
            return gson.toJsonTree(value);
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

    static JsonConfiguration createConfig(Plugin plugin, String path) {
        return new JsonConfigurationImpl(plugin, path);
    }

    static JsonConfiguration createConfig(Plugin plugin, String dir, String path) {
        return new JsonConfigurationImpl(plugin, dir, path);
    }
}