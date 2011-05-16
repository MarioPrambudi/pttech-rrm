package com.djavafactory.pttech.rrm.mongorepository;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.repository.MongoRepository;

import com.djavafactory.pttech.rrm.domain.AuditTrail;

/**
 * 
 * May 8, 2011 8:22:58 PM
 * @author Mario Tinton Prambudi
 *
 */
public interface AuditTrailRepository extends MongoRepository<AuditTrail, ObjectId>, AuditTrailCustomRepository {
	
}
