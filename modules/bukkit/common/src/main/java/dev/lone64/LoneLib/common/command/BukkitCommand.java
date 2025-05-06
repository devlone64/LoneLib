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
public class BukkitCommand {
    private Plugin plugin;
    private String name;
    private String usage = "/<command>";
    private String description = "No description provided.";
    private List<String> aliases = new ArrayList<>();

    private Target<?> target;
    private Permission permission = new Permission("", "&c당신은 해당 명령어를 사용할 권한이 없습니다.");

    private final Map<MsgType, String> options = new HashMap<>();
    private final Map<String, Target<?>> commands = new HashMap<>();

    public BukkitCommand(Plugin plugin) {
        this(plugin, "");
    }

    public BukkitCommand(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name.toLowerCase();
    }

    public BukkitCommand setPlugin(Plugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public BukkitCommand setName(String name) {
        this.name = name;
        return this;
    }

    public BukkitCommand setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public BukkitCommand setDescription(String description) {
        this.description = description;
        return this;
    }

    public BukkitCommand setAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public BukkitCommand setPermission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public BukkitCommand setPermission(String node) {
        this.permission.setNode(node);
        return this;
    }

    public BukkitCommand setPermission(String node, String message) {
        this.permission.setNode(node);
        this.permission.setMessage(message);
        return this;
    }

    public BukkitCommand setOption(MsgType type, String message) {
        this.options.put(type, message);
        return this;
    }

    public BukkitCommand onExecute(Target<?> target) {
        this.target = target;
        return this;
    }

    public BukkitCommand onArgument(String name, Target<?> target) {
        this.commands.put(name, target);
        return this;
    }

    public void register() {
        CommandUtil.register(this);
    }

    public static BukkitCommand get(Plugin plugin, String name) {
        return new BukkitCommand(plugin, name);
    }
}