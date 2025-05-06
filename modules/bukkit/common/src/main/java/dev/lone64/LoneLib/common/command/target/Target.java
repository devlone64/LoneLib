package dev.lone64.LoneLib.common.command.target;

import dev.lone64.LoneLib.common.command.handler.TabHandler;
import dev.lone64.LoneLib.common.command.handler.TargetHandler;
import dev.lone64.LoneLib.common.command.target.impl.ConsoleTarget;
import dev.lone64.LoneLib.common.command.target.impl.PlayerTarget;

import java.util.List;

public interface Target<T> {
    Target<T> with(TargetHandler<T> targetHandler);
    Target<T> with(TargetHandler<T> targetHandler, TabHandler<T> tabHandler);

    default boolean onCommand(T sender, String[] args) {
        return true;
    }

    default List<String> onTab(T sender, String[] args) {
        return List.of();
    }

    static ConsoleTarget createSender() {
        return new ConsoleTarget();
    }

    static PlayerTarget createPlayer() {
        return new PlayerTarget();
    }
}