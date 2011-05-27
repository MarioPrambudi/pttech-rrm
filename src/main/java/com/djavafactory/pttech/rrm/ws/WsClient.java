package com.djavafactory.pttech.rrm.ws;


import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import epg.webservice.ReloadRequest;
import epg.webservice.ReloadRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

public class WsClient {
    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public JAXBElement<ReloadRequestResponse> sendWsClient(JAXBElement<ReloadRequest> reloadReq) {
        Configuration configuration = Configuration.findConfigurationByConfigKey(Constants.CONFIG_TNG_REQUESTWS);
        return (JAXBElement<ReloadRequestResponse>) webServiceTemplate.marshalSendAndReceive(configuration.getConfigValue(), reloadReq);
    }
}
