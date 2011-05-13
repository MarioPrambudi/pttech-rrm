package com.djavafactory.pttech.rrm.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;

@Aspect
@Configurable
public class EventTracker {

    @AfterThrowing(value = "execution(* com.djavafactory.pttech.rrm.*(..))", throwing = "exception")
    public void trackEventAction(JoinPoint jp, Throwable exception) {
        // log event info
    }
}
