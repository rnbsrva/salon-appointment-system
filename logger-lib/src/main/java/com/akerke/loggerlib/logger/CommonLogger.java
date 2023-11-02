package com.akerke.loggerlib.logger;

import com.akerke.loggerlib.model.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommonLogger {

    private final BiConsumer<String, String> addProperty = MDC::put;

    private void addLogProperties(Log log) {
        var objClass = Log.class;

        var fields = objClass.getDeclaredFields();

        for (var field : fields) {
            field.setAccessible(true);

            var fieldName = field.getName();
            Object fieldValue = null;
            try {
                fieldValue = field.get(log);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            addProperty.accept(fieldName, fieldValue.toString());

            field.setAccessible(false);
        }
    }

    public void info(String message, Log logEntity) {
        addLogProperties(logEntity);
        log.info(message);
        MDC.clear();
    }
}
