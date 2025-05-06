package dev.lone64.LoneLib.common.util.string;

public class StringUtil {
    public static String fromArgs(String[] args, int start) {
        StringBuilder w = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            w.append(args[i]).append(" ");
        }
        w = new StringBuilder(w.substring(0, w.length() - 1));
        return w.toString();
    }
}