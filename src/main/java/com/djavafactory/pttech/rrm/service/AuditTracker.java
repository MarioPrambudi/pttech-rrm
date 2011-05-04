package com.djavafactory.pttech.rrm.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuditTracker {

    @AfterReturning("persistMethod() || mergeMethod() || removeMethod()")
    public void trackDatabaseAction(JoinPoint jp) {
        //TODO log audit info
        //1. entity -> jp.getTarget().getClass().getSimpleName()
        //2. action -> jp.getSignature().getName().toUpperCase().charAt(0)
        //3. performedOn -> new Date()
        //4. performedBy -> get userid (phase 2)
        //5. description -> jp.getTarget()
    }

    @Pointcut("execution(* com.djavafactory.pttech.rrm.domain.*.persist*(..))")
    public void persistMethod() {
    }

    @Pointcut("execution(* com.djavafactory.pttech.rrm.domain.*.merge*(..))")
    public void mergeMethod() {
    }

    @Pointcut("execution(* com.djavafactory.pttech.rrm.domain.*.remove*(..))")
    public void removeMethod() {
    }
}
