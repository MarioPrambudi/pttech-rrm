package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class MessageFilterTest extends BaseManagerTestCase {

    @Test
    public void testReloadRequestFilter() {
        assert (new MessageFilter().reloadRequestFilter(getReloadRequestMessage(new Date())) == true);
    }

    @Test
    public void testRtmReloadRequestFilter() {
        assert (new MessageFilter().rtmReloadRequestFilter(getReloadRequestMessage(new Date())) == false);
    }

    @Test
    public void testTimeoutFilter() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);

        assert (new MessageFilter().validateTimeoutFilter(getReloadRequestMessage(cal.getTime())) == false);
    }

    @Test
    public void testKeyRequestFilter() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);

        assert (new MessageFilter().keyRequestFilter(getReloadRequestMessage(cal.getTime())) == false);
    }

    private ReloadRequestMessage getReloadRequestMessage(Date date) {
        ReloadRequestMessage reloadRequestMessage = new ReloadRequestMessage();
        reloadRequestMessage.setAmount(new BigDecimal("10.00"));
        reloadRequestMessage.setEncryptedMsg("FDsfjksdf&#874293");
        reloadRequestMessage.setMfgNo(101010019L);
        reloadRequestMessage.setMsgType("N");
        reloadRequestMessage.setRequestTime(date);
        reloadRequestMessage.setSpId("318938123");
        reloadRequestMessage.setTransCode(2);
        reloadRequestMessage.setTransId("00000000000001");

        return reloadRequestMessage;
    }
}
