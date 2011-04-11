package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.ConfigurationProxy;
import com.djavafactory.pttech.rrm.client.managed.request.ConfigurationRequest;
import com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationEditView;
import com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationListView;
import com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationMobileDetailsView;
import com.djavafactory.pttech.rrm.client.managed.ui.ConfigurationMobileEditView;
import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldApp;
import com.djavafactory.pttech.rrm.client.scaffold.place.CreateAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.FindAndEditProxy;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.RequestContext;

public class ConfigurationActivitiesMapper {

    private final ApplicationRequestFactory requests;

    private final PlaceController placeController;

    public ConfigurationActivitiesMapper(ApplicationRequestFactory requests, PlaceController placeController) {
        this.requests = requests;
        this.placeController = placeController;
    }

    public Activity getActivity(ProxyPlace place) {
        switch(place.getOperation()) {
            case DETAILS:
                return new ConfigurationDetailsActivity((EntityProxyId<ConfigurationProxy>) place.getProxyId(), requests, placeController, ScaffoldApp.isMobile() ? ConfigurationMobileDetailsView.instance() : ConfigurationDetailsView.instance());
            case EDIT:
                return makeEditActivity(place);
            case CREATE:
                return makeCreateActivity();
        }
        throw new IllegalArgumentException("Unknown operation " + place.getOperation());
    }

    @SuppressWarnings("unchecked")
    private EntityProxyId<com.djavafactory.pttech.rrm.client.managed.request.ConfigurationProxy> coerceId(ProxyPlace place) {
        return (EntityProxyId<ConfigurationProxy>) place.getProxyId();
    }

    private Activity makeCreateActivity() {
        ConfigurationEditView.instance().setCreating(true);
        final ConfigurationRequest request = requests.configurationRequest();
        Activity activity = new CreateAndEditProxy<ConfigurationProxy>(ConfigurationProxy.class, request, ScaffoldApp.isMobile() ? ConfigurationMobileEditView.instance() : ConfigurationEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(ConfigurationProxy proxy) {
                request.persist().using(proxy);
                return request;
            }
        };
        return new ConfigurationEditActivityWrapper(requests, ScaffoldApp.isMobile() ? ConfigurationMobileEditView.instance() : ConfigurationEditView.instance(), activity, null);
    }

    private Activity makeEditActivity(ProxyPlace place) {
        ConfigurationEditView.instance().setCreating(false);
        EntityProxyId<ConfigurationProxy> proxyId = coerceId(place);
        Activity activity = new FindAndEditProxy<ConfigurationProxy>(proxyId, requests, ScaffoldApp.isMobile() ? ConfigurationMobileEditView.instance() : ConfigurationEditView.instance(), placeController) {

            @Override
            protected RequestContext createSaveRequest(ConfigurationProxy proxy) {
                ConfigurationRequest request = requests.configurationRequest();
                request.persist().using(proxy);
                return request;
            }
        };
        return new ConfigurationEditActivityWrapper(requests, ScaffoldApp.isMobile() ? ConfigurationMobileEditView.instance() : ConfigurationEditView.instance(), activity, proxyId);
    }
}
