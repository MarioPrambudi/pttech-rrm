package com.djavafactory.pttech.rrm.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@RooIntegrationTest(entity = Configuration.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class ConfigurationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

}
