package com.djavafactory.pttech.rrm.mongorepository;

import static org.springframework.data.document.mongodb.query.Criteria.where;
import static org.springframework.data.document.mongodb.query.Query.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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

import com.djavafactory.pttech.rrm.domain.AuditTrail;
import com.djavafactory.pttech.rrm.domain.EventTrail;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;

public class EventTrailRepositoryImpl implements EventTrailRepository {

	private static final String fieldEventTrailDate = "date";
	private static final String fieldEventTrailSource = "source";
	private static final String fieldEventTrailCode = "code";
	private static final String fieldEventTrailMessage = "message";
	private static final String fieldEventTrailId = "_id";
	
	
	@Autowired
	private MongoOperations mongoOps;

	@Autowired
	MongoRepository<EventTrail, ObjectId> eventTrailRepository;
	
	@Override
	public List<EventTrail> findByParam(Date dateFrom, Date dateTo, String source, String code, String message, Integer page, Integer size) {
		Criteria criteria = where(fieldEventTrailId).exists(true);		
		if (source == null || source.isEmpty() || !source.equalsIgnoreCase("-1")) 
		{
			criteria = criteria.and(fieldEventTrailSource).is(source);
		}	
		if (code != null && !code.isEmpty()) 
		{
			criteria = criteria.and(fieldEventTrailCode).regex("(?i)" + code);
		}
		if (message != null && !message.isEmpty()) 
		{
			criteria = criteria.and(fieldEventTrailMessage).regex("(?i)" + message);
		}
		if (dateFrom != null && dateTo != null) 
		{
			criteria = criteria.and(fieldEventTrailDate);
			criteria = criteria.lte(dateTo);
			criteria = criteria.gte(dateFrom);	
		}
		Query querying = query(criteria);
		if (dateFrom == null && dateTo == null 
			&& (source == null || source.isEmpty() || source.equalsIgnoreCase("-1"))
			&& (code == null || code.isEmpty())
			&& (message == null || message.isEmpty()))
		{
			querying = new Query();
		}
		if (page != null && page > 0)
		{
			querying= querying.skip((page - 1) * (size == null || size < 0 ? 0 : size));
		}			
		if (size != null && size > 0)
		{	
			querying = querying.limit(size);
		}		
		return mongoOps.find(querying, EventTrail.class);
	}
	
	@Override
	public Long countByParam(Date dateFrom, Date dateTo, String source, String code, String message) {
		Long totalCount = 0L;
		Integer tempCount = 0;
		int pageNo = 1;
		do {
			List<EventTrail> eventTrails = findByParam(dateFrom, dateTo, source, code, message, pageNo, Integer.MAX_VALUE);
			tempCount = eventTrails == null ? null : eventTrails.size();
			totalCount += tempCount == null ? 0L : tempCount.longValue();
			pageNo++;
		} while (tempCount != null && tempCount >= Integer.MAX_VALUE);
		return totalCount;
	}

	@Override
	public List<EventTrail> findAll() {
		return eventTrailRepository.findAll();
	}

	@Override
	public List<EventTrail> findAll(Sort sort) {
		return eventTrailRepository.findAll(sort);
	}

	@Override
	public Page<EventTrail> findAll(Pageable pageable) {
		return eventTrailRepository.findAll(pageable);
	}

	@Override
	public EventTrail save(EventTrail entity) {
		return eventTrailRepository.save(entity);
	}

	@Override
	public Iterable<EventTrail> save(Iterable<? extends EventTrail> entities) {
		return eventTrailRepository.save(entities);
	}

	@Override
	public EventTrail findOne(ObjectId id) {
		return eventTrailRepository.findOne(id);
	}

	@Override
	public boolean exists(ObjectId id) {
		return eventTrailRepository.exists(id);
	}

	@Override
	public Long count() {
		return eventTrailRepository.count();
	}

	@Override
	public void delete(EventTrail entity) {
		eventTrailRepository.delete(entity);
	}

	@Override
	public void delete(Iterable<? extends EventTrail> entities) {
		eventTrailRepository.delete(entities);
	}

	@Override
	public void deleteAll() {
		eventTrailRepository.deleteAll();
	}
}
