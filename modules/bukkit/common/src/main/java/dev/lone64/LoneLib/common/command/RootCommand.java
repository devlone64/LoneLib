package dev.lone64.LoneLib.common.command;

import dev.lone64.LoneLib.common.command.data.type.MsgType;
import dev.lone64.LoneLib.common.command.target.impl.ConsoleTarget;
import dev.lone64.LoneLib.common.command.target.impl.PlayerTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

@Getter
@AllArgsConstructor
public class RootCommand {
    private final BukkitCommand command;

    public boolean handleCommand(CommandSender sender, org.bukkit.command.Command command, String arg, String[] args) {
        if (this.command.getTarget() instanceof ConsoleTarget consoleTarget) {
            if (args.length != 0 && !this.command.getCommands().isEmpty()) {
                var target = this.command.getCommands().get(args[0]);
                if (target == null) {
                    var message = this.command.getOptions().get(MsgType.COMMAND_NOT_FOUND);
                    if (message != null) {
                        sender.sendMessage(format(message));
                        return true;
                    }
                    sender.sendMessage(format(MsgType.COMMAND_NOT_FOUND.getMessage()));
                    return true;
                } else if (target instanceof ConsoleTarget consoleTarget1) {
                    return consoleTarget1.onCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                } else if (target instanceof PlayerTarget playerTarget1) {
                    if (sender instanceof Player player) {
                        return playerTarget1.onCommand(player, Arrays.copyOfRange(args, 1, args.length));
                    }
                    sender.sendMessage(format("&c[%s] This command is not available on the console.".formatted(this.command.getPlugin().getName())));
                    return true;
                }
                sender.sendMessage(format("&c[%s] An error occurred while executing the command.".formatted(this.command.getPlugin().getName())));
                return true;
            }
            return consoleTarget.onCommand(sender, args);
        } else if (this.command.getTarget() instanceof PlayerTarget playerTarget) {
            if (sender instanceof Player player) {
                if (args.length != 0 && !this.command.getCommands().isEmpty()) {
                    var target = this.command.getCommands().get(args[0]);
                    if (target == null) {
                        var message = this.command.getOptions().get(MsgType.COMMAND_NOT_FOUND);
                        if (message != null) {
                            sender.sendMessage(format(message));
                            return true;
                        }
                        sender.sendMessage(format(MsgType.COMMAND_NOT_FOUND.getMessage()));
                        return true;
                    } else if (target instanceof ConsoleTarget consoleTarget1) {
                        return consoleTarget1.onCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                    } else if (target instanceof PlayerTarget playerTarget1) {
                        return playerTarget1.onCommand(player, Arrays.copyOfRange(args, 1, args.length));
                    }
                    sender.sendMessage(format("&c[%s] An error occurred while executing the command.".formatted(this.command.getPlugin().getName())));
                    return true;
                }
                return playerTarget.onCommand(player, args);
            }
            sender.sendMessage(format("&c[%s] This command is not available on the console.".formatted(this.command.getPlugin().getName())));
            return true;
        }
        sender.sendMessage(format("&c[%s] An error occurred while executing the command.".formatted(this.command.getPlugin().getName())));
        return true;
    }
    public List<String> handleTab(CommandSender sender, org.bukkit.command.Command command, String arg, String[] args) {
        if (this.command.getTarget() instanceof ConsoleTarget consoleTarget) {
            if (args.length == 1 && !this.command.getCommands().isEmpty()) {
                return this.command.getCommands().keySet().stream().toList();
            } else if (args.length != 0 && !this.command.getCommands().isEmpty()) {
                var target = this.command.getCommands().get(args[0]);
                if (target instanceof ConsoleTarget consoleTarget1) {
                    return consoleTarget1.onTab(sender, Arrays.copyOfRange(args, 1, args.length));
                } else if (target instanceof PlayerTarget playerTarget1 && sender instanceof Player player) {
                    return playerTarget1.onTab(player, Arrays.copyOfRange(args, 1, args.length));
                }
                return List.of();
            }
            return consoleTarget.onTab(sender, args);
        } else if (this.command.getTarget() instanceof PlayerTarget playerTarget && sender instanceof Player player) {
            if (args.length == 1 && !this.command.getCommands().isEmpty()) {
                return this.command.getCommands().keySet().stream().toList();
            } else if (args.length != 0 && !this.command.getCommands().isEmpty()) {
                var target = this.command.getCommands().get(args[0]);
                if (target instanceof ConsoleTarget consoleTarget1) {
                    return consoleTarget1.onTab(sender, Arrays.copyOfRange(args, 1, args.length));
                } else if (target instanceof PlayerTarget playerTarget1) {
                    return playerTarget1.onTab(player, Arrays.copyOfRange(args, 1, args.length));
                }
                return List.of();
            }
            return playerTarget.onTab(player, args);
        }
        return List.of();
    }
}