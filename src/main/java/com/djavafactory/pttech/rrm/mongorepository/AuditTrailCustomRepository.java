package com.djavafactory.pttech.rrm.mongorepository;

import java.util.Date;
import java.util.List;

import com.djavafactory.pttech.rrm.domain.AuditTrail;

/**
 * 
 * @author Mario Tinton Prambudi
 * May 9, 2011 3:28:36 PM
 *
 */
public interface AuditTrailCustomRepository {

	public List<AuditTrail> findByParam(Date dateFrom, Date dateTo, String entity, String action);

}
