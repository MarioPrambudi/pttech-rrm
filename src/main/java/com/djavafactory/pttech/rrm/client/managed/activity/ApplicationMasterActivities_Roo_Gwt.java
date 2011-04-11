// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationEntityTypesProcessor;
import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.ConfigurationProxy;
import com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy;
import com.djavafactory.pttech.rrm.client.managed.request.ParamProxy;
import com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy;
import com.djavafactory.pttech.rrm.client.managed.request.TerminalProxy;
import com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationMobileListView;
import com.djavafactory.pttech.rrm.client.managed.ui.FirmwareListView;
import com.djavafactory.pttech.rrm.client.managed.ui.FirmwareMobileListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ParamListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ParamMobileListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestMobileListView;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalListView;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalMobileListView;
import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldApp;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyListPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

public abstract class ApplicationMasterActivities_Roo_Gwt implements ActivityMapper {

    protected ApplicationRequestFactory requests;

    protected PlaceController placeController;

    public Activity getActivity(Place place) {
        if (!(place instanceof ProxyListPlace)) {
            return null;
        }
        ProxyListPlace listPlace = (ProxyListPlace) place;
        return new ApplicationEntityTypesProcessor<Activity>() {

            @Override
            public void handleTerminal(TerminalProxy isNull) {
                setResult(new TerminalListActivity(requests, ScaffoldApp.isMobile() ? TerminalMobileListView.instance() : TerminalListView.instance(), placeController));
            }

            @Override
            public void handleReloadRequest(ReloadRequestProxy isNull) {
                setResult(new ReloadRequestListActivity(requests, ScaffoldApp.isMobile() ? ReloadRequestMobileListView.instance() : ReloadRequestListView.instance(), placeController));
            }

            @Override
            public void handleParam(ParamProxy isNull) {
                setResult(new ParamListActivity(requests, ScaffoldApp.isMobile() ? ParamMobileListView.instance() : ParamListView.instance(), placeController));
            }

            @Override
            public void handleFirmware(FirmwareProxy isNull) {
                setResult(new FirmwareListActivity(requests, ScaffoldApp.isMobile() ? FirmwareMobileListView.instance() : FirmwareListView.instance(), placeController));
            }

            @Override
            public void handleConfiguration(ConfigurationProxy isNull) {
                setResult(new ConfigurationListActivity(requests, ScaffoldApp.isMobile() ? ConfigurationMobileListView.instance() : ConfigurationListView.instance(), placeController));
            }
        }.process(listPlace.getProxyClass());
    }
}