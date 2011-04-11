package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy;
import com.djavafactory.pttech.rrm.client.managed.request.FirmwareRequest;
import com.djavafactory.pttech.rrm.client.managed.ui.FirmwareDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.FirmwareEditView;
import com.djavafactory.pttech.rrm.client.managed.ui.FirmwareListView;
import com.djavafactory.pttech.rrm.client.managed.ui.FirmwareMobileDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.FirmwareMobileEditView;
import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldApp;
import com.djavafactory.pttech.rrm.client.scaffold.place.CreateAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.FindAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.RequestContext;

public class FirmwareActivitiesMapper {

    private final ApplicationRequestFactory requests;

    private final PlaceController placeController;

    public FirmwareActivitiesMapper(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }

    public Activity getActivity(ProxyPlace place) {
        switch(place.getOperation()) {
            case DETAILS:
                return new FirmwareDetailsActivity((EntityProxyId<FirmwareProxy>) place.getProxyId(), requests, placeController, ScaffoldApp.isMobile() ? FirmwareMobileDetailsView.instance() : FirmwareDetailsView.instance());
            case EDIT:
                return makeEditActivity(place);
            case CREATE:
                return makeCreateActivity();
        }
        throw new IllegalArgumentException("Unknown operation " + place.getOperation());
    }

    @SuppressWarnings("unchecked")
    private EntityProxyId<com.djavafactory.pttech.rrm.client.managed.request.FirmwareProxy> coerceId(ProxyPlace place) {
        return (EntityProxyId<FirmwareProxy>) place.getProxyId();
    }

    private Activity makeCreateActivity() {
        FirmwareEditView.instance().setCreating(true);
        final FirmwareRequest request = requests.firmwareRequest();
        Activity activity = new CreateAndEditProxy<FirmwareProxy>(FirmwareProxy.class, request, ScaffoldApp.isMobile() ? FirmwareMobileEditView.instance() : FirmwareEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(FirmwareProxy proxy) {
                request.persist().using(proxy);
                return request;
            }
        };
        return new FirmwareEditActivityWrapper(requests, ScaffoldApp.isMobile() ? FirmwareMobileEditView.instance() : FirmwareEditView.instance(), activity, null);
    }

    private Activity makeEditActivity(ProxyPlace place) {
        FirmwareEditView.instance().setCreating(false);
        EntityProxyId<FirmwareProxy> proxyId = coerceId(place);
        Activity activity = new FindAndEditProxy<FirmwareProxy>(proxyId, requests, ScaffoldApp.isMobile() ? FirmwareMobileEditView.instance() : FirmwareEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(FirmwareProxy proxy) {
                FirmwareRequest request = requests.firmwareRequest();
                request.persist().using(proxy);
                return request;
            }
        };
        return new FirmwareEditActivityWrapper(requests, ScaffoldApp.isMobile() ? FirmwareMobileEditView.instance() : FirmwareEditView.instance(), activity, proxyId);
    }
}
