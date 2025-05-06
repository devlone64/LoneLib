package dev.lone64.LoneLib.common.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Arguments {
    private final String[] args;

    public String getString(int index) {
        return args[index];
    }

    public Number getNumber(int index) {
        return Integer.parseInt(this.args[index]);
    }

    public boolean getBoolean(int index) {
        return Boolean.parseBoolean(this.args[index]);
    }

    public int size() {
        return args.length;
    }

    public boolean isEmpty() {
        return args.length == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean equals(int index, String text) {
        return equals(index, text, true);
    }

    public boolean equals(int index, String text, boolean equalsIgnored) {
        return equalsIgnored ? args[index].equalsIgnoreCase(text) : args[index].equals(text);
    }
}