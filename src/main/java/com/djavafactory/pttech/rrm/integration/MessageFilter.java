package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
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
        }

		return false;
	}

    public Boolean validateTimeoutFilter(ReloadRequestMessage requestMessage) {
        Date tngKeyRequestTime = requestMessage.getRequestTime();
        Long timeOutPeriod = -1L;

        List<Configuration> configList = new Configuration().findConfigurationsByConfigKey(Constants.timeOutKey).getResultList();
        if(configList != null && !configList.isEmpty()) {
            timeOutPeriod = Long.valueOf(configList.get(0).getConfigValue());
        }

        List<ReloadRequest> reloadList = new ReloadRequest().findReloadRequestsByTransId(requestMessage.getTransId()).getResultList();
        if(reloadList != null && !reloadList.isEmpty()) {
            ReloadRequest reloadRequest = reloadList.get(0);
            return (timeOutPeriod <= 0L || (reloadRequest.getRequestedTime() != null && tngKeyRequestTime != null && (tngKeyRequestTime.getTime() - reloadRequest.getRequestedTime().getTime()) <= timeOutPeriod)) ? true : false;
        }

		return false;
	}
}