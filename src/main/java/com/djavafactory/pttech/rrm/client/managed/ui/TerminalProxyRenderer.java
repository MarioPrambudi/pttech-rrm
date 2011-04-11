package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.TerminalProxy;
import com.google.gwt.requestfactory.ui.client.ProxyRenderer;

public class TerminalProxyRenderer extends ProxyRenderer<TerminalProxy> {

    private static com.djavafactory.pttech.rrm.client.managed.ui.TerminalProxyRenderer INSTANCE;

    protected TerminalProxyRenderer() {
        super(new String[] { "name" });
    }

    public static com.djavafactory.pttech.rrm.client.managed.ui.TerminalProxyRenderer instance() {
        if (INSTANCE == null) {
            INSTANCE = new TerminalProxyRenderer();
        }
        return INSTANCE;
    }

    public String render(TerminalProxy object) {
        if (object == null) {
            return "";
        }
        return object.getName() + " (" + object.getId() + ")";
    }
}
