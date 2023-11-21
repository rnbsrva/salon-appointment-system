package com.akerke.authserver.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private record LoggingDetails(
            String url,
            long execution,
            String method,
            int responseStatus,
            String agent
    ){
        @Override
        public String toString() {
            return "url='" + url + '\'' +
                    ", execution=" + execution +
                    "ms, method='" + method + '\'' +
                    ", responseStatus=" + responseStatus +
                    ", agent='" + agent;
        }

        public void putContext(){
            MDC.put("url",url);
            MDC.put("execution", String.valueOf(execution));
            MDC.put("method",method);
            MDC.put("responseStatus", String.valueOf(responseStatus));
            MDC.put("agent",agent);
        }
    }

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        var startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        var loggingDetails = new LoggingDetails(
                request.getRequestURL().toString(),
                executionTime,
                request.getMethod(),
                response.getStatus(),
                response.getHeader("User-Agent")
        );

        loggingDetails.putContext();

        log.info("log: {}",loggingDetails);
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            Exception exception
    ) throws Exception {
        MDC.clear();
    }

}