package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import com.djavafactory.pttech.rrm.ws.KeyRequest;
import epg.webservice.EpgResponse;
import epg.webservice.ObjectFactory;
import epg.webservice.ReloadRequestResponse;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.*;

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
    public void testMapTngResponseToReloadResponse() {
        List<Object> list = new MessageMapper().mapTngResponseToReloadResponse(getReloadReqResponseMessage());
        assert (list.size() == 2);
        assert ((list.get(0) instanceof ReloadRequest) == true);
        assert ((list.get(1) instanceof ReloadResponseMessage) == true);
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
        reloadRequestMessage.setTransCode("2");
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

    private JAXBElement<ReloadRequestResponse> getReloadReqResponseMessage() {
        ReloadRequestResponse message = new ObjectFactory().createReloadRequestResponse();
        EpgResponse response = new EpgResponse();
        response.setStatusCode("00");
        response.setStatusMessage("Success");
        response.setTransactionId("00000000000001");
        message.setReturn(response);
        return new ObjectFactory().createReloadRequestResponse(message);
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
