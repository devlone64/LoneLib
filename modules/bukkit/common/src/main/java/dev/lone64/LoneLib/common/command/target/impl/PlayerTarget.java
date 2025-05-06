package dev.lone64.LoneLib.common.command.target.impl;

import dev.lone64.LoneLib.common.command.data.Arguments;
import dev.lone64.LoneLib.common.command.handler.TabHandler;
import dev.lone64.LoneLib.common.command.handler.TargetHandler;
import dev.lone64.LoneLib.common.command.target.Target;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerTarget implements Target<Player> {
    @Override
    public Target<Player> with(TargetHandler<Player> targetHandler) {
        return new PlayerTarget() {
            @Override
            public boolean onCommand(Player sender, String[] args) {
                return targetHandler.onTarget(sender, new Arguments(args));
            }
        };
    }

    @Override
    public Target<Player> with(TargetHandler<Player> targetHandler, TabHandler<Player> tabHandler) {
        return new PlayerTarget() {
            @Override
            public boolean onCommand(Player sender, String[] args) {
                return targetHandler.onTarget(sender, new Arguments(args));
            }

            @Override
            public List<String> onTab(Player sender, String[] args) {
                return tabHandler.onTab(sender, new Arguments(args));
            }
        };
    }
}