package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.ConfigurationProxy;
import com.google.gwt.requestfactory.ui.client.ProxyRenderer;

public class ConfigurationProxyRenderer extends ProxyRenderer<ConfigurationProxy> {

    private static com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationProxyRenderer INSTANCE;

    protected ConfigurationProxyRenderer() {
        super(new String[] { "configKey" });
    }

    public static com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationProxyRenderer instance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigurationProxyRenderer();
        }
        return INSTANCE;
    }

    public String render(ConfigurationProxy object) {
        if (object == null) {
            return "";
        }
        return object.getConfigKey() + " (" + object.getId() + ")";
    }
}
