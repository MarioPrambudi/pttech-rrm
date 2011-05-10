package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RooIntegrationTest(entity = Terminal.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class TerminalIntegrationTest {

    @Autowired
    private TerminalDataOnDemand dod;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testFindTerminalsByParam() {
        Terminal obj = dod.getRandomTerminal();
        obj.merge();
        assertNotNull(Terminal.findTerminalsByParam(obj.getTerminalId(), obj.getStatus(), -1L, -1L, -1, -1, null));
        assertEquals(1, Terminal.findTerminalsByParam(obj.getTerminalId(), obj.getStatus(), -1L, -1L, -1, -1, null).getResultList().size());
    }

    @Test
    public void testTotalTerminals() {
        Terminal obj = dod.getRandomTerminal();
        obj.merge();
        assert (Terminal.totalTerminals() > 0);
    }
}
