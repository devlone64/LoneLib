package dev.lone64.LoneLib.common.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Permission {
    private String node;
    private String message;
}