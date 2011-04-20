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


public class MessageMapper {
    private static final Log logger = LogFactory.getLog(MessageMapper.class);

    public List<Object> mapNewReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.newRequestStatus));
        objectList.add(message);
		return objectList;
	}

    public List<Object> mapFailedReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.failedRequestStatus));
        objectList.add(message);
		return objectList;
	}

    public List<Object> mapExpiredReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.expiredRequestStatus));
        objectList.add(message);
		return objectList;
	}

    public List<Object> mapSuccessReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.successRequestStatus));
        objectList.add(message);
		return objectList;
	}

    public List<Object> mapTngReloadRequest(ReloadRequestMessage message) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(convertMessageToReloadRequest(message, Constants.pendingRequestStatus));
        objectList.add(message);
		return objectList;
	}

    public List<Object> mapTngReloadResponse(ReloadResponseMessage message) {
        List<Object> objectList = new ArrayList<Object>();
		ReloadRequest reloadRequest = null;

        List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(message.getTransId()).getResultList();
        if(reloadRecord != null && !reloadRecord.isEmpty()) {
            reloadRequest = reloadRecord.get(0);
            if(!StringUtils.equalsIgnoreCase(Constants.successResponseCode, message.getStatusCode())) {
                reloadRequest.setStatus(Constants.failedRequestStatus);
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
        if(reloadRecord != null && !reloadRecord.isEmpty()) {
            reloadRequest = reloadRecord.get(0);
        } else {
            reloadRequest = new ReloadRequest();
        }

        reloadRequest.setTransId(message.getTransId());
        reloadRequest.setStatus(status);
        reloadRequest.setMfgNumber(message.getMfgNo());
        reloadRequest.setReloadAmount(message.getAmount());
        reloadRequest.setServiceProviderId(message.getSpId());
        reloadRequest.setTransCode(message.getTransCode());
        if(StringUtils.equalsIgnoreCase(Constants.pendingRequestStatus, status)) {
            reloadRequest.setTngKey(message.getEncryptedMsg());
        } else {
            reloadRequest.setTngKey(null);
        }
        reloadRequest.setRequestedTime(message.getRequestTime());

        logger.info("[convertMessageToReloadRequest - New ReloadRequest object] >> " + reloadRequest);
		return reloadRequest;
	}
}
