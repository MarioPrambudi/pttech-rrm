package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy;
import com.google.gwt.requestfactory.ui.client.ProxyRenderer;

public class FirmwareProxyRenderer extends ProxyRenderer<FirmwareProxy> {

    private static com.djavafactory.pttech.rrm.client.managed.ui.FirmwareProxyRenderer INSTANCE;

    protected FirmwareProxyRenderer() {
        super(new String[] { "terminalId" });
    }

    public static com.djavafactory.pttech.rrm.client.managed.ui.FirmwareProxyRenderer instance() {
        if (INSTANCE == null) {
            INSTANCE = new FirmwareProxyRenderer();
        }
        return INSTANCE;
    }

    public String render(FirmwareProxy object) {
        if (object == null) {
            return "";
        }
        return object.getTerminalId() + " (" + object.getId() + ")";
    }
}
