package com.akerke.authservice.common.reflection;

import com.akerke.authservice.utils.Pair;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.UtilityClass;
import org.springframework.util.ReflectionUtils;

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
     */
    public static Map<String, Object> toMap(Object obj, Pair... vararg) {
        var map = new HashMap<String, Object>();
        var objClass = obj.getClass();

        var fields = objClass.getDeclaredFields();

        for (var field : fields) {
            try {
                field.setAccessible(true);

                var fieldName = field.getName();
                var fieldValue = field.get(obj);

                map.put(fieldName, fieldValue);

                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                log.error("error: {}", e.getMessage());
            }
        }

        Arrays.stream(vararg)
                .forEach(pair -> map.put(pair.key(), pair.value()));

        return map;
    }


    /**
     * Converts an object's fields to a map with string values.
     *
     * @param obj The object to convert.
     * @param <T> The type of the object.
     * @return A map where keys are field names, and values are string representations of field values.
     */
    public static <T> Map<String, String> classToMapWithValueString(T obj)  {
        Map<String, String> map = new HashMap<>();
        Class<?> objClass = obj.getClass();

        try {
            for (var field : objClass.getDeclaredFields()) {
                field.setAccessible(true);
                var value = field.get(obj);

                if (value != null) {
                    if (simpleType(value)) {
                        map.put(field.getName(), value.toString());
                    } else {
                        log.error("Value cannot be a data type!");
                    }
                } else {
                    map.put(field.getName(), null);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("error while classToMapWithValueString {}", e.getClass());
        }

        return map;
    }

    private static boolean simpleType(Object value) {
        return value.getClass().isEnum() || value.getClass().isPrimitive() || value instanceof Number || value instanceof String;
    }

}
