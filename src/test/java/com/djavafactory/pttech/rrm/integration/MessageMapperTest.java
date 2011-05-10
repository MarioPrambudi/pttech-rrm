package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import com.djavafactory.pttech.rrm.ws.KeyRequest;
import com.djavafactory.pttech.rrm.ws.ReloadReqResponse;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class MessageMapperTest extends BaseManagerTestCase {

    @Test
    public void testMapNewReloadRequest() {
        List<Object> list = new MessageMapper().mapNewReloadRequest(getReloadRequestMessage());
        assert (list.size() == 2);
        assert ((list.get(0) instanceof ReloadRequest) == true);
        assert ((list.get(1) instanceof ReloadRequestMessage) == true);
    }

    @Test
    public void testMapRtmReloadRequest() {
        List<Object> list = new MessageMapper().mapRtmReloadRequest(getReloadRequestMessage());
        assert (list.size() == 2);
        assert ((list.get(0) instanceof ReloadRequest) == true);
        assert ((list.get(1) instanceof ReloadRequestMessage) == true);
    }

    @Test
    public void testMapTimeoutReloadRequest() {
        List<Object> list = new MessageMapper().mapTimeoutReloadRequest(getReloadRequestMessage());
        assert (list.size() == 2);
        assert ((list.get(0) instanceof ReloadRequest) == true);
        assert ((list.get(1) instanceof ReloadRequestMessage) == true);
    }

    @Test
    public void testMapTngReloadRequest() {
        List<Object> list = new MessageMapper().mapTngReloadRequest(getReloadRequestMessage());
        assert (list.size() == 2);
        assert ((list.get(0) instanceof ReloadRequest) == true);
        assert ((list.get(1) instanceof ReloadRequestMessage) == true);
    }

    @Test
    public void testMapTngReloadResponse() {
        List<Object> list = new MessageMapper().mapTngReloadResponse(getReloadResponseMessage());
        assert (list.size() == 2);
        assert ((list.get(0) instanceof ReloadRequest) == true);
        assert ((list.get(1) instanceof ReloadResponseMessage) == true);
    }

    @Test
    public void testMapTngResponseToReloadResponse() {
        ReloadResponseMessage message = new MessageMapper().mapTngResponseToReloadResponse(getReloadReqResponseMessage());
        assert (message != null);
        assertEquals("00", message.getStatusCode());
        assertEquals("Success", message.getStatusMsg());
        assertEquals("00000000000001", message.getTransId());
    }

    public void testMapTngKeyReqToReloadReq() {
        ReloadRequestMessage message = new MessageMapper().mapTngKeyReqToReloadReq(getKeyRequestMessage());
        assert (message != null);
        assertEquals("00000000000001", message.getTransId());
        assertEquals(new BigDecimal("200"), message.getAmount());
        assertEquals("Message", message.getEncryptedMsg());
        assertEquals("12345L", message.getMfgNo());
        assertEquals("N", message.getMsgType());
        assertNotNull(message.getRequestTime());
        assertNull(message.getSpId());
        assertNull(message.getTransCode());
    }

    private ReloadRequestMessage getReloadRequestMessage() {
        ReloadRequestMessage reloadRequestMessage = new ReloadRequestMessage();
        reloadRequestMessage.setAmount(new BigDecimal("10.00"));
        reloadRequestMessage.setEncryptedMsg(null);
        reloadRequestMessage.setMfgNo(101010019L);
        reloadRequestMessage.setMsgType("F");
        reloadRequestMessage.setRequestTime(new Date());
        reloadRequestMessage.setSpId("318938123");
        reloadRequestMessage.setTransCode(2);
        reloadRequestMessage.setTransId("00000000000001");

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

    private ReloadReqResponse getReloadReqResponseMessage() {
        ReloadReqResponse message = new ReloadReqResponse();
        message.setStatusCode("00");
        message.setStatusMsg("Success");
        message.setTransactionId("00000000000001");

        return message;
    }

    private KeyRequest getKeyRequestMessage() {
        KeyRequest message = new KeyRequest();
        message.setTransactionId("00000000000001");
        message.setAmount(new BigDecimal("200"));
        message.setEncryptedMessage("Message");
        message.setMfgNo(12345L);
        message.setRequestDateTime(new Date());

        return message;
    }
}
