package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class MessageRouterTest extends BaseManagerTestCase {

    @Test
    public void testRouteReloadRequest() {
        assertEquals(new MessageRouter().routeReloadRequest(getReloadRequestMessage()), "newReloadReqPersistChannel");
    }

    @Test
    public void testRouteReloadResponse() {
        assertEquals(new MessageRouter().routeReloadResponse(getReloadResponseMessage()), null);
    }

    private ReloadRequestMessage getReloadRequestMessage() {
        ReloadRequestMessage reloadRequestMessage = new ReloadRequestMessage();
        reloadRequestMessage.setAmount(new BigDecimal("10.00"));
        reloadRequestMessage.setEncryptedMsg(null);
        reloadRequestMessage.setMfgNo(101010019L);
        reloadRequestMessage.setMsgType("N");
        reloadRequestMessage.setRequestTime(new Date());
        reloadRequestMessage.setSpId("318938123");
        reloadRequestMessage.setTransCode(2);
        reloadRequestMessage.setTransId("00000000000007");

        return reloadRequestMessage;
    }

    private ReloadResponseMessage getReloadResponseMessage() {
        ReloadResponseMessage reloadResponseMessage = new ReloadResponseMessage();
        reloadResponseMessage.setResponseTime(new Date());
        reloadResponseMessage.setStatusCode("00");
        reloadResponseMessage.setStatusMsg("Success");
        reloadResponseMessage.setTransId("00000000000001");

        return reloadResponseMessage;
    }
}
