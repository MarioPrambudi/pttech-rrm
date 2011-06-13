package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
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
    public void testRouteErrorRequest() {
        ReloadRequestMessage requestMessage = getReloadRequestMessage();
        requestMessage.setMsgType("rtm");
        assertEquals(new MessageRouter().routeErrorRequest(requestMessage), "rtmErrorChannel");
    }

    private ReloadRequestMessage getReloadRequestMessage() {
        ReloadRequestMessage reloadRequestMessage = new ReloadRequestMessage();
        reloadRequestMessage.setAmount(new BigDecimal("10.00"));
        reloadRequestMessage.setEncryptedMsg(null);
        reloadRequestMessage.setMfgNo(101010019L);
        reloadRequestMessage.setMsgType("N");
        reloadRequestMessage.setRequestTime(new Date());
        reloadRequestMessage.setSpId("318938123");
        reloadRequestMessage.setTransCode("2");
        reloadRequestMessage.setTransId("00000000000007");

        return reloadRequestMessage;
    }
}
