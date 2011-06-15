package com.djavafactory.pttech.rrm.service;


import com.djavafactory.pttech.rrm.domain.Param;
import org.springframework.integration.annotation.Gateway;

public interface UploadParameter {

    @Gateway(requestChannel="uploadFirmwareChannel")
	void uploadParameter(Param parameter);

}
