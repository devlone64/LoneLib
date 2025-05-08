package dev.lone64.LoneLib.common.util.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class JsonUtil {
    public static void saveJson(File file, JsonObject src) {
        var parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdir()) {
            throw new IllegalArgumentException("An error occurred while saving the json file.");
        }

        try (var writer = new FileWriter(file)) {
            var gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(src, writer);
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while saving the json file.", e);
        }
    }

    public static JsonObject loadJson(File file) {
        var parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdir()) {
            return null;
        }

        var gson = new Gson();
        try (var reader = new FileReader(file)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static JsonObject loadJson(Plugin plugin, String path) {
        var resource = plugin.getResource(path);
        if (resource == null) return null;
        var gson = new Gson();
        try (var reader = new InputStreamReader(resource)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }
}