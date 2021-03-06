// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TerminalIntegrationTest_Roo_IntegrationTest {
    
    declare @type: TerminalIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: TerminalIntegrationTest: @Transactional;
    
    @Test
    public void TerminalIntegrationTest.testCountTerminals() {
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", dod.getRandomTerminal());
        long count = com.djavafactory.pttech.rrm.domain.Terminal.countTerminals();
        org.junit.Assert.assertTrue("Counter for 'Terminal' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void TerminalIntegrationTest.testFindTerminal() {
        com.djavafactory.pttech.rrm.domain.Terminal obj = dod.getRandomTerminal();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.Terminal.findTerminal(id);
        org.junit.Assert.assertNotNull("Find method for 'Terminal' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Terminal' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void TerminalIntegrationTest.testFindAllTerminals() {
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", dod.getRandomTerminal());
        long count = com.djavafactory.pttech.rrm.domain.Terminal.countTerminals();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Terminal', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.djavafactory.pttech.rrm.domain.Terminal> result = com.djavafactory.pttech.rrm.domain.Terminal.findAllTerminals();
        org.junit.Assert.assertNotNull("Find all method for 'Terminal' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Terminal' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void TerminalIntegrationTest.testFindTerminalEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", dod.getRandomTerminal());
        long count = com.djavafactory.pttech.rrm.domain.Terminal.countTerminals();
        if (count > 20) count = 20;
        java.util.List<com.djavafactory.pttech.rrm.domain.Terminal> result = com.djavafactory.pttech.rrm.domain.Terminal.findTerminalEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Terminal' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Terminal' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void TerminalIntegrationTest.testFlush() {
        com.djavafactory.pttech.rrm.domain.Terminal obj = dod.getRandomTerminal();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.Terminal.findTerminal(id);
        org.junit.Assert.assertNotNull("Find method for 'Terminal' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTerminal(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Terminal' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TerminalIntegrationTest.testMerge() {
        com.djavafactory.pttech.rrm.domain.Terminal obj = dod.getRandomTerminal();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.Terminal.findTerminal(id);
        boolean modified =  dod.modifyTerminal(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.djavafactory.pttech.rrm.domain.Terminal merged = (com.djavafactory.pttech.rrm.domain.Terminal) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Terminal' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TerminalIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", dod.getRandomTerminal());
        com.djavafactory.pttech.rrm.domain.Terminal obj = dod.getNewTransientTerminal(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Terminal' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Terminal' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void TerminalIntegrationTest.testRemove() {
        com.djavafactory.pttech.rrm.domain.Terminal obj = dod.getRandomTerminal();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Terminal' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.Terminal.findTerminal(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Terminal' with identifier '" + id + "'", com.djavafactory.pttech.rrm.domain.Terminal.findTerminal(id));
    }
    
}
