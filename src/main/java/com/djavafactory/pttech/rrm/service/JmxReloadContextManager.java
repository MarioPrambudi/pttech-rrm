package com.djavafactory.pttech.rrm.service;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: macintosh
 * Date: 5/4/11
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JmxReloadContextManager {

    public ConfigurableApplicationContext getContext();

    public void setContext(ConfigurableApplicationContext context);

    public void start();

    public void stop();

    public boolean isRunning();

    public void reloadContext();
}
