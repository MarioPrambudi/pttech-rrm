package com.djavafactory.pttech.rrm.mongorepository;

import static org.springframework.data.document.mongodb.query.Criteria.where;
import static org.springframework.data.document.mongodb.query.Query.query;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.djavafactory.pttech.rrm.domain.Report;
	
/**
 * 
 * June 4, 2011 2:15:33 PM
 * @author Firman Agustian
 *
 */
public class ReportRepositoryImpl implements ReportRepository {

	private static final String fieldSofRequestedDate = "sofRequestedDate";
	private static final String fieldStatus = "status";
	
	@Autowired
	private MongoOperations mongoOps;
	
	@Autowired
	MongoRepository<Report, String> reportRepository;
	
	@Override
	public List<Report> findByParam(Date dateFrom, Date dateTo, String status, Integer page, Integer size) {
		Criteria criteria = where(fieldStatus);
		
		if(dateFrom != null && dateTo != null) {
			criteria = criteria.and(fieldSofRequestedDate);
			criteria = criteria.lte(dateTo);
			criteria = criteria.gte(dateFrom);
		}
		
		Query querying = query(criteria);
		if(dateFrom == null && dateTo == null) {
			querying = new Query();
		}
		
		if(page != null && page > 0) {
			querying = querying.skip((page - 1) * (size == null || size < 0 ? 0 : size));
		}
		if(size != null && size > 0) {
			querying = querying.limit(size);
		}
		
		return mongoOps.find(querying, Report.class);
	}
	
	@Override
	public Long countByParam(Date dateFrom, Date dateTo, String status) {
		Long totalCount = 0L;
		Integer tempCount = 0;
		int pageNo = 1;
		do {
			List<Report> reports = findByParam(dateFrom, dateTo, status, pageNo, Integer.MAX_VALUE);
			tempCount = reports == null ? null : reports.size();
			totalCount += tempCount == null ? 0L : tempCount.longValue();
			pageNo++;
		} while (tempCount != null && tempCount >= Integer.MAX_VALUE);
		
		return totalCount;
	}
	
	@Override
	public List<Report> findAll() {
		return reportRepository.findAll();
	}

	@Override
	public List<Report> findAll(Sort sort) {
		return reportRepository.findAll(sort);
	}

	@Override
	public Page<Report> findAll(Pageable pageable) {
		return reportRepository.findAll(pageable);
	}

	@Override
	public Report save(Report entity) {
		return reportRepository.save(entity);
	}

	@Override
	public Iterable<Report> save(Iterable<? extends Report> entities) {
		return reportRepository.save(entities);
	}

	@Override
	public Report findOne(String reportId) {
		return reportRepository.findOne(reportId);
	}

	@Override
	public boolean exists(String reportId) {
		return reportRepository.exists(reportId);
	}

	@Override
	public Long count() {
		return reportRepository.count();
	}

	@Override
	public void delete(Report entity) {
		reportRepository.delete(entity);
	}

	@Override
	public void delete(Iterable<? extends Report> entities) {
		reportRepository.delete(entities);
	}

	@Override
	public void deleteAll() {
		reportRepository.deleteAll();
	}
}
