package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RooIntegrationTest(entity = TerminalType.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class TerminalTypeIntegrationTest {

    @Autowired
    private TerminalTypeDataOnDemand dod;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testFindTerminalTypesByParam() {
        TerminalType obj = dod.getRandomTerminalType();
        obj.merge();
        assertNotNull(TerminalType.findTerminalTypesByParam(obj.getName(), true, null, -1, -1));
        assertEquals(1, TerminalType.findTerminalTypesByParam(obj.getName(), true, null, -1, -1).getResultList().size());
    }
}
