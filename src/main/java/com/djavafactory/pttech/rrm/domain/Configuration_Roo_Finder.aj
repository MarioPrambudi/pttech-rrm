¯// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.Configuration;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Configuration_Roo_Finder {
    
    public static TypedQuery<Configuration> Configuration.findConfigurationsByConfigKey(String configKey) {
        if (configKey == null || configKey.length() == 0) throw new IllegalArgumentException("The configKey argument is required");
        EntityManager em = Configuration.entityManager();
        TypedQuery<Configuration> q = em.createQuery("SELECT Configuration FROM Configuration AS configuration WHERE configuration.configKey = :configKey", Configuration.class);
        q.setParameter("configKey", configKey);
        return q;
    }
    
}
