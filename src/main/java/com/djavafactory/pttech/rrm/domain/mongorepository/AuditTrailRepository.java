package com.djavafactory.pttech.rrm.domain.mongorepository;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.repository.MongoRepository;

import com.djavafactory.pttech.rrm.domain.AuditTrail;

/**
 * 
 * @author Mario Tinton Prambudi
 * May 8, 2011 8:22:58 PM
 *
 */
public interface AuditTrailRepository extends MongoRepository<AuditTrail, ObjectId> {

}
