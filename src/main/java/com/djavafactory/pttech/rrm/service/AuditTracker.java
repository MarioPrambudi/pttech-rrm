package com.djavafactory.pttech.rrm.service;

import java.util.Date;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.document.mongodb.MongoOperations;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.AuditTrail;

@Aspect
@Configurable
public class AuditTracker {

	@Autowired
	private MongoOperations mongoOps;

	@AfterReturning("persistMethod() || mergeMethod() || removeMethod()")
	public void trackDatabaseAction(JoinPoint jp) {
		// log audit info
		// 1. entity -> jp.getTarget().getClass().getSimpleName()
		// 2. action -> jp.getSignature().getName().toUpperCase().charAt(0)
		// 3. performedOn -> new Date()
		// 4. performedBy -> get userid (phase 2)
		// 5. description -> jp.getTarget()
		AuditTrail auditTrail = new AuditTrail();
		// Entity class name
		Object joinPoint = jp.getTarget();
		auditTrail.setEntity(joinPoint.getClass().getSimpleName());
		// Action performed
		auditTrail.setAction(jp.getSignature().getName().toUpperCase().substring(0, 1));
		// Datetime performed
		auditTrail.setPerformedAt(new Date());
		// TODO: set user here
		auditTrail.setPerformedBy("Mario");
		// TODO: set description here
		auditTrail.setDescription(joinPoint.toString());
		auditTrail.setDebugInfo(jp.getSourceLocation() == null ? "N/A" : jp.getSourceLocation().toString());
		mongoOps.save(Constants.AUDIT_TRAIL_MONGODB_COLLECTION_NAME, auditTrail);
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
