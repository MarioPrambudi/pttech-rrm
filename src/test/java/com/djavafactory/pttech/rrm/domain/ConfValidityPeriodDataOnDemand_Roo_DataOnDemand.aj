// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.ConfValidityPeriod;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect ConfValidityPeriodDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ConfValidityPeriodDataOnDemand: @Component;
    
    private Random ConfValidityPeriodDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<ConfValidityPeriod> ConfValidityPeriodDataOnDemand.data;
    
    public ConfValidityPeriod ConfValidityPeriodDataOnDemand.getNewTransientConfValidityPeriod(int index) {
        com.djavafactory.pttech.rrm.domain.ConfValidityPeriod obj = new com.djavafactory.pttech.rrm.domain.ConfValidityPeriod();
        setConfigKey(obj, index);
        setConfigValue(obj, index);
        setStartDate(obj, index);
        setEndDate(obj, index);
        return obj;
    }
    
    public void ConfValidityPeriodDataOnDemand.setConfigKey(ConfValidityPeriod obj, int index) {
        java.lang.String configKey = "configKey_" + index;
        obj.setConfigKey(configKey);
    }
    
    public void ConfValidityPeriodDataOnDemand.setConfigValue(ConfValidityPeriod obj, int index) {
        java.lang.String configValue = "configValue_" + index;
        obj.setConfigValue(configValue);
    }
    
    public void ConfValidityPeriodDataOnDemand.setStartDate(ConfValidityPeriod obj, int index) {
        java.util.Date startDate = new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setStartDate(startDate);
    }
    
    public void ConfValidityPeriodDataOnDemand.setEndDate(ConfValidityPeriod obj, int index) {
        java.util.Date endDate = new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setEndDate(endDate);
    }
    
    public ConfValidityPeriod ConfValidityPeriodDataOnDemand.getSpecificConfValidityPeriod(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        ConfValidityPeriod obj = data.get(index);
        return ConfValidityPeriod.findConfValidityPeriod(obj.getId());
    }
    
    public ConfValidityPeriod ConfValidityPeriodDataOnDemand.getRandomConfValidityPeriod() {
        init();
        ConfValidityPeriod obj = data.get(rnd.nextInt(data.size()));
        return ConfValidityPeriod.findConfValidityPeriod(obj.getId());
    }
    
    public boolean ConfValidityPeriodDataOnDemand.modifyConfValidityPeriod(ConfValidityPeriod obj) {
        return false;
    }
    
    public void ConfValidityPeriodDataOnDemand.init() {
        data = com.djavafactory.pttech.rrm.domain.ConfValidityPeriod.findConfValidityPeriodEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'ConfValidityPeriod' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.djavafactory.pttech.rrm.domain.ConfValidityPeriod>();
        for (int i = 0; i < 10; i++) {
            com.djavafactory.pttech.rrm.domain.ConfValidityPeriod obj = getNewTransientConfValidityPeriod(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
