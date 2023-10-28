package com.akerke.authservice.aspect;

import com.akerke.authservice.common.aspect.LoggingAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

    @Mock
    private ProceedingJoinPoint pjp;

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Test
    public void logMethodExecutionTime_whenMethodExecutionIsSuccessful_thenLogsExecutionTime() throws Throwable {
        var methodSignature = mock(MethodSignature.class);

        when(pjp.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringType()).thenReturn(LoggingAspectTest.class);
        when(methodSignature.getName()).thenReturn("testMethod");
        when(pjp.proceed()).thenReturn(null);

        loggingAspect.logMethodExecutionTime(pjp);

        verify(pjp, times(1)).proceed();
    }

    @Test
    public void logMethodExecutionTime_whenExceptionIsThrown_thenPropagatesException() throws Throwable {
        when(pjp.proceed()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> loggingAspect.logMethodExecutionTime(pjp));
    }
}