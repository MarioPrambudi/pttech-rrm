package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.annotation.ExpectedException;

@RooIntegrationTest(entity = Terminal.class)
public class TerminalIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Test
    @ExpectedException(org.springframework.orm.jpa.JpaSystemException.class)
    public void testRemove() {
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
