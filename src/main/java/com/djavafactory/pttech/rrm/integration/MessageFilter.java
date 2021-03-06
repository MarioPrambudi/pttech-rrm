package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import com.djavafactory.pttech.rrm.exception.RrmBusinessException;
import com.djavafactory.pttech.rrm.exception.RrmStatusCode;
import epg.webservice.ReloadRequestResponse;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.JAXBElement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Spring Integration Filter class to sift out invalid messages.
 * Valid messages will be sent to the next flow whereas invalid ones will be redirected to a discard channel.
 *
 * @author Carine Leong
 */
public class MessageFilter {
    private static Long milisecinsec = 1000L;

    /**
     * Method to validate the message type in the queue.rrm.reload.req queue.
     * Validate whether the Success/Failed/Expired reload request from RTM  and key request from TnG is allow to proceed based on the current status in RRM.
     * Valid message types: N (New), F (Failed), E (Expired), S (Success) and M (Manual Cancellation)
     *
     * @param requestMessage ReloadRequestMessage object
     * @return true/false
     */
    public Boolean reloadRequestFilter(ReloadRequestMessage requestMessage) {
        if (Arrays.asList(Constants.RELOAD_REQUEST_NEW, Constants.RELOAD_REQUEST_FAILED,
                Constants.RELOAD_REQUEST_EXPIRED, Constants.RELOAD_REQUEST_SUCCESS, Constants.RELOAD_REQUEST_TNG_KEY)
                .contains(requestMessage.getMsgType())) {

            if (!validateFieldLength(requestMessage)) {
                return false;
            } else if (!requestMessage.getMsgType().equalsIgnoreCase(Constants.RELOAD_REQUEST_NEW)) {
                List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(requestMessage.getTransId()).getResultList();
                if (reloadRecord == null || reloadRecord.isEmpty() ||
                        (StringUtils.equalsIgnoreCase(requestMessage.getMsgType(), Constants.RELOAD_REQUEST_TNG_KEY) && !StringUtils.equalsIgnoreCase(reloadRecord.get(0).getStatus(), Constants.RELOAD_STATUS_NEW)) ||
                        (!StringUtils.equalsIgnoreCase(reloadRecord.get(0).getStatus(), Constants.RELOAD_STATUS_PENDING) && !StringUtils.equalsIgnoreCase(reloadRecord.get(0).getStatus(), Constants.RELOAD_STATUS_PENDINGCANCEL))) {
                    setReloadMessageStatus(requestMessage, RrmStatusCode.STS_INVALIDSTATUS.getCode(), RrmStatusCode.STS_INVALIDSTATUS.getDescription());
                    return false;
                }
            }
            setReloadMessageStatus(requestMessage, RrmStatusCode.STS_SUCCESS.getCode(), RrmStatusCode.STS_SUCCESS.getDescription());
            return true;
        } else {
            setReloadMessageStatus(requestMessage, RrmStatusCode.STS_INVALIDMSGTYPE.getCode(), RrmStatusCode.STS_INVALIDMSGTYPE.getDescription());
            return false;
        }
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
            timeOutPeriod = Long.valueOf(configList.get(0).getConfigValue()) * milisecinsec;
        }

        List<ReloadRequest> reloadList = ReloadRequest.findReloadRequestsByTransId(requestMessage.getTransId()).getResultList();
        if (reloadList != null && !reloadList.isEmpty()) {
            ReloadRequest reloadRequest = reloadList.get(0);
            if (timeOutPeriod <= 0L || (reloadRequest.getRequestedTime() != null && tngKeyRequestTime != null && (tngKeyRequestTime.getTime() - reloadRequest.getRequestedTime().getTime()) <= timeOutPeriod)) {
                setReloadMessageStatus(requestMessage, RrmStatusCode.STS_SUCCESS.getCode(), RrmStatusCode.STS_SUCCESS.getDescription());
                return true;
            } else {
                setReloadMessageStatus(requestMessage, RrmStatusCode.STS_MSGTIMEOUT.getCode(), RrmStatusCode.STS_MSGTIMEOUT.getDescription());
                return false;
            }
        }
        setReloadMessageStatus(requestMessage, RrmStatusCode.STS_INVALIDSTATUS.getCode(), RrmStatusCode.STS_INVALIDSTATUS.getDescription());
        return false;
    }

    /**
     * Method to validate whether the incoming RTM message needs to be routed to RMI or not.
     *
     * @param requestMessage ReloadRequestMessage object
     * @return true/false
     */
    public Boolean rtmRequestFilter(ReloadRequestMessage requestMessage) {
        return (!StringUtils.equalsIgnoreCase(Constants.RELOAD_REQUEST_SUCCESS, requestMessage.getMsgType()));
    }

    /**
     * Method to validate whether the TNG card verification response needs to be routed to RMI or not.
     *
     * @param message JAXBElement<ReloadRequestResponse> object
     * @return true/false
     */
    public Boolean tngRequestReplyFilter(JAXBElement<ReloadRequestResponse> message) {
        return (!StringUtils.equalsIgnoreCase(Constants.RESPONSE_CODE_SUCCESS, message.getValue().getReturn().getStatusCode()));
    }

    /**
     * Method to validate whether the Manual Cancellation response needs to be routed to RMI or not.
     *
     * @param message ReloadResponseMessage object
     * @return true/false
     */
    public Boolean manualCancelResponseFilter(ReloadResponseMessage message) {
        return (StringUtils.equalsIgnoreCase(Constants.RESPONSE_CODE_SUCCESS, message.getStatusCode()));
    }

    private boolean validateFieldLength(ReloadRequestMessage requestMessage) {
        try {
            isValidLength(String.valueOf(requestMessage.getAmount()), 7);
            isValidLength(String.valueOf(requestMessage.getMfgNo()), 18);
            isValidLength(requestMessage.getSpId(), 4);
            isValidLength(requestMessage.getTransCode(), 2);
            isValidLength(requestMessage.getTransId(), 16);
            isValidLength(requestMessage.getMobileNo(), 10);
        } catch (RrmBusinessException e) {
            setReloadMessageStatus(requestMessage, e.getErrorCode(), e.getMessage());
            return false;
        }
        return true;
    }

    private void setReloadMessageStatus(ReloadRequestMessage requestMessage, String code, String message) {
        requestMessage.setStatusCode(code);
        requestMessage.setStatusMsg(message);
    }

    private static boolean isValidLength(String str, int len) throws RrmBusinessException {
        if (str != null && !"".equals(str)) {
            if (str.length() > len) {
                RrmBusinessException fbe =
                        new RrmBusinessException(RrmStatusCode.STS_INVALIDLENGTH.getCode(), RrmStatusCode.STS_INVALIDLENGTH.getDescription(), str);

                throw fbe;
            }
        }
        return true;
    }
}