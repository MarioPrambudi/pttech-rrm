package com.djavafactory.pttech.rrm.mongorepository;

import java.util.Date;
import java.util.List;

import com.djavafactory.pttech.rrm.domain.EventTrail;

/**
 * 
 * User: rainpoh
 * Date: 18/5/11
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EventTrailCustomRepository {
	
	public List<EventTrail> findByParam(Date dateFrom, Date dateTo, String source, String code, String message, Integer page, Integer size);
	
	public Long countByParam(Date dateFrom, Date dateTo, String source, String codeOrMessage, String message);

}
