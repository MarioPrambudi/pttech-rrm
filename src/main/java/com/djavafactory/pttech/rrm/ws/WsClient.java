package com.djavafactory.pttech.rrm.ws;


import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class WsClient {
    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public ReloadReqResponse sendWsClient(ReloadReq reloadReq) {
        Configuration configuration = Configuration.findConfigurationByConfigKey(Constants.CONFIG_TNG_REQUESTWS);
        return (ReloadReqResponse) webServiceTemplate.marshalSendAndReceive(configuration.getConfigValue(), reloadReq);
    }
}
