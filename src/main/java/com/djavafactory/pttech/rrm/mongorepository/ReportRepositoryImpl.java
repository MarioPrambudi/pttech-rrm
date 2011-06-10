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

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.EventTrail;
import com.djavafactory.pttech.rrm.domain.Report;
	
/**
 * 
 * June 4, 2011 2:15:33 PM
 * @author Firman Agustian
 *
 */
public class ReportRepositoryImpl implements ReportRepository {

	private static final String FIELDREQUESTEDTIME= "requestedTime";
	private static final String FIELDMODIFIEDTIME = "modifiedDate";
	private static final String FIELDSTATUS = "status";
	private static final String FIELDID= "_id";

	@Autowired
	private MongoOperations mongoOps;
	
	@Autowired
	MongoRepository<Report, ObjectId> reportRepository;
	
	@Override
	public List<Report> findByParam(Date dateFrom, Date dateTo, String[] listStatus, String dateField) {
		Criteria criteria = where(FIELDSTATUS).in(listStatus);	
		criteria = criteria.and(dateField);
		criteria = criteria.lte(dateTo);
		criteria = criteria.gte(dateFrom);
		Query querying = query(criteria);
		return mongoOps.find(querying, Report.class);
	}
	
	@Override
	public List<Report> findByParam(Date dateFrom, Date dateTo) {
		Criteria criteria = where(FIELDREQUESTEDTIME);
		criteria = criteria.lte(dateTo);
		criteria = criteria.gte(dateFrom);
		Query querying = query(criteria);
		return mongoOps.find(querying, Report.class);
	}
	
	@Override
	public Long countByParam(Date dateFrom, Date dateTo){
		int size = 0;
		List<Report> listReport = findByParam(dateFrom, dateTo);
		if (listReport != null && listReport.size()>0) size = listReport.size();
		return (long)size;
	}
	
	@Override
	public void delete(Date dateFrom, Date dateTo){
		System.out.println(">>>>>>>>>>> DELETE");
		Criteria criteria = where(FIELDREQUESTEDTIME);
		criteria = criteria.lte(dateTo);
		criteria = criteria.gte(dateFrom);
		Query querying = query(criteria);
		mongoOps.findAndRemove(querying, Report.class);
	}
	
	
	@Override
	public void saveReport(List<Report> listReport) {
		for(Report report : listReport)
		{
			mongoOps.save(Constants.REPORT_MONGODB_COLLECTION_NAME, report);	
		}		
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
	public Report findOne(ObjectId id) {
		return reportRepository.findOne(id);
	}

	@Override
	public boolean exists(ObjectId id) {
		return reportRepository.exists(id);
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
