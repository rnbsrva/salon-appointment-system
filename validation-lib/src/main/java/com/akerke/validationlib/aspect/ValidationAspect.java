package com.akerke.validationlib.aspect;

import jakarta.validation.Valid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ValidationAspect {

    @Pointcut("@annotation(com.akerke.validationlib.annotation.Validate)")
    public void validate() { }

    @Before("validate()")
    public void beforeValidAnnotation(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("    @Before(\"execution(* *(.., @jakarta.validation.Valid (*), ..))\")");

        for (Object arg : args) {
            if (arg != null && arg.getClass().isAnnotationPresent(Valid.class)) {
                System.out.println("Found parameter with @Valid annotation: " + arg);
                // Your logic here
            }
        }
    }

}
