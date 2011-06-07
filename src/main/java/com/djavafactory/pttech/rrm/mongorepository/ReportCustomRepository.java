package com.djavafactory.pttech.rrm.mongorepository;

import java.util.Date;
import java.util.List;

import com.djavafactory.pttech.rrm.domain.Report;

/**
 * 
 * June 4, 2011 2:15:33 PM
 * @author Firman Agustian
 *
 */
public interface ReportCustomRepository {

	public List<Report> findByParam(Date dateFrom, Date dateTo, String status, Integer page, Integer size);
	public Long countByParam(Date dateFrom, Date dateTo, String status);
}
