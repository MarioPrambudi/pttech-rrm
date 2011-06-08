package com.djavafactory.pttech.rrm.mongorepository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.Report;
import com.djavafactory.pttech.rrm.reports.ReportGenerator;

/**
 * 
 * June 6, 2011 11:26:21 AM
 * @author Firman Agustian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class ReportRepositoryTest {

	@Autowired
	ReportRepository reportRepository;
	
	@Test
	public void testCount() {
		Report report = new Report();
		report.setTransId("1122");
		report.setStatus("S");
		
		reportRepository.save(report);
		
		Report report2 = new Report();
		report2.setTransId("1122");
		report2.setStatus("S");
		
		reportRepository.save(report2);
		System.out.println("<<<<<<<< after save");
		
        List<Report> rResultFind = new ArrayList<Report>();
        rResultFind = reportRepository.findByParam(null, null, "S", 0, 0);
        assertNotNull(rResultFind);
        assertEquals(2, rResultFind.size());
        System.out.println("<<<<<<<<<<< list.size() = " + rResultFind.size());
        
        Long countResult = reportRepository.count();
        System.out.println("<<<<<<<<<<< countResult = " + countResult);
        
        
        reportRepository.deleteAll();
        rResultFind = reportRepository.findByParam(null, null, "S", 0, 0);
        System.out.println("<<<<<<<<<<< list.size() = " + rResultFind.size());
        assertEquals(0, rResultFind.size());
	}
	
	@Test
	public void testSave() {
		reportRepository.deleteAll();
		
		Report report = new Report();
		report.setTransId("1122");
		report.setStatus("S");
		
		reportRepository.save(report);
		System.out.println("<<<<<<<< after save");
		
        List<Report> rResultFind = new ArrayList<Report>();
        rResultFind = reportRepository.findByParam(null, null, "S", 0, 0);
        assertNotNull(rResultFind);
        assertEquals(1, rResultFind.size());
        System.out.println("<<<<<<<<<<< list.size() = " + rResultFind.size());
        
        
        
        reportRepository.delete(report);
        rResultFind = reportRepository.findByParam(null, null, "S", 0, 0);
        System.out.println("<<<<<<<<<<< list.size() = " + rResultFind.size());
        assertEquals(0, rResultFind.size());
        
        reportRepository.deleteAll();
	}
	
	@Test
	public void testCompare() {
		Report report = new Report();
		report.setTransId("1111");
		report.setStatus("S");
		reportRepository.save(report);
		
		Report report2 = new Report();
		report2.setTransId("1122");
		report2.setStatus("S");
		reportRepository.save(report2);
		
		System.out.println("<<<<<<<< after save");
		
		List<ReloadRequest> listRR = new ArrayList<ReloadRequest>();
    	List<Report> listReport    = new ArrayList<Report>();
    	
		Long countRR 	  = ReloadRequest.countReloadRequestsByParam(null, null, null, null);
    	Long countMongoDB = reportRepository.count();
    	
    	System.out.println("<<<<<<<<<<< countRR 	 = " + countRR);
    	System.out.println("<<<<<<<<<<< countMongoDB = " + countMongoDB);
    	
    	if(countRR != countMongoDB) {
    		System.out.println("<<<<<<<<<<<< different in count");
    		reportRepository.deleteAll();
    		
    		listRR = ReloadRequest.findAllReloadRequests();
    		listReport = ReportGenerator.copyReloadRequestToReport(listRR);
    		
    		reportRepository.save(listReport);
    	}
    	
    	System.out.println("<<<<<<<<<< last list = " + reportRepository.count());
        reportRepository.deleteAll();
        
	}
}
