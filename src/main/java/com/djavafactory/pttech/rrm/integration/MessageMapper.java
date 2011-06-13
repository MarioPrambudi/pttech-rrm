package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import com.djavafactory.pttech.rrm.exception.RrmStatusCode;
import com.djavafactory.pttech.rrm.ws.KeyRequest;
import epg.webservice.ReloadRequestResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Spring Integration Mapper class.
 *
 * @author Carine Leong
 */
public class MessageMapper {
    private static final Log logger = LogFactory.getLog(MessageMapper.class);

    /**
     * Method to map the Reload Request Message to List<Object>.
     *
     * @param message ReloadRequestMessage object
     * @return List of Object.
     */
    public List<Object> mapNewReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.RELOAD_STATUS_NEW));
        objectList.add(message);
        return objectList;
    }

    /**
     * Method to map the RTM Reload Request Message to List<Object>.
     *
     * @param message ReloadRequestMessage object
     * @return List of Object.
     */
    public List<Object> mapRtmReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        if (StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.RELOAD_REQUEST_FAILED)) {
            objectList.add(convertMessageToReloadRequest(message, Constants.RELOAD_REQUEST_FAILED));
        } else if (StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.RELOAD_REQUEST_EXPIRED)) {
            objectList.add(convertMessageToReloadRequest(message, Constants.RELOAD_REQUEST_EXPIRED));
        } else if (StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.RELOAD_REQUEST_SUCCESS)) {
            objectList.add(convertMessageToReloadRequest(message, Constants.RELOAD_REQUEST_SUCCESS));
        }
        objectList.add(message);
        return objectList;
    }

    /**
     * Method to map the Timeout TnG Key Message to List<Object>.
     *
     * @param message ReloadRequestMessage object
     * @return List of Object.
     */
    public List<Object> mapTimeoutReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.RELOAD_STATUS_FAILED));
        objectList.add(message);
        return objectList;
    }

    /**
     * Method to map the Pending Reload Request Message to List<Object>.
     *
     * @param message ReloadRequestMessage object
     * @return List of Object.
     */
    public List<Object> mapTngReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.RELOAD_STATUS_PENDING));
        objectList.add(message);
        return objectList;
    }

    /**
     * Method to map the TnG Response Message to List<Object>.
     *
     * @param message ReloadRequestResponse object
     * @return List of Object.
     */
    public List<Object> mapTngResponseToReloadResponse(JAXBElement<ReloadRequestResponse> message) {
        ReloadResponseMessage responseMessage = new ReloadResponseMessage();
        responseMessage.setTransId(message.getValue().getReturn().getTransactionId());
        responseMessage.setStatusCode(message.getValue().getReturn().getStatusCode());
        responseMessage.setStatusMsg(message.getValue().getReturn().getStatusMessage());
        responseMessage.setResponseTime(new Date());

        List<Object> objectList = new ArrayList<Object>();
        ReloadRequest reloadRequest = new ReloadRequest();

        List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(message.getValue().getReturn().getTransactionId()).getResultList();
        if (reloadRecord != null && !reloadRecord.isEmpty()) {
            reloadRequest = reloadRecord.get(0);
            if (!StringUtils.equalsIgnoreCase(Constants.RESPONSE_CODE_SUCCESS, message.getValue().getReturn().getStatusCode())) {
                reloadRequest.setStatus(Constants.RELOAD_STATUS_FAILED);
            }
        }

        logger.info("[mapTngReloadResponse - New ReloadReq object] >> " + reloadRequest);
        objectList.add(reloadRequest);
        objectList.add(responseMessage);

        return objectList;
    }

    /**
     * Method to map the TnG Key Request Message to ReloadRequestMessage.
     *
     * @param message KeyRequest object
     * @return ReloadRequestMessage.
     */
    public ReloadRequestMessage mapTngKeyReqToReloadReq(KeyRequest message) {
        ReloadRequestMessage requestMessage = new ReloadRequestMessage();
        requestMessage.setAmount(message.getAmount());
        requestMessage.setMfgNo(message.getMfgNo());
        requestMessage.setEncryptedMsg(message.getEncryptedMessage());
        requestMessage.setTransId(message.getTransactionId());
        requestMessage.setRequestTime(message.getRequestDateTime());
        requestMessage.setMsgType(Constants.RELOAD_REQUEST_TNG_KEY);
        requestMessage.setStatusCode(RrmStatusCode.STS_SUCCESS.getCode());
        requestMessage.setStatusMsg(RrmStatusCode.STS_SUCCESS.getDescription());
        logger.info("[mapTngKeyReqToReloadReq - New ReloadRequestMessage object] >> " + requestMessage);
        return requestMessage;
    }

    /**
     * Method to map the Manual Cancellation Request Reload to to ReloadRequestMessage.
     *
     * @param request ReloadRequest object
     * @return to ReloadRequestMessage.
     */
    public ReloadRequestMessage mapManualCancelReloadRequest(ReloadRequest request) {
        ReloadRequestMessage requestMessage = new ReloadRequestMessage();
        requestMessage.setMfgNo(request.getMfgNumber());
        requestMessage.setTransId(request.getTransId());
        requestMessage.setRequestTime(new Date());
        requestMessage.setMsgType(Constants.RELOAD_REQUEST_MANUALCANCEL);
        logger.info("[mapReloadRequestToMessage - New ReloadRequestMessage object] >> " + requestMessage);
        return requestMessage;
    }

    private ReloadRequest convertMessageToReloadRequest(ReloadRequestMessage message, String status) {
        ReloadRequest reloadRequest;

        List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(message.getTransId()).getResultList();
        if (reloadRecord != null && !reloadRecord.isEmpty()) {
            reloadRequest = reloadRecord.get(0);
            reloadRequest.setModifiedTime(message.getRequestTime());
            if (message.getAcquirerTerminal() != null && !"".equals(message.getAcquirerTerminal())) {
                reloadRequest.setAcquirerTerminal(message.getAcquirerTerminal());
            }
        } else {
            reloadRequest = new ReloadRequest();
            reloadRequest.setTransId(message.getTransId());
            reloadRequest.setMfgNumber(message.getMfgNo());
            reloadRequest.setReloadAmount(message.getAmount());
            reloadRequest.setServiceProviderId(message.getSpId());
            reloadRequest.setTransCode(message.getTransCode());
            reloadRequest.setRequestedTime(message.getRequestTime());
            reloadRequest.setCmmpTrxId(message.getCmmpTransId());
            reloadRequest.setModifiedTime(null);
            if (message.getMobileNo() != null && !"".equals(message.getMobileNo())) {
                reloadRequest.setMobileNo(Long.valueOf(message.getMobileNo()));
            }
        }
        if (StringUtils.equalsIgnoreCase(Constants.RELOAD_STATUS_PENDING, status)) {
            reloadRequest.setTngKey(message.getEncryptedMsg());
        } else {
            reloadRequest.setTngKey(null);
        }
        reloadRequest.setStatus(status);

        logger.info("[convertMessageToReloadRequest - New ReloadReq object] >> " + reloadRequest);
        return reloadRequest;
    }
}
