package dev.lone64.LoneLib.common.command;

import dev.lone64.LoneLib.common.command.data.Permission;
import dev.lone64.LoneLib.common.command.data.type.MsgType;
import dev.lone64.LoneLib.common.command.other.util.CommandUtil;
import dev.lone64.LoneLib.common.command.target.Target;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CommandInstance {
    private Plugin plugin;
    private String name;
    private String usage = "/<command>";
    private String description = "No description provided.";
    private List<String> aliases = new ArrayList<>();

    private Target<?> target;
    private Permission permission = new Permission("", "&c당신은 해당 명령어를 사용할 권한이 없습니다.");

    private final Map<MsgType, String> options = new HashMap<>();
    private final Map<String, Target<?>> commands = new HashMap<>();

    public CommandInstance(Plugin plugin) {
        this(plugin, "");
    }

    public CommandInstance(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name.toLowerCase();
    }

    public CommandInstance setPlugin(Plugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public CommandInstance setName(String name) {
        this.name = name;
        return this;
    }

    public CommandInstance setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public CommandInstance setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandInstance setAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandInstance setPermission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public CommandInstance setPermission(String node) {
        this.permission.setNode(node);
        return this;
    }

    public CommandInstance setPermission(String node, String message) {
        this.permission.setNode(node);
        this.permission.setMessage(message);
        return this;
    }

    public CommandInstance setOption(MsgType type, String message) {
        this.options.put(type, message);
        return this;
    }

    public CommandInstance onExecute(Target<?> target) {
        this.target = target;
        return this;
    }

    public CommandInstance onArgument(String name, Target<?> target) {
        this.commands.put(name, target);
        return this;
    }

    public void register() {
        CommandUtil.register(this);
    }

    public static CommandInstance fetch(Plugin plugin, String name) {
        return new CommandInstance(plugin, name);
    }
}