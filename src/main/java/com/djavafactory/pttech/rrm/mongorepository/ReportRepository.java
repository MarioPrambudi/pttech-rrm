package com.djavafactory.pttech.rrm.mongorepository;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.repository.MongoRepository;

import com.djavafactory.pttech.rrm.domain.Report;


/**
 * 
 * June 4, 2011 2:15:45 PM
 * @author Firman Agustian
 *
 */
public interface ReportRepository extends MongoRepository<Report, String>, ReportCustomRepository {

}
