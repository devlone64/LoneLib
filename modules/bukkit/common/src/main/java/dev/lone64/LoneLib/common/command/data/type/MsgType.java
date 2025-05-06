package dev.lone64.LoneLib.common.command.data.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgType {
    COMMAND_NOT_FOUND(0, "&c명령어를 찾을 수 없습니다.");

    private final int id;
    private final String message;
}