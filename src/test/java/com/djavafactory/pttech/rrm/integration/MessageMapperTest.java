package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
}
