package dev.lone64.LoneLib.common.command.handler;

import dev.lone64.LoneLib.common.command.data.Arguments;

@FunctionalInterface
public interface TargetHandler<T> {
    boolean onTarget(T sender, Arguments args);
}