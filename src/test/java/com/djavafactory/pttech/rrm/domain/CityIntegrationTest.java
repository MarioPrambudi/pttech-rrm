package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import static junit.framework.Assert.assertNotNull;

@RooIntegrationTest(entity = City.class)
public class CityIntegrationTest {

    @Autowired
    private CityDataOnDemand dod;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testFindCitiesByParam() {
        City obj = dod.getRandomCity();
        obj.merge();
        assertNotNull(City.findCitiesByState(obj.getAcquirerState().getId()));
    }
}
