package com.djavafactory.pttech.rrm.service;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.document.mongodb.MongoOperations;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.EventTrail;
import com.djavafactory.pttech.rrm.exception.RrmException;
import com.djavafactory.pttech.rrm.exception.RrmStatusCode;

@Aspect
@Configurable
public class EventTracker {

	@Autowired
	private MongoOperations mongoOps;

	private EventTrail lastEventTrail;

	@AfterThrowing(value = "execution(* com.djavafactory.pttech.rrm..*.*(..))", throwing = "exception")
	public void trackEventAction(JoinPoint jp, Throwable exception) {
		EventTrail eventTrail = new EventTrail();
		// Datetime occurred
		eventTrail.setDate(new Date());
		String eventCode = RrmStatusCode.STS_GENERALRRMERROR.getCode();
		String eventMessage = exception.toString();
		if (exception != null && exception instanceof RrmException) {
			RrmException rrmException = (RrmException) exception;
			eventCode = rrmException.getErrorCode();
			eventMessage = rrmException.getMessage();
		}
		// Set code
		eventTrail.setCode(eventCode);
		// Set message
		eventTrail.setMessage(eventMessage);
		// TODO: set source here
		eventTrail.setSource(Constants.EVENT_TRAIL_SOURCE_RRM);
		// TODO: set person in charge here
		eventTrail.setUser("Rain");
		// Save stack trace
		eventTrail.setStackTrace(ExceptionUtils.getStackTrace(exception));
		if (lastEventTrail == null || !lastEventTrail.equals(eventTrail)) {
			mongoOps.save(eventTrail, Constants.EVENT_TRAIL_MONGODB_COLLECTION_NAME);
			lastEventTrail = eventTrail;
		}
	}

}
