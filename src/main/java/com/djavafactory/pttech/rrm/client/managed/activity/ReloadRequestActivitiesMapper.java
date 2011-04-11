package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy;
import com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestRequest;
import com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestEditView;
import com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestMobileDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.ReloadRequestMobileEditView;
import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldApp;
import com.djavafactory.pttech.rrm.client.scaffold.place.CreateAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.FindAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.RequestContext;

public class ReloadRequestActivitiesMapper {

    private final ApplicationRequestFactory requests;

    private final PlaceController placeController;

    public ReloadRequestActivitiesMapper(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }

    public Activity getActivity(ProxyPlace place) {
        switch(place.getOperation()) {
            case DETAILS:
                return new ReloadRequestDetailsActivity((EntityProxyId<ReloadRequestProxy>) place.getProxyId(), requests, placeController, ScaffoldApp.isMobile() ? ReloadRequestMobileDetailsView.instance() : ReloadRequestDetailsView.instance());
            case EDIT:
                return makeEditActivity(place);
            case CREATE:
                return makeCreateActivity();
        }
        throw new IllegalArgumentException("Unknown operation " + place.getOperation());
    }

    @SuppressWarnings("unchecked")
    private EntityProxyId<com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy> coerceId(ProxyPlace place) {
        return (EntityProxyId<ReloadRequestProxy>) place.getProxyId();
    }

    private Activity makeCreateActivity() {
        ReloadRequestEditView.instance().setCreating(true);
        final ReloadRequestRequest request = requests.reloadRequestRequest();
        Activity activity = new CreateAndEditProxy<ReloadRequestProxy>(ReloadRequestProxy.class, request, ScaffoldApp.isMobile() ? ReloadRequestMobileEditView.instance() : ReloadRequestEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(ReloadRequestProxy proxy) {
                request.persist().using(proxy);
                return request;
            }
        };
        return new ReloadRequestEditActivityWrapper(requests, ScaffoldApp.isMobile() ? ReloadRequestMobileEditView.instance() : ReloadRequestEditView.instance(), activity, null);
    }

    private Activity makeEditActivity(ProxyPlace place) {
        ReloadRequestEditView.instance().setCreating(false);
        EntityProxyId<ReloadRequestProxy> proxyId = coerceId(place);
        Activity activity = new FindAndEditProxy<ReloadRequestProxy>(proxyId, requests, ScaffoldApp.isMobile() ? ReloadRequestMobileEditView.instance() : ReloadRequestEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(ReloadRequestProxy proxy) {
                ReloadRequestRequest request = requests.reloadRequestRequest();
                request.persist().using(proxy);
                return request;
            }
        };
        return new ReloadRequestEditActivityWrapper(requests, ScaffoldApp.isMobile() ? ReloadRequestMobileEditView.instance() : ReloadRequestEditView.instance(), activity, proxyId);
    }
}
