package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.*;

@RooJavaBean
@RooToString
@RooEntity(finders = {"findConfigurationsByConfigKeyLike", "findConfigurationsByConfigKey"})
public class Configuration implements Comparable<Configuration> {

    public enum ConfigPrefix {

        CELCOM("CEL"), TOUCH_N_GO("TNG"), RRM("RRM"), REPORT("REPORT"),
        REPORT_CELCOM("REPORT.CEL."), REPORT_TNG("REPORT.TNG."), REPORT_PTT("REPORT.PTT.");

        private final String key;

        ConfigPrefix(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }
    }

    @NotNull
    @Column(unique = true)
    private String configKey;

    private String configValue;

    @NotNull
    private Integer ordering;

    @Override
    public int compareTo(Configuration anotherConfiguration) {
        if (this.ordering.compareTo(anotherConfiguration.getOrdering()) != 0) {
            return this.ordering.compareTo(anotherConfiguration.getOrdering());
        } else {
            return this.configKey.compareTo(anotherConfiguration.getConfigKey());
        }
    }

    public static List<Configuration> findByConfigKeyPrefix(String prefix) {
        boolean valid = false;
        for (ConfigPrefix configPrefix : ConfigPrefix.values()) {
            if (configPrefix.key.equals(prefix)) {
                valid = true;
                break;
            }
        }
        if (!valid) throw new IllegalArgumentException("The ConfigPrefix is invalid");
        prefix = prefix + "%";
        EntityManager em = Configuration.entityManager();
        TypedQuery<Configuration> q = em.createQuery("SELECT Configuration FROM Configuration AS configuration WHERE LOWER(configuration.configKey) LIKE LOWER(:configKey)", Configuration.class);
        q.setParameter("configKey", prefix);
        List<Configuration> configurations = q.getResultList();
        Collections.sort(configurations);
        return configurations;
    }

    @Transient
    public ConfigPrefix getConfigPrefix() {
        for (ConfigPrefix configPrefix : ConfigPrefix.values()) {
            if (this.configKey.toLowerCase().startsWith(configPrefix.key.toLowerCase())) {
                return configPrefix;
            }
        }
        return null;
    }

    public static Configuration findConfigurationByConfigKey(String configKey) {
        if (configKey == null || configKey.length() == 0)
            throw new IllegalArgumentException("The configKey argument is required");
        EntityManager em = Configuration.entityManager();
        TypedQuery<Configuration> q = em.createQuery("SELECT Configuration FROM Configuration AS configuration WHERE configuration.configKey = :configKey", Configuration.class);
        q.setParameter("configKey", configKey);
        List<Configuration> configurations = q.getResultList();
        return (configurations != null && !configurations.isEmpty()) ? configurations.get(0) : null;
    }

    //to get a specific configure value
     public static String getReportConfigValue(String valueName){
           Configuration config = findConfigurationByConfigKey(valueName);
           return config.getConfigValue().toString();
     }

}
