package dev.lone64.LoneLib.common.command.handler;

import dev.lone64.LoneLib.common.command.data.Arguments;

import java.util.List;

@FunctionalInterface
public interface TabHandler<T> {
    List<String> onTab(T sender, Arguments args);
}