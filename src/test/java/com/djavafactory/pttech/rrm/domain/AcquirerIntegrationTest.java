package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RooIntegrationTest(entity = Acquirer.class)
public class AcquirerIntegrationTest {

    @Autowired
    private AcquirerDataOnDemand dod;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testFindAcquirersByParam() {
        Acquirer obj = dod.getRandomAcquirer();
        obj.merge();
        assertNotNull(Acquirer.findAcquirersByParam(obj.getName(), null, true, null, -1, -1));
        assertEquals(1, Acquirer.findAcquirersByParam(obj.getName(), null, true, null, -1, -1).getResultList().size());
    }
}
