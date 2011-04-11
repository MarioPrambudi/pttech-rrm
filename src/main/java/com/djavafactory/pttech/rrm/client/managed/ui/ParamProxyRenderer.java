package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.ParamProxy;
import com.google.gwt.requestfactory.ui.client.ProxyRenderer;

public class ParamProxyRenderer extends ProxyRenderer<ParamProxy> {

    private static com.djavafactory.pttech.rrm.client.managed.ui.ParamProxyRenderer INSTANCE;

    protected ParamProxyRenderer() {
        super(new String[] { "terminalId" });
    }

    public static com.djavafactory.pttech.rrm.client.managed.ui.ParamProxyRenderer instance() {
        if (INSTANCE == null) {
            INSTANCE = new ParamProxyRenderer();
        }
        return INSTANCE;
    }

    public String render(ParamProxy object) {
        if (object == null) {
            return "";
        }
        return object.getTerminalId() + " (" + object.getId() + ")";
    }
}
