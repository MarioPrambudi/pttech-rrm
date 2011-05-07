package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MessagePersistenceTest extends BaseManagerTestCase {

    @Test
    public void testMergeReloadRequest() {
        List<Object> list = new MessageMapper().mapNewReloadRequest(getReloadRequestMessage());
        assert (list.size() == 2);
        assert ((list.get(0) instanceof ReloadRequest) == true);
        assert ((list.get(1) instanceof ReloadRequestMessage) == true);

        Object object = new MessagePersistence().mergeReloadRequest(list);
        assert ((object instanceof ReloadRequestMessage) == true);
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
        reloadRequestMessage.setTransId("00000000000008");

        return reloadRequestMessage;
    }

}
