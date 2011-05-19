package com.djavafactory.pttech.rrm.mongorepository;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.repository.MongoRepository;

import com.djavafactory.pttech.rrm.domain.EventTrail;

/**
 * 
 * User: rainpoh
 * Date: 18/5/11
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EventTrailRepository extends MongoRepository<EventTrail, ObjectId>, EventTrailCustomRepository{

}
