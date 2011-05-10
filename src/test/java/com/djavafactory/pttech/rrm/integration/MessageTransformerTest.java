package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class MessageTransformerTest extends BaseManagerTestCase {

    @Test
    public void testTransformReloadRequest() {
        String msg = "{\"amount\":100.00,\"transCode\":1,\"spId\":\"4213232\",\"encryptedMsg\":null,\"mfgNo\":6767676,\"msgType\":\"N\",\"requestTime\":\"28042011120100\",\"transId\":\"00000000001\"}";
        ReloadRequestMessage requestMessage = new MessageTransformer().transformReloadRequest(msg);
        assert (requestMessage != null);
        assertEquals(requestMessage.getAmount(), new BigDecimal("100"));
        assert (requestMessage.getEncryptedMsg() == null);
        assert (requestMessage.getMfgNo() == 6767676L);
        assertEquals(requestMessage.getMsgType(), "N");
        assertEquals(requestMessage.getSpId(), "4213232");
        assert (requestMessage.getTransCode() == 1);
        assertEquals(requestMessage.getTransId(), "00000000001");
    }

    @Test
    public void testTransformInvalidMessage() {
        assert (new MessageTransformer().transformInvalidMessage(getReloadRequestMessage()) != null);
    }

    @Test
    public void testTransformInvalidStatusMessage() {
        assert (new MessageTransformer().transformInvalidStatusMessage(getReloadRequestMessage()) != null);
    }

    @Test
    public void testTransformKeyInvalidStatusMessage() {
        assert (new MessageTransformer().transformKeyInvalidStatusMessage(getReloadRequestMessage()) != null);
    }

    @Test
    public void testTransformSuccessMessage() {
        assert (new MessageTransformer().transformSuccessMessage(getReloadRequestMessage()) != null);
    }

    @Test
    public void testTransformTimeoutMessage() {
        assert (new MessageTransformer().transformTimeoutMessage(getReloadRequestMessage()) != null);
    }

    @Test
    public void testTransformMessageToReloadReq() {
        assert (new MessageTransformer().transformMessageToReloadReq(getReloadRequestMessage()) != null);
    }


    @Test
    public void testTransformReloadResponse() {
        String msg = "{\"transId\":\"00000000001\", \"statusMsg\":\"Success\", \"statusCode\":\"00\", \"responsetTime\":\"28042011120100\"}";
        ReloadResponseMessage responseMessage = new MessageTransformer().transformReloadResponse(msg);
        assert (responseMessage != null);
        assertEquals(responseMessage.getTransId(), "00000000001");
        assertEquals(responseMessage.getStatusCode(), "00");
        assertEquals(responseMessage.getStatusMsg(), "Success");
    }

    @Test
    public void testTransformMessageToJson() {
        assert (new MessageTransformer().transformMessageToJson(getReloadRequestMessage()) != null);
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
}
