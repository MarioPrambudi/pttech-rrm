package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Spring Integration Filter class to sift out invalid messages.
 * Valid messages will be sent to the next flow whereas invalid ones will be redirected to a discard channel.
 *
 * @author Carine Leong
 */
public class MessageFilter {

    /**
     * Method to validate the message type in the queue.rrm.reload.req queue.
     * Valid message types: N (New), F (Failed), E (Expired), S (Success) and M (Manual Cancellation)
     *
     * @param requestMessage ReloadRequestMessage object
     * @return true/false
     */
    public Boolean reloadRequestFilter(ReloadRequestMessage requestMessage) {
        if (StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.RELOAD_REQUEST_NEW)) {
            return true;
        } else if (StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.RELOAD_REQUEST_FAILED)) {
            return true;
        } else if (StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.RELOAD_REQUEST_EXPIRED)) {
            return true;
        } else if (StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.RELOAD_REQUEST_SUCCESS)) {
            return true;
        } else if (StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.RELOAD_REQUEST_MANUALCANCEL)) {
            return true;
        }

        return false;
    }

    /**
     * Method to validate whether the incoming TnG key message has exceeded the configured time-out period.
     *
     * @param requestMessage ReloadRequestMessage object
     * @return true/false
     */
    public Boolean validateTimeoutFilter(ReloadRequestMessage requestMessage) {
        Date tngKeyRequestTime = requestMessage.getRequestTime();
        Long timeOutPeriod = -1L;

        List<Configuration> configList = Configuration.findConfigurationsByConfigKey(Constants.CONFIG_TNG_TIMEOUT).getResultList();
        if (configList != null && !configList.isEmpty()) {
            timeOutPeriod = Long.valueOf(configList.get(0).getConfigValue());
        }

        List<ReloadRequest> reloadList = ReloadRequest.findReloadRequestsByTransId(requestMessage.getTransId()).getResultList();
        if (reloadList != null && !reloadList.isEmpty()) {
            ReloadRequest reloadRequest = reloadList.get(0);
            return (timeOutPeriod <= 0L || (reloadRequest.getRequestedTime() != null && tngKeyRequestTime != null && (tngKeyRequestTime.getTime() - reloadRequest.getRequestedTime().getTime()) <= timeOutPeriod)) ? true : false;
        }

        return false;
    }
}