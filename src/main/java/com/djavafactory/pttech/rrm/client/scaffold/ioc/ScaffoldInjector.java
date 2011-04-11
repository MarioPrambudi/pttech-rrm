package com.djavafactory.pttech.rrm.client.scaffold.ioc;

import com.djavafactory.pttech.rrm.client.scaffold.ScaffoldApp;
import com.google.gwt.inject.client.Ginjector;

public interface ScaffoldInjector extends Ginjector {

	ScaffoldApp getScaffoldApp();
}
