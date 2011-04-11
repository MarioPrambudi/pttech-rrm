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

public final class ApplicationMasterActivities extends ApplicationMasterActivities_Roo_Gwt {

    @Inject
    public ApplicationMasterActivities(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }
}
