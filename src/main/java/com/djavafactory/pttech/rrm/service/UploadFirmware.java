package com.djavafactory.pttech.rrm.service;


import com.djavafactory.pttech.rrm.domain.Firmware;
import org.springframework.integration.annotation.Gateway;

public interface UploadFirmware {

    @Gateway(requestChannel="uploadFirmwareChannel")
	void uploadFirmware(Firmware firmware);

}
