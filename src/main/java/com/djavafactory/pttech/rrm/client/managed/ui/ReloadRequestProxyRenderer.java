package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy;
import com.google.gwt.requestfactory.ui.client.ProxyRenderer;

public class ReloadRequestProxyRenderer extends ProxyRenderer<ReloadRequestProxy> {

    private static com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestProxyRenderer INSTANCE;

    protected ReloadRequestProxyRenderer() {
        super(new String[] { "transId" });
    }

    public static com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestProxyRenderer instance() {
        if (INSTANCE == null) {
            INSTANCE = new ReloadRequestProxyRenderer();
        }
        return INSTANCE;
    }

    public String render(ReloadRequestProxy object) {
        if (object == null) {
            return "";
        }
        return object.getTransId() + " (" + object.getId() + ")";
    }
}
