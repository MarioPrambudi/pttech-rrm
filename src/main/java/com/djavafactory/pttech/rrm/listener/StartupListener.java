package com.djavafactory.pttech.rrm.listener;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.service.DynamicScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * StartupListener class used to initialize the Task Scheduler.
 *
 * @author Carine Leong
 */
public class StartupListener implements ServletContextListener {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();

        setupSchedulerContext(context);
    }

    /**
     * Shutdown servlet context (currently a no-op method).
     *
     * @param servletContextEvent The servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * This method uses the Configuration to lookup the Celcom and TnG Batch Report schedule cron expression.
     *
     * @param context The servlet context
     */
    public static void setupSchedulerContext(ServletContext context) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        String celcomCron = (Configuration.findConfigurationByConfigKey(Constants.CONFIG_CEL_BATCH_SCHEDULE) != null) ? Configuration.findConfigurationByConfigKey(Constants.CONFIG_CEL_BATCH_SCHEDULE).getConfigValue() : null;
        String tngCron = (Configuration.findConfigurationByConfigKey(Constants.CONFIG_TNG_BATCH_SCHEDULE) != null) ? Configuration.findConfigurationByConfigKey(Constants.CONFIG_TNG_BATCH_SCHEDULE).getConfigValue() : null;

        if (celcomCron != null) {
            DynamicScheduler scheduler = (DynamicScheduler) ctx.getBean("celcomScheduler");
            scheduler.resetScheduler(celcomCron);
        }

        if (tngCron != null) {
            DynamicScheduler scheduler = (DynamicScheduler) ctx.getBean("tngScheduler");
            scheduler.resetScheduler(celcomCron);
        }
    }
}
