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
import com.djavafactory.pttech.rrm.domain.AuditTrail;

/**
 * 
 * @author Mario Tinton Prambudi
 * May 9, 2011 2:41:13 PM
 *
 */
public class AuditTrailRepositoryImpl implements AuditTrailRepository {

	@Autowired
	private MongoOperations mongoOps;

	@Autowired
	MongoRepository<AuditTrail, ObjectId> auditTrailRepository;

	private static final String fieldPerformedAt = "performedAt";
	private static final String fieldEntity = "entity";
	private static final String fieldAction = "action";

	@Override
	public List<AuditTrail> findByParam(Date dateFrom, Date dateTo, String entity, String action, Integer page, Integer size) {
		Criteria criteria = where(fieldAction);
		if (entity != null && !entity.isEmpty()) {
			criteria = where(fieldEntity).regex("(?i)" + entity);
			if (action != null && !action.isEmpty() && !action.equalsIgnoreCase("-1"))
				criteria = criteria.and(fieldAction);
		}
		if (action != null && !action.isEmpty() && !action.equalsIgnoreCase("-1"))
			criteria = criteria.is(action);
		if (dateTo != null || dateFrom != null) {
			criteria = criteria.and(fieldPerformedAt);
			if ((entity == null || entity.isEmpty()) && (action == null || action.isEmpty() || action.equalsIgnoreCase("-1")))
				criteria = where(fieldPerformedAt);
		}
		if (dateTo != null)
			criteria = criteria.lte(dateTo);
		if (dateFrom != null)
			criteria = criteria.gte(dateFrom);
		Query querying = query(criteria);
		if (dateFrom == null && dateTo == null && (entity == null || entity.isEmpty())
				&& (action == null || action.isEmpty() || action.equalsIgnoreCase("-1")))
			querying = new Query();
		if (page != null && page > 0)
			querying = querying.skip((page - 1) * (size == null || size < 0 ? 0 : size));
		if (size != null && size > 0)
			querying = querying.limit(size);
		return mongoOps.find(querying, AuditTrail.class);
	}

	@Override
	public Long countByParam(Date dateFrom, Date dateTo, String entity, String action) {
		Long totalCount = 0L;
		Integer tempCount = 0;
		int pageNo = 1;
		do {
			List<AuditTrail> auditTrails = findByParam(dateFrom, dateTo, entity, action, pageNo, Integer.MAX_VALUE);
			tempCount = auditTrails == null ? null : auditTrails.size();
			totalCount += tempCount == null ? 0L : tempCount.longValue();
			pageNo++;
		} while (tempCount != null && tempCount >= Integer.MAX_VALUE);
		return totalCount;
	}

	@Override
	public List<AuditTrail> findAll() {
		return auditTrailRepository.findAll();
	}

	@Override
	public List<AuditTrail> findAll(Sort sort) {
		return auditTrailRepository.findAll(sort);
	}

	@Override
	public Page<AuditTrail> findAll(Pageable pageable) {
		return auditTrailRepository.findAll(pageable);
	}

	@Override
	public AuditTrail save(AuditTrail entity) {
		return auditTrailRepository.save(entity);
	}

	@Override
	public Iterable<AuditTrail> save(Iterable<? extends AuditTrail> entities) {
		return auditTrailRepository.save(entities);
	}

	@Override
	public AuditTrail findOne(ObjectId id) {
		return auditTrailRepository.findOne(id);
	}

	@Override
	public boolean exists(ObjectId id) {
		return auditTrailRepository.exists(id);
	}

	@Override
	public Long count() {
		return auditTrailRepository.count();
	}

	@Override
	public void delete(AuditTrail entity) {
		auditTrailRepository.delete(entity);
	}

	@Override
	public void delete(Iterable<? extends AuditTrail> entities) {
		auditTrailRepository.delete(entities);
	}

	@Override
	public void deleteAll() {
		auditTrailRepository.deleteAll();
	}

}
