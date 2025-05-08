package dev.lone64.LoneLib.common.util.file;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    public static void saveProperties(File file, Properties properties) {
        var parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdir()) {
            throw new IllegalArgumentException("An error occurred while saving the properties file.");
        }

        try (var out = new FileOutputStream(file)) {
            properties.store(out, "saved properties.");
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while saving the properties file.", e);
        }
    }

    public static Properties loadProperties(File file) {
        var properties = new Properties();
        var parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdir()) {
            return properties;
        }

        try (var in = new FileInputStream(file)) {
            properties.load(in);
            return properties;
        } catch (IOException e) {
            return properties;
        }
    }

    public static Properties loadProperties(Plugin plugin, String path) {
        var properties = new Properties();
        try (var in = plugin.getResource(path)) {
            properties.load(in);
            return properties;
        } catch (IOException e) {
            return properties;
        }
    }
}