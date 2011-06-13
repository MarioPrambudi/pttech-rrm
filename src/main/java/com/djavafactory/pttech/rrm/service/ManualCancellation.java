package com.djavafactory.pttech.rrm.service;


import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import org.springframework.integration.annotation.Gateway;

public interface ManualCancellation {

    @Gateway(requestChannel="cancelRequest")
	void sendManualCancel(ReloadRequest reloadRequest);

}
