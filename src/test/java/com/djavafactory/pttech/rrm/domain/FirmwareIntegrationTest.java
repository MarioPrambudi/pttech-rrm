package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.annotation.ExpectedException;

@RooIntegrationTest(entity = Firmware.class)
public class FirmwareIntegrationTest {

    @Autowired
    private FirmwareDataOnDemand dod;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    @ExpectedException(org.springframework.orm.jpa.JpaSystemException.class)
    public void testRemove() {
        com.djavafactory.pttech.rrm.domain.Firmware obj = dod.getRandomFirmware();
        org.junit.Assert.assertNotNull("Data on demand for 'Firmware' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Firmware' failed to provide an identifier", id);
        obj = com.djavafactory.pttech.rrm.domain.Firmware.findFirmware(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Firmware' with identifier '" + id + "'", com.djavafactory.pttech.rrm.domain.Firmware.findFirmware(id));
    }
}
