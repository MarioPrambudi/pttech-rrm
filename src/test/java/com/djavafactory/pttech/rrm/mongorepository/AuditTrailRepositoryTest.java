package com.djavafactory.pttech.rrm.mongorepository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.djavafactory.pttech.rrm.domain.AuditTrail;

/**
 * 
 * @author Mario Tinton Prambudi
 * May 9, 2011 5:26:21 PM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class AuditTrailRepositoryTest {

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Test
	@Ignore
	public void testFindByParam() {
		List<AuditTrail> auditTrailList = auditTrailRepository.findByParam(null, null, "aCquirer", null);
		assertNotNull(auditTrailList);
		assertFalse(auditTrailList.isEmpty());
	}

}
