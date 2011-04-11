package com.djavafactory.pttech.rrm.client.managed.activity;

import com.djavafactory.pttech.rrm.client.managed.request.ApplicationRequestFactory;
import com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy;
import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldMobileApp;
import com.djavafactory.pttech.rrm.client.scaffold.activity.IsScaffoldMobileActivity;
import com.djavafactory.pttech.rrm.client.scaffold.place.AbstractProxyListActivity;
import com.djavafactory.pttech.rrm.client.scaffold.place.ProxyListView;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.view.client.Range;
import java.util.List;

public class ReloadRequestListActivity extends AbstractProxyListActivity<ReloadRequestProxy> implements IsScaffoldMobileActivity {

    private final ApplicationRequestFactory requests;

    public ReloadRequestListActivity(ApplicationRequestFactory requests, ProxyListView<com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy> view, PlaceController placeController) {
        super(placeController, view, ReloadRequestProxy.class);
        this.requests = requests;
    }

    public Place getBackButtonPlace() {
        return ScaffoldMobileApp.ROOT_PLACE;
    }

    public String getBackButtonText() {
        return "Entities";
    }

    public Place getEditButtonPlace() {
        return null;
    }

    public String getTitleText() {
        return "ReloadRequests";
    }

    public boolean hasEditButton() {
        return false;
    }

    protected Request<java.util.List<com.djavafactory.pttech.rrm.client.managed.request.ReloadRequestProxy>> createRangeRequest(Range range) {
        return requests.reloadRequestRequest().findReloadRequestEntries(range.getStart(), range.getLength());
    }

    protected void fireCountRequest(Receiver<Long> callback) {
        requests.reloadRequestRequest().countReloadRequests().fire(callback);
    }
}
