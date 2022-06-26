package utils;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Utils {
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T castToClassOrGetString(Class<T> clz, String val) {
        try {
            Method method = clz.getDeclaredMethod("valueOf", String.class);
            return (T) method.invoke(null, val);
        } catch (InvocationTargetException | NoSuchMethodException ignored) {
            return (T) val;
        }
    }
}
