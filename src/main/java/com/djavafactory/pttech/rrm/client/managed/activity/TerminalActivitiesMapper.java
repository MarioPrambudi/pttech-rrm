package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.TerminalProxy;
import com.djavafactory.pttech.rrm.client.managed.request.TerminalRequest;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalEditView;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalListView;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalMobileDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalMobileEditView;
import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldApp;
import com.djavafactory.pttech.rrm.client.scaffold.place.CreateAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.FindAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.RequestContext;

public class TerminalActivitiesMapper {

    private final ApplicationRequestFactory requests;

    private final PlaceController placeController;

    public TerminalActivitiesMapper(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }

    public Activity getActivity(ProxyPlace place) {
        switch(place.getOperation()) {
            case DETAILS:
                return new TerminalDetailsActivity((EntityProxyId<TerminalProxy>) place.getProxyId(), requests, placeController, ScaffoldApp.isMobile() ? TerminalMobileDetailsView.instance() : TerminalDetailsView.instance());
            case EDIT:
                return makeEditActivity(place);
            case CREATE:
                return makeCreateActivity();
        }
        throw new IllegalArgumentException("Unknown operation " + place.getOperation());
    }

    @SuppressWarnings("unchecked")
    private EntityProxyId<com.djavafactory.pttech.rrm.client.managed.request.TerminalProxy> coerceId(ProxyPlace place) {
        return (EntityProxyId<TerminalProxy>) place.getProxyId();
    }

    private Activity makeCreateActivity() {
        TerminalEditView.instance().setCreating(true);
        final TerminalRequest request = requests.terminalRequest();
        Activity activity = new CreateAndEditProxy<TerminalProxy>(TerminalProxy.class, request, ScaffoldApp.isMobile() ? TerminalMobileEditView.instance() : TerminalEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(TerminalProxy proxy) {
                request.persist().using(proxy);
                return request;
            }
        };
        return new TerminalEditActivityWrapper(requests, ScaffoldApp.isMobile() ? TerminalMobileEditView.instance() : TerminalEditView.instance(), activity, null);
    }

    private Activity makeEditActivity(ProxyPlace place) {
        TerminalEditView.instance().setCreating(false);
        EntityProxyId<TerminalProxy> proxyId = coerceId(place);
        Activity activity = new FindAndEditProxy<TerminalProxy>(proxyId, requests, ScaffoldApp.isMobile() ? TerminalMobileEditView.instance() : TerminalEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(TerminalProxy proxy) {
                TerminalRequest request = requests.terminalRequest();
                request.persist().using(proxy);
                return request;
            }
        };
        return new TerminalEditActivityWrapper(requests, ScaffoldApp.isMobile() ? TerminalMobileEditView.instance() : TerminalEditView.instance(), activity, proxyId);
    }
}
