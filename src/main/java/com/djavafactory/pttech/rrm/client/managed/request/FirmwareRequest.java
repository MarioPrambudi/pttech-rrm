// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package com.djavafactory.pttech.rrm.client.managed.request;

import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.ServiceName;
import org.springframework.roo.addon.gwt.RooGwtMirroredFrom;

@RooGwtMirroredFrom("com.djavafactory.pttech.rrm.domain.Firmware")
@ServiceName("com.djavafactory.pttech.rrm.domain.Firmware")
public interface FirmwareRequest extends RequestContext {

    abstract Request<java.lang.Long> countFirmwares();

    abstract Request<java.util.List<com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy>> findAllFirmwares();

    abstract Request<java.util.List<com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy>> findFirmwareEntries(int firstResult, int maxResults);

    abstract Request<com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy> findFirmware(Long id);

    abstract InstanceRequest<com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy, java.lang.Void> remove();

    abstract InstanceRequest<com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy, java.lang.Void> persist();
}
