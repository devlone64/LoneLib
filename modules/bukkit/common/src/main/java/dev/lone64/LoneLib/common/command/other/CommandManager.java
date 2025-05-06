package dev.lone64.LoneLib.common.command.other;

import org.bukkit.command.PluginCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Map<String, PluginCommand> RAON_COMMAND_BUILDER_MAP = new HashMap<>();

    public static void register(String name, PluginCommand command) {
        RAON_COMMAND_BUILDER_MAP.put(name.toLowerCase(), command);
    }

    public static void unregister(String name) {
        RAON_COMMAND_BUILDER_MAP.remove(name.toLowerCase());
    }

    public static PluginCommand find(String name) {
        return RAON_COMMAND_BUILDER_MAP.get(name.toLowerCase());
    }

    public static boolean is(String name) {
        return find(name) != null;
    }
}