// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationEntityTypesProcessor;
import com.djavafactory.pttech.rrm.client.managed.request.ConfigurationProxy;
import com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy;
import com.djavafactory.pttech.rrm.client.managed.request.ParamProxy;
import com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy;
import com.djavafactory.pttech.rrm.client.managed.request.TerminalProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyListPlace;
import com.google.gwt.text.shared.AbstractRenderer;

public abstract class ApplicationListPlaceRenderer_Roo_Gwt extends AbstractRenderer<ProxyListPlace> {

    public String render(ProxyListPlace object) {
        return new ApplicationEntityTypesProcessor<String>() {

            @Override
            public void handleTerminal(TerminalProxy isNull) {
                setResult("Terminals");
            }

            @Override
            public void handleReloadRequest(ReloadRequestProxy isNull) {
                setResult("ReloadRequests");
            }

            @Override
            public void handleParam(ParamProxy isNull) {
                setResult("Params");
            }

            @Override
            public void handleFirmware(FirmwareProxy isNull) {
                setResult("Firmwares");
            }

            @Override
            public void handleConfiguration(ConfigurationProxy isNull) {
                setResult("Configurations");
            }
        }.process(object.getProxyClass());
    }
}
