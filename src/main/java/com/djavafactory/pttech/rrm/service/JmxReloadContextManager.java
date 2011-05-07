package com.djavafactory.pttech.rrm.service;

import org.springframework.context.ConfigurableApplicationContext;

public interface JmxReloadContextManager {

    public ConfigurableApplicationContext getContext();

    public void setContext(ConfigurableApplicationContext context);

    public void start();

    public void stop();

    public boolean isRunning();

    public void reloadContext();
}
