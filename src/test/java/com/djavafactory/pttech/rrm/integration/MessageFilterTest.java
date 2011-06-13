package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import epg.webservice.EpgResponse;
import epg.webservice.ObjectFactory;
import epg.webservice.ReloadRequestResponse;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class MessageFilterTest extends BaseManagerTestCase {

    @Test
    public void testReloadRequestFilter() {
        assert (new MessageFilter().reloadRequestFilter(getReloadRequestMessage(new Date())) == true);
    }

    @Test
    public void testTimeoutFilter() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);

        assert (new MessageFilter().validateTimeoutFilter(getReloadRequestMessage(cal.getTime())) == false);
    }

    @Test
    public void testRtmRequestFilter() {
        assert (new MessageFilter().rtmRequestFilter(getReloadRequestMessage(new Date())) == true);
    }

    @Test
    public void testTngRequestReplyFilter() {
        assert (new MessageFilter().tngRequestReplyFilter(getReloadRequestResponseMessage()) == false);
    }

    @Test
    public void testManualCancelResponseFilter() {
        assert (new MessageFilter().manualCancelResponseFilter(getReloadResponseMessage()) == true);
    }

    private ReloadRequestMessage getReloadRequestMessage(Date date) {
        ReloadRequestMessage reloadRequestMessage = new ReloadRequestMessage();
        reloadRequestMessage.setAmount(new BigDecimal("10.00"));
        reloadRequestMessage.setEncryptedMsg(null);
        reloadRequestMessage.setMfgNo(101010019L);
        reloadRequestMessage.setMsgType("N");
        reloadRequestMessage.setRequestTime(date);
        reloadRequestMessage.setSpId("8987");
        reloadRequestMessage.setTransCode("2");
        reloadRequestMessage.setTransId("00000000000001");

        return reloadRequestMessage;
    }

    private JAXBElement<ReloadRequestResponse> getReloadRequestResponseMessage() {
        ReloadRequestResponse response = new ObjectFactory().createReloadRequestResponse();
        EpgResponse epgResponse = new ObjectFactory().createEpgResponse();
        epgResponse.setStatusCode("00");
        epgResponse.setStatusMessage("Success");
        epgResponse.setTransactionId("00000000000001");
        response.setReturn(epgResponse);
        return new ObjectFactory().createReloadRequestResponse(response);
    }

    private ReloadResponseMessage getReloadResponseMessage() {
        ReloadResponseMessage response = new ReloadResponseMessage();
        response.setStatusCode("00");
        response.setStatusMsg("Success");
        response.setTransId("00000000000001");
        return response;
    }
}
