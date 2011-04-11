package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.ParamProxy;
import com.djavafactory.pttech.rrm.client.managed.request.ParamRequest;
import com.djavafactory.pttech.rrm.client.managed.ui.ParamDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.ParamEditView;
import com.djavafactory.pttech.rrm.client.managed.ui.ParamListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ParamMobileDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.ParamMobileEditView;
import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldApp;
import com.djavafactory.pttech.rrm.client.scaffold.place.CreateAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.FindAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.RequestContext;

public class ParamActivitiesMapper {

    private final ApplicationRequestFactory requests;

    private final PlaceController placeController;

    public ParamActivitiesMapper(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }

    public Activity getActivity(ProxyPlace place) {
        switch(place.getOperation()) {
            case DETAILS:
                return new ParamDetailsActivity((EntityProxyId<ParamProxy>) place.getProxyId(), requests, placeController, ScaffoldApp.isMobile() ? ParamMobileDetailsView.instance() : ParamDetailsView.instance());
            case EDIT:
                return makeEditActivity(place);
            case CREATE:
                return makeCreateActivity();
        }
        throw new IllegalArgumentException("Unknown operation " + place.getOperation());
    }

    @SuppressWarnings("unchecked")
    private EntityProxyId<com.djavafactory.pttech.rrm.client.managed.request.ParamProxy> coerceId(ProxyPlace place) {
        return (EntityProxyId<ParamProxy>) place.getProxyId();
    }

    private Activity makeCreateActivity() {
        ParamEditView.instance().setCreating(true);
        final ParamRequest request = requests.paramRequest();
        Activity activity = new CreateAndEditProxy<ParamProxy>(ParamProxy.class, request, ScaffoldApp.isMobile() ? ParamMobileEditView.instance() : ParamEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(ParamProxy proxy) {
                request.persist().using(proxy);
                return request;
            }
        };
        return new ParamEditActivityWrapper(requests, ScaffoldApp.isMobile() ? ParamMobileEditView.instance() : ParamEditView.instance(), activity, null);
    }

    private Activity makeEditActivity(ProxyPlace place) {
        ParamEditView.instance().setCreating(false);
        EntityProxyId<ParamProxy> proxyId = coerceId(place);
        Activity activity = new FindAndEditProxy<ParamProxy>(proxyId, requests, ScaffoldApp.isMobile() ? ParamMobileEditView.instance() : ParamEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(ParamProxy proxy) {
                ParamRequest request = requests.paramRequest();
                request.persist().using(proxy);
                return request;
            }
        };
        return new ParamEditActivityWrapper(requests, ScaffoldApp.isMobile() ? ParamMobileEditView.instance() : ParamEditView.instance(), activity, proxyId);
    }
}
