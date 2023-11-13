package com.akerke.authservice.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import static com.akerke.authservice.common.validate.BindingValidator.validateRequest;


@Aspect
@Component
@Slf4j
public class ValidatedMethodAspect {

    @Before(value = "@annotation(com.akerke.authservice.common.annotations.ValidatedMethod) && args(.., br)")
    public void validateMethod(BindingResult br) {
        log.info("validating request in interceptor");
        validateRequest(br);
    }


}
