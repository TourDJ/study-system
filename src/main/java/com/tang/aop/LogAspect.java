package com.tang.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.tang.service..*.bookFlight(..))")
    private void logPointCut() {
    }

    @AfterReturning(pointcut = "logPointCut()", returning = "retVala")
    public void logBookingStatus(boolean retVala) { 
        if (retVala) {
            System.out.println("booking flight succeeded!");
        } else {
            System.out.println("booking flight failed!");
        }
    }
}
