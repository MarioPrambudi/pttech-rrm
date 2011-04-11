// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.TerminalProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyDetailsView;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyListView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class TerminalDetailsView_Roo_Gwt extends Composite implements ProxyDetailsView<TerminalProxy> {

    @UiField
    SpanElement id;

    @UiField
    SpanElement version;

    @UiField
    SpanElement name;

    @UiField
    SpanElement location;

    @UiField
    SpanElement status;

    @UiField
    SpanElement createdTime;

    @UiField
    SpanElement modifiedTime;

    @UiField
    SpanElement createdBy;

    @UiField
    SpanElement modifiedBy;

    TerminalProxy proxy;

    @UiField
    SpanElement displayRenderer;

    public void setValue(TerminalProxy proxy) {
        this.proxy = proxy;
        id.setInnerText(proxy.getId() == null ? "" : String.valueOf(proxy.getId()));
        version.setInnerText(proxy.getVersion() == null ? "" : String.valueOf(proxy.getVersion()));
        name.setInnerText(proxy.getName() == null ? "" : String.valueOf(proxy.getName()));
        location.setInnerText(proxy.getLocation() == null ? "" : String.valueOf(proxy.getLocation()));
        status.setInnerText(proxy.getStatus() == null ? "" : String.valueOf(proxy.getStatus()));
        createdTime.setInnerText(proxy.getCreatedTime() == null ? "" : DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(proxy.getCreatedTime()));
        modifiedTime.setInnerText(proxy.getModifiedTime() == null ? "" : DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(proxy.getModifiedTime()));
        createdBy.setInnerText(proxy.getCreatedBy() == null ? "" : String.valueOf(proxy.getCreatedBy()));
        modifiedBy.setInnerText(proxy.getModifiedBy() == null ? "" : String.valueOf(proxy.getModifiedBy()));
        displayRenderer.setInnerText(com.djavafactory.pttech.rrm.client.managed.ui.TerminalProxyRenderer.instance().render(proxy));
    }
}