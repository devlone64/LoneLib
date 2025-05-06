package dev.lone64.LoneLib.common.command.target.impl;

import dev.lone64.LoneLib.common.command.data.Arguments;
import dev.lone64.LoneLib.common.command.handler.TabHandler;
import dev.lone64.LoneLib.common.command.handler.TargetHandler;
import dev.lone64.LoneLib.common.command.target.Target;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ConsoleTarget implements Target<CommandSender> {
    @Override
    public Target<CommandSender> with(TargetHandler<CommandSender> targetHandler) {
        return new ConsoleTarget() {
            @Override
            public boolean onCommand(CommandSender sender, String[] args) {
                return targetHandler.onTarget(sender, new Arguments(args));
            }
        };
    }

    @Override
    public Target<CommandSender> with(TargetHandler<CommandSender> targetHandler, TabHandler<CommandSender> tabHandler) {
        return new ConsoleTarget() {
            @Override
            public boolean onCommand(CommandSender sender, String[] args) {
                return targetHandler.onTarget(sender, new Arguments(args));
            }

            @Override
            public List<String> onTab(CommandSender sender, String[] args) {
                return tabHandler.onTab(sender, new Arguments(args));
            }
        };
    }
}