package dev.lone64.LoneLib.common.util.java;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static boolean createNewFile(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IllegalArgumentException("An error occurred while creating folder: %s".formatted(parent.getPath()));
            }
        }

        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while creating file: %s".formatted(file.getPath()), e);
        }
    }

    public static boolean saveResource(Plugin plugin, String path, boolean replace) {
        if (plugin.getResource(path) == null) return false;
        plugin.saveResource(path, replace);
        return true;
    }
}