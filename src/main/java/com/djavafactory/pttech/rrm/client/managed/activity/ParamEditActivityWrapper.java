package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.activity.ParamEditActivityWrapper.View;
import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.ParamProxy;
import com.djavafactory.pttech.rrm.client.scaffold.activity.IsScaffoldMobileActivity;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyEditView;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyListPlace;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ParamEditActivityWrapper extends ParamEditActivityWrapper_Roo_Gwt {

    private final EntityProxyId<ParamProxy> proxyId;

    public ParamEditActivityWrapper(ApplicationRequestFactory requests, View<?> view, Activity activity, EntityProxyId<com.djavafactory.pttech.rrm.client.managed.request.ParamProxy> proxyId) {
        this.requests = requests;
        this.view = view;
        this.wrapped = activity;
        this.proxyId = proxyId;
    }

    public Place getBackButtonPlace() {
        return (proxyId == null) ? new ProxyListPlace(ParamProxy.class) : new ProxyPlace(proxyId, ProxyPlace.Operation.DETAILS);
    }

    public String getBackButtonText() {
        return "Cancel";
    }

    public Place getEditButtonPlace() {
        return null;
    }

    public String getTitleText() {
        return (proxyId == null) ? "New Param" : "Edit Param";
    }

    public boolean hasEditButton() {
        return false;
    }

    @Override
    public String mayStop() {
        return wrapped.mayStop();
    }

    @Override
    public void onCancel() {
        wrapped.onCancel();
    }

    @Override
    public void onStop() {
        wrapped.onStop();
    }

    public interface View<V extends com.djavafactory.pttech.rrm.client.scaffold.place.ProxyEditView<com.djavafactory.pttech.rrm.client.managed.request.ParamProxy, V>> extends View_Roo_Gwt<V> {
    }
}