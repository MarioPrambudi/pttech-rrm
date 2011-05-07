package com.djavafactory.pttech.rrm.integration;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MessageHandlerTest extends BaseManagerTestCase {


    @Test
    public void testReloadRequestFilter() {
        String testString = "This is a testing string";
        assertEquals(new MessageHandler().reloadRequestHandler(testString.getBytes()), testString);
    }

}
