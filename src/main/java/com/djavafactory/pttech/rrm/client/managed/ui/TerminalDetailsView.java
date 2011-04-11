package com.djavafactory.pttech.rrm.client.managed.ui;

import com.djavafactory.pttech.rrm.client.managed.request.TerminalProxy;
import com.djavafactory.pttech.rrm.client.managed.ui.TerminalDetailsView.Binder;
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

public class TerminalDetailsView extends TerminalDetailsView_Roo_Gwt {

    private static final Binder BINDER = GWT.create(Binder.class);

    private static com.djavafactory.pttech.rrm.client.managed.ui.TerminalDetailsView instance;

    @UiField
    HasClickHandlers edit;

    @UiField
    HasClickHandlers delete;

    private Delegate delegate;

    public TerminalDetailsView() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public static com.djavafactory.pttech.rrm.client.managed.ui.TerminalDetailsView instance() {
        if (instance == null) {
            instance = new TerminalDetailsView();
        }
        return instance;
    }

    public Widget asWidget() {
        return this;
    }

    public boolean confirm(String msg) {
        return Window.confirm(msg);
    }

    public TerminalProxy getValue() {
        return proxy;
    }

    @UiHandler("delete")
    public void onDeleteClicked(ClickEvent e) {
        delegate.deleteClicked();
    }

    @UiHandler("edit")
    public void onEditClicked(ClickEvent e) {
        delegate.editClicked();
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    interface Binder extends UiBinder<HTMLPanel, TerminalDetailsView> {
    }
}
