package com.akerke.authservice.reflection;

import com.akerke.authservice.utils.Pair;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for working with maps and claims.
 */
@Slf4j
@UtilityClass
public class MapUtils {

    /**
     * Converts an object's fields and optional key-value pairs into a map of claims.
     *
     * @param obj    The object whose fields are to be converted to claims.
     * @param vararg Optional key-value pairs (as Pair objects) to include in the claims.
     * @return A map containing the claims extracted from the object's fields and key-value pairs.
     * @throws IllegalAccessException If there is an issue accessing the fields of the object via reflection.
     */
    public static Map<String, Object> toMap(Object obj, Pair... vararg)
            throws IllegalAccessException {
        var map = new HashMap<String, Object>();
        var objClass = obj.getClass();

        var fields = objClass.getDeclaredFields();
        for (var field : fields) {
            try {
                field.setAccessible(true);

                var fieldName = field.getName();
                var fieldValue = field.get(obj);

                if (isPrimitive(fieldValue.getClass())) {
                    map.put(fieldName, fieldValue);
                } else {
                    map.put(fieldName, toMap(fieldValue));
                }

                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                log.error("error: {}", e.getMessage());
                throw e;
            }
        }

        Arrays.stream(vararg)
                .forEach(pair -> map.put(pair.key(), pair.value()));

        return map;
    }

    private static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
                || type == Boolean.class || type == Character.class
                || type == Date.class || Enum.class.isAssignableFrom(type)
                || type == LocalDateTime.class || type == LocalTime.class
                || type.isArray() && isPrimitive(type.getComponentType());
    }
}
