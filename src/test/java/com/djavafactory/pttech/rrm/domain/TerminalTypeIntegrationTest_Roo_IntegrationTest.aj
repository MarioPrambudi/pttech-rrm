// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TerminalTypeIntegrationTest_Roo_IntegrationTest {
    
    declare @type: TerminalTypeIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: TerminalTypeIntegrationTest: @Transactional;
    
    @Test
    public void TerminalTypeIntegrationTest.testCountTerminalTypes() {
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", dod.getRandomTerminalType());
        long count = com.djavafactory.pttech.rrm.domain.TerminalType.countTerminalTypes();
        org.junit.Assert.assertTrue("Counter for 'TerminalType' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void TerminalTypeIntegrationTest.testFindTerminalType() {
        com.djavafactory.pttech.rrm.domain.TerminalType obj = dod.getRandomTerminalType();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.TerminalType.findTerminalType(id);
        org.junit.Assert.assertNotNull("Find method for 'TerminalType' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'TerminalType' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void TerminalTypeIntegrationTest.testFindAllTerminalTypes() {
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", dod.getRandomTerminalType());
        long count = com.djavafactory.pttech.rrm.domain.TerminalType.countTerminalTypes();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'TerminalType', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.djavafactory.pttech.rrm.domain.TerminalType> result = com.djavafactory.pttech.rrm.domain.TerminalType.findAllTerminalTypes();
        org.junit.Assert.assertNotNull("Find all method for 'TerminalType' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'TerminalType' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void TerminalTypeIntegrationTest.testFindTerminalTypeEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", dod.getRandomTerminalType());
        long count = com.djavafactory.pttech.rrm.domain.TerminalType.countTerminalTypes();
        if (count > 20) count = 20;
        java.util.List<com.djavafactory.pttech.rrm.domain.TerminalType> result = com.djavafactory.pttech.rrm.domain.TerminalType.findTerminalTypeEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'TerminalType' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'TerminalType' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void TerminalTypeIntegrationTest.testFlush() {
        com.djavafactory.pttech.rrm.domain.TerminalType obj = dod.getRandomTerminalType();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.TerminalType.findTerminalType(id);
        org.junit.Assert.assertNotNull("Find method for 'TerminalType' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTerminalType(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'TerminalType' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TerminalTypeIntegrationTest.testMerge() {
        com.djavafactory.pttech.rrm.domain.TerminalType obj = dod.getRandomTerminalType();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.TerminalType.findTerminalType(id);
        boolean modified =  dod.modifyTerminalType(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.djavafactory.pttech.rrm.domain.TerminalType merged = (com.djavafactory.pttech.rrm.domain.TerminalType) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'TerminalType' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TerminalTypeIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", dod.getRandomTerminalType());
        com.djavafactory.pttech.rrm.domain.TerminalType obj = dod.getNewTransientTerminalType(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'TerminalType' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'TerminalType' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void TerminalTypeIntegrationTest.testRemove() {
        com.djavafactory.pttech.rrm.domain.TerminalType obj = dod.getRandomTerminalType();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'TerminalType' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.TerminalType.findTerminalType(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'TerminalType' with identifier '" + id + "'", com.djavafactory.pttech.rrm.domain.TerminalType.findTerminalType(id));
    }
    
}
