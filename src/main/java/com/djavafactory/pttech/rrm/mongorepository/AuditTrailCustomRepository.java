package com.djavafactory.pttech.rrm.mongorepository;

import java.util.Date;
import java.util.List;

import com.djavafactory.pttech.rrm.domain.AuditTrail;

/**
 * 
 * May 9, 2011 3:28:36 PM
 * @author Mario Tinton Prambudi
 *
 */
public interface AuditTrailCustomRepository {

	public List<AuditTrail> findByParam(Date dateFrom, Date dateTo, String entity, String action, Integer page, Integer size);

	public Long countByParam(Date dateFrom, Date dateTo, String entity, String action);

}
