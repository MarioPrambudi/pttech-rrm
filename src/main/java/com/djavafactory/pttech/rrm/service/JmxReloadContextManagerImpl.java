package com.djavafactory.pttech.rrm.service;

import com.djavafactory.pttech.rrm.conf.DynamicScheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class JmxReloadContextManagerImpl implements JmxReloadContextManager, ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @Autowired
    @Qualifier("celcomScheduler")
    private DynamicScheduler celcomScheduler;

    @Autowired
    @Qualifier("tngScheduler")
    private DynamicScheduler tngScheduler;

    @ManagedAttribute
    public ConfigurableApplicationContext getContext() {
        return context;
    }

    @ManagedOperation
    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @ManagedOperation
    public void start() {
        this.context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext*.xml");
    }

    @ManagedOperation
    public void stop() {
        this.context.stop();
    }

    @ManagedAttribute
    public boolean isRunning() {
        return this.context.isRunning();
    }

    @ManagedOperation
    public void reloadContext() {
        celcomScheduler.stopScheduler();
        tngScheduler.stopScheduler();
        this.context.refresh();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = ((ConfigurableApplicationContext) applicationContext);
    }
}
