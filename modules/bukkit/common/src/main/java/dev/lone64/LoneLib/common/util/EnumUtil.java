package dev.lone64.LoneLib.common.util;

public class EnumUtil {
    public static String to(Object value) {
        return ((Enum<?>) value).name();
    }

    @SuppressWarnings("unchecked")
    public static Enum<?> from(Class<?> enumType, String name) {
        return Enum.valueOf((Class<Enum>) enumType, name);
    }
}