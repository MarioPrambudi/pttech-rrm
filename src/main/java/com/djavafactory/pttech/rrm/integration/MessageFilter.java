package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.integration.Message;

import java.util.List;


public class MessageFilter {

    public Boolean reloadRequestFilter(ReloadRequestMessage requestMessage) {
        if(StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.newReloadRequest)) {
            return true;
        } else if(StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.failedReloadRequest)) {
            return true;
        } else if(StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.expiredReloadRequest)) {
            return true;
        } else if(StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.successReloadRequest)) {
            return true;
        } else if(StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.tngKeyRequest)) {
            return true;
        }

		return false;
	}
}