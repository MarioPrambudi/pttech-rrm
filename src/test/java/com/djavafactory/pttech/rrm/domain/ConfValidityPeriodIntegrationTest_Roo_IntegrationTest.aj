// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.ConfValidityPeriodDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ConfValidityPeriodIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ConfValidityPeriodIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ConfValidityPeriodIntegrationTest: @Transactional;
    
    @Autowired
    private ConfValidityPeriodDataOnDemand ConfValidityPeriodIntegrationTest.dod;
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testCountConfValidityPeriods() {
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", dod.getRandomConfValidityPeriod());
        long count = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.countConfValidityPeriods();
        org.junit.Assert.assertTrue("Counter for 'ConfValidityPeriod' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testFindConfValidityPeriod() {
        com.djavafactory.pttech.rrm.domain.ConfValidityPeriod obj = dod.getRandomConfValidityPeriod();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findConfValidityPeriod(id);
        org.junit.Assert.assertNotNull("Find method for 'ConfValidityPeriod' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'ConfValidityPeriod' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testFindAllConfValidityPeriods() {
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", dod.getRandomConfValidityPeriod());
        long count = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.countConfValidityPeriods();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'ConfValidityPeriod', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.djavafactory.pttech.rrm.domain.ConfValidityPeriod> result = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findAllConfValidityPeriods();
        org.junit.Assert.assertNotNull("Find all method for 'ConfValidityPeriod' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'ConfValidityPeriod' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testFindConfValidityPeriodEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", dod.getRandomConfValidityPeriod());
        long count = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.countConfValidityPeriods();
        if (count > 20) count = 20;
        java.util.List<com.djavafactory.pttech.rrm.domain.ConfValidityPeriod> result = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findConfValidityPeriodEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'ConfValidityPeriod' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'ConfValidityPeriod' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testFlush() {
        com.djavafactory.pttech.rrm.domain.ConfValidityPeriod obj = dod.getRandomConfValidityPeriod();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findConfValidityPeriod(id);
        org.junit.Assert.assertNotNull("Find method for 'ConfValidityPeriod' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyConfValidityPeriod(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'ConfValidityPeriod' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testMerge() {
        com.djavafactory.pttech.rrm.domain.ConfValidityPeriod obj = dod.getRandomConfValidityPeriod();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findConfValidityPeriod(id);
        boolean modified =  dod.modifyConfValidityPeriod(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.djavafactory.pttech.rrm.domain.ConfValidityPeriod merged = (com.djavafactory.pttech.rrm.domain.ConfValidityPeriod) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'ConfValidityPeriod' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", dod.getRandomConfValidityPeriod());
        com.djavafactory.pttech.rrm.domain.ConfValidityPeriod obj = dod.getNewTransientConfValidityPeriod(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'ConfValidityPeriod' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'ConfValidityPeriod' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ConfValidityPeriodIntegrationTest.testRemove() {
        com.djavafactory.pttech.rrm.domain.ConfValidityPeriod obj = dod.getRandomConfValidityPeriod();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ConfValidityPeriod' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findConfValidityPeriod(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'ConfValidityPeriod' with identifier '" + id + "'", com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findConfValidityPeriod(id));
    }
    
}
