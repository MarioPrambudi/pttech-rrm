// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package com.djavafactory.pttech.rrm.client.managed.request;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.ProxyForName;
import org.springframework.roo.addon.gwt.RooGwtMirroredFrom;

@RooGwtMirroredFrom("com.djavafactory.pttech.rrm.domain.Configuration")
@ProxyForName("com.djavafactory.pttech.rrm.domain.Configuration")
public interface ConfigurationProxy extends EntityProxy {

    abstract Long getId();

    abstract Integer getVersion();

    abstract String getConfigKey();

    abstract String getConfigValue();

    abstract Integer getOrdering();

    abstract void setId(Long id);

    abstract void setVersion(Integer version);

    abstract void setConfigKey(String configKey);

    abstract void setConfigValue(String configValue);

    abstract void setOrdering(Integer ordering);
}
