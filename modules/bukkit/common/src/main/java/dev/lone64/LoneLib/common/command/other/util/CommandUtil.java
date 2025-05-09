package dev.lone64.LoneLib.common.command.other.util;

import dev.lone64.LoneLib.common.command.CommandInstance;
import dev.lone64.LoneLib.common.command.RootCommand;
import dev.lone64.LoneLib.common.command.other.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public class CommandUtil {
    public static void register(CommandInstance command) {
        var commandMap = getCommandMap();
        if (commandMap == null) return;

        unregister(command.getName());

        var mainCommand = createCommand(command.getPlugin(), command.getName());
        if (mainCommand == null) return;

        var route = new RootCommand(command);
        mainCommand.setUsage(command.getUsage());
        mainCommand.setAliases(command.getAliases());
        mainCommand.setDescription(command.getDescription());

        mainCommand.setPermission(command.getPermission().getNode());
        mainCommand.setPermissionMessage(command.getPermission().getMessage());

        mainCommand.setExecutor(route::handleCommand);
        mainCommand.setTabCompleter(route::handleTab);

        commandMap.register(command.getPlugin().getName(), mainCommand);

        CommandManager.register(command.getName(), mainCommand);
        CommandManager.register(command.getPlugin().getName() + ":" + command.getName(), mainCommand);

        command.getAliases().forEach(alias -> {
            var aliasCommand = createCommand(command.getPlugin(), alias);
            if (aliasCommand == null) return;

            aliasCommand.setUsage(command.getUsage());
            aliasCommand.setDescription(command.getDescription());

            aliasCommand.setPermission(command.getPermission().getNode());
            aliasCommand.setPermissionMessage(command.getPermission().getMessage());

            aliasCommand.setExecutor(route::handleCommand);
            aliasCommand.setTabCompleter(route::handleTab);

            commandMap.register(command.getPlugin().getName(), aliasCommand);

            CommandManager.register(alias, aliasCommand);
            CommandManager.register(command.getPlugin().getName() + ":" + alias, aliasCommand);
        });
    }

    public static void unregister(String name) {
        var commandMap = getCommandMap();
        if (commandMap == null) return;

        var command = commandMap.getCommand(name.toLowerCase());
        if (command == null || command.getName().equalsIgnoreCase(name)) return;

        command.unregister(commandMap);
        CommandManager.unregister(name);
    }

    private static PluginCommand createCommand(Plugin plugin, String name) {
        try {
            var commandMap = getCommandMap();
            if (commandMap == null) return null;

            var constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            var command = constructor.newInstance(name, plugin);
            commandMap.register(plugin.getName(), command);
            return command;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }

    private static CommandMap getCommandMap() {
        try {
            var f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            return (CommandMap) f.get(Bukkit.getServer());
        } catch (Exception e) {
            return null;
        }
    }
}