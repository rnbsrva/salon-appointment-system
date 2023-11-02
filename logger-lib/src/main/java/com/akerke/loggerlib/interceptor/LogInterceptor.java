package com.akerke.loggerlib.interceptor;

import com.akerke.loggerlib.logger.CommonLogger;
import com.akerke.loggerlib.model.Log;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

    private final CommonLogger commonLogger;

    @Value("${spring.application.name:logger}")
    private String application;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        var startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
                    var log = Log.builder()
                    .app(application)
                    .url(request.getRequestURL().toString())
                    .httpMethod(request.getMethod())
                    .execution(endTime - startTime)
                    .httpStatus(String.valueOf(response.getStatus()))
                    .build();

        commonLogger.info("Log %s".formatted(log),log);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
