package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
     * Method to map the Reload Request Response to List<Object>.
     *
     * @param message ReloadResponseMessage object
     * @return List of Object.
     */
    public List<Object> mapTngReloadResponse(ReloadResponseMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        ReloadRequest reloadRequest = new ReloadRequest();

        List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(message.getTransId()).getResultList();
        if (reloadRecord != null && !reloadRecord.isEmpty()) {
            reloadRequest = reloadRecord.get(0);
            if (!StringUtils.equalsIgnoreCase(Constants.RESPONSE_CODE_SUCCESS, message.getStatusCode())) {
                reloadRequest.setStatus(Constants.RELOAD_STATUS_FAILED);
            }
        }

        logger.info("[mapTngReloadResponse - New ReloadRequest object] >> " + reloadRequest);
        objectList.add(reloadRequest);
        objectList.add(message);
        return objectList;
    }

    private ReloadRequest convertMessageToReloadRequest(ReloadRequestMessage message, String status) {
        ReloadRequest reloadRequest;

        List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(message.getTransId()).getResultList();
        if (reloadRecord != null && !reloadRecord.isEmpty()) {
            reloadRequest = reloadRecord.get(0);
            reloadRequest.setModifiedTime(new Date());
        } else {
            reloadRequest = new ReloadRequest();
            reloadRequest.setTransId(message.getTransId());
            reloadRequest.setMfgNumber(message.getMfgNo());
            reloadRequest.setReloadAmount(message.getAmount());
            reloadRequest.setServiceProviderId(message.getSpId());
            reloadRequest.setTransCode(message.getTransCode());
            reloadRequest.setRequestedTime(message.getRequestTime());
            reloadRequest.setModifiedTime(null);
        }
        if (StringUtils.equalsIgnoreCase(Constants.RELOAD_STATUS_PENDING, status)) {
            reloadRequest.setTngKey(message.getEncryptedMsg());
        } else {
            reloadRequest.setTngKey(null);
        }
        reloadRequest.setStatus(status);

        logger.info("[convertMessageToReloadRequest - New ReloadRequest object] >> " + reloadRequest);
        return reloadRequest;
    }
}
